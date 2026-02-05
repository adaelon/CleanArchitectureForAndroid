package com.mitteloupe.whoami.home.presentation.viewmodel

import com.mitteloupe.whoami.architecture.domain.UseCaseExecutor
import com.mitteloupe.whoami.architecture.presentation.viewmodel.BaseViewModel
import com.mitteloupe.whoami.coroutine.CoroutineContextProvider // 👈 引入你的 Provider
import com.mitteloupe.whoami.home.domain.model.ActivityCategory
import com.mitteloupe.whoami.home.domain.model.EnergyLevel
import com.mitteloupe.whoami.home.domain.model.ThreadTask
import com.mitteloupe.whoami.home.domain.usecase.CreateUserTaskUseCase
import com.mitteloupe.whoami.home.domain.usecase.GetHomeConfigurationUseCase
import com.mitteloupe.whoami.home.domain.usecase.HomeConfiguration
import com.mitteloupe.whoami.home.domain.usecase.UserTaskRequest
import com.mitteloupe.whoami.home.presentation.mapper.HomePresentationMapper
import com.mitteloupe.whoami.home.presentation.model.HomePresentationNotification
import com.mitteloupe.whoami.home.presentation.model.HomeViewState
import com.mitteloupe.whoami.home.presentation.navigation.HomePresentationNavigationEvent.OnViewHistory
import com.mitteloupe.whoami.home.presentation.navigation.HomePresentationNavigationEvent.OnViewOpenSourceNotices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getHomeConfigurationUseCase: GetHomeConfigurationUseCase,
    private val createUserTaskUseCase: CreateUserTaskUseCase,
    private val homePresentationMapper: HomePresentationMapper,
    useCaseExecutor: UseCaseExecutor,
    // 👇 1. 在这里注入 Provider (只给这个 ViewModel 用)
    private val coroutineContextProvider: CoroutineContextProvider
) : BaseViewModel<HomeViewState, HomePresentationNotification>(useCaseExecutor) {

    // 👇 2. 自己创建一个 Scope
    // 使用 main 线程，这样更新 UI 是安全的
    private val viewModelScope = CoroutineScope(SupervisorJob() + coroutineContextProvider.main)

    private var timerJob: Job? = null

    // 👇 3. 本地记录当前状态 (解决 viewState 没有 .value 的问题)
    private var currentViewState: HomeViewState = HomeViewState.Loading

    fun onEnter() {
        updateViewState(HomeViewState.Loading)
        fetchConfiguration()
    }

    // ... (onCategorySelected, onTaskStarted, onTaskCompleted 等方法保持不变) ...

    fun onCategorySelected(category: ActivityCategory, energyLevel: EnergyLevel) {
        updateViewState(
            HomeViewState.Inputting(
                category = category,
                energyLevel = energyLevel,
                parentGoal = null
            )
        )
    }

    fun onTaskStarted(content: String, parentGoal: String?) {
        val state = currentViewState // 使用本地记录的状态
        if (state is HomeViewState.Inputting) {
            createTask(content, parentGoal, state.energyLevel)
        }
    }

    fun onTaskCompleted() {
        timerJob?.cancel()
        val state = currentViewState
        if (state is HomeViewState.Focusing) {
            updateViewState(
                HomeViewState.Inputting(
                    category = ActivityCategory.LEARNING, // 临时写死，或者从 focusing state 里取
                    energyLevel = state.energyLevel,
                    parentGoal = state.currentTask.parentId ?: state.currentTask.content
                )
            )
        }
    }

    fun onViewHistoryAction() {
        emitNavigationEvent(OnViewHistory)
    }

    fun onOpenSourceNoticesAction() {
        emitNavigationEvent(OnViewOpenSourceNotices)
    }

    // 👇 4. 必须重写这个方法，截获状态更新，记录到 currentViewState
    // 这里的 override 可能会报错，因为 BaseViewModel 的 updateViewState 是 protected 的。
    // 如果 BaseViewModel 是 library 里的改不了，那就在调用 updateViewState 之前，手动赋值一次。
    // 比如定义一个新的私有方法:
    private fun emitViewState(newState: HomeViewState) {
        currentViewState = newState // 记下来
        updateViewState(newState)   // 发出去
    }

    // --- 私有逻辑 ---

    private fun fetchConfiguration() {
        getHomeConfigurationUseCase(
            value = Unit,
            onResult = ::presentConfiguration,
            onException = ::presentError
        )
    }

    private fun presentConfiguration(configuration: HomeConfiguration) {
        emitViewState(HomeViewState.Selection()) // 注意这里用了 emitViewState
    }

    private fun createTask(content: String, parentGoal: String?, energyLevel: EnergyLevel) {
        createUserTaskUseCase(
            value = UserTaskRequest(content, parentGoal),
            onResult = { task -> presentFocusingState(task, energyLevel) },
            onException = ::presentError
        )
    }

    private fun presentFocusingState(task: ThreadTask, energyLevel: EnergyLevel) {
        // 1. 更新 UI
        emitViewState(
            HomeViewState.Focusing(
                currentTask = task,
                energyLevel = energyLevel,
                isCatMeowing = false
            )
        )

        // 2. 启动倒计时
        startCheckInTimer(energyLevel.checkIntervalMs)
    }

    private fun startCheckInTimer(durationMs: Long) {
        timerJob?.cancel()
        // 👇 使用我们自己的 viewModelScope
        timerJob = viewModelScope.launch {
            delay(durationMs)

            // 👇 直接读取本地变量
            val state = currentViewState
            if (state is HomeViewState.Focusing) {
                emitViewState(
                    state.copy(isCatMeowing = true)
                )
            }
        }
    }

    private fun presentError(throwable: Throwable) {
        emitViewState(homePresentationMapper.toErrorState(throwable))
    }

    // 👇 5. 重要：手动清理 (防止内存泄漏)
    // 请在 Activity/Fragment 的 onDestroy 中调用 homeViewModel.clear()
    fun clear() {
        viewModelScope.cancel()
    }
}
