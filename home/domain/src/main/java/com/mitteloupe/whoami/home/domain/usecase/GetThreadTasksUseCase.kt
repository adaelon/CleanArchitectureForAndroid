package com.mitteloupe.whoami.home.domain.usecase

import com.mitteloupe.whoami.architecture.domain.usecase.ContinuousExecutingUseCase
import com.mitteloupe.whoami.coroutine.CoroutineContextProvider
import com.mitteloupe.whoami.home.domain.model.ThreadTask
import com.mitteloupe.whoami.home.domain.repository.TaskRepository
import javax.inject.Inject

/**
 * 继承 ContinuousExecutingUseCase
 * 输入 (Request): Unit (不需要参数，查全部)
 * 输出 (Response): List<ThreadTask> (任务列表)
 */
class GetThreadTasksUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
    coroutineContextProvider: CoroutineContextProvider
) : ContinuousExecutingUseCase<Unit, List<ThreadTask>>(coroutineContextProvider) {

    // 这里返回的是 Flow<List<ThreadTask>>
    override fun executeInBackground(request: Unit) =
        taskRepository.getTasks()
}
