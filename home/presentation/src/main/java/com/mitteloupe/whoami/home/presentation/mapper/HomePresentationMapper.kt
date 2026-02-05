package com.mitteloupe.whoami.home.presentation.mapper

import com.mitteloupe.whoami.home.presentation.model.HomeViewState
import javax.inject.Inject

/**
 * 原名: ConnectionDetailsPresentationMapper
 * 现在的作用: 帮助 ViewModel 把各种复杂的异常或数据，转换为 UI 能读懂的 HomeViewState
 */
class HomePresentationMapper @Inject constructor(
    private val exceptionPresentationMapper: ExceptionPresentationMapper
) {

    /**
     * ❌ 映射错误状态
     * 把底层的 Throwable (比如 NetworkException) 转换成 UI 显示的 Error 状态
     */
    fun toErrorState(throwable: Throwable): HomeViewState.Error {
        // 利用架构中现有的 ExceptionMapper 把异常转成人类可读的文字
        val errorModel = exceptionPresentationMapper.toPresentation(throwable)

        // 假设 ErrorPresentationModel 有 title 和 description 字段
        // 我们把它拼接成你的 HomeViewState.Error 需要的 message 字符串
        val errorMessage = "${errorModel.title}: ${errorModel.description}"

        return HomeViewState.Error(message = errorMessage)
    }

    // 💡 提示：
    // 对于 Selection, Inputting, Focusing 这些状态，
    // 因为它们的数据结构很简单（就是直接把 Task 对象塞进去），
    // 通常直接在 ViewModel 里创建即可，不需要专门写 Mapper 方法。
    // 但如果你想保持绝对的纯净，也可以在这里加 factory 方法，如下：

    /*
    fun toFocusingState(task: ThreadTask, energyLevel: EnergyLevel): HomeViewState.Focusing {
        return HomeViewState.Focusing(
            currentTask = task,
            energyLevel = energyLevel
        )
    }
    */
}
