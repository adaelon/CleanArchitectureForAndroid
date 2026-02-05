package com.mitteloupe.whoami.home.domain.usecase

import com.mitteloupe.whoami.architecture.domain.usecase.BackgroundExecutingUseCase
import com.mitteloupe.whoami.coroutine.CoroutineContextProvider
import com.mitteloupe.whoami.home.domain.model.TaskStatus
import com.mitteloupe.whoami.home.domain.repository.TaskRepository
import javax.inject.Inject
import kotlinx.coroutines.runBlocking

/**
 * 继承 BackgroundExecutingUseCase (一次性后台任务)
 *
 * - 输入 (Request): String (也就是 taskId)
 * - 输出 (Response): Unit (不需要返回值)
 */
class CompleteThreadTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
    coroutineContextProvider: CoroutineContextProvider // 必须注入这个给基类用
) : BackgroundExecutingUseCase<String, Unit>(coroutineContextProvider) {

    /**
     * 这里 request 就是 taskId
     */
    override fun executeInBackground(request: String) {

        taskRepository.updateTaskStatus(taskId = request, status = TaskStatus.COMPLETED)

    }
}
