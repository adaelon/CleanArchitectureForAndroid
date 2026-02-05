package com.mitteloupe.whoami.home.domain.usecase

import com.mitteloupe.whoami.architecture.domain.usecase.BackgroundExecutingUseCase
import com.mitteloupe.whoami.coroutine.CoroutineContextProvider
import com.mitteloupe.whoami.home.domain.model.ThreadTask
import com.mitteloupe.whoami.home.domain.repository.TaskRepository
import javax.inject.Inject

/**
 * 继承 BackgroundExecutingUseCase (一次性任务)
 * 输入 (Request): ThreadTask (要添加的任务对象)
 * 输出 (Response): Unit (没有返回值，或者返回 ID)
 */
class AddThreadTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
    coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<ThreadTask, Unit>(coroutineContextProvider) {

    override fun executeInBackground(request: ThreadTask) {
        // 1. 检查数量限制 (Rule of 3)
        // 注意：BackgroundExecutingUseCase 通常是同步的，
        // 我们需要在 Repository 增加一个同步方法或者阻塞获取 count
        // 既然这个基类帮你切到了后台线程(IO)，你可以放心地调用 suspend 函数 (如果基类支持)
        // 或者调用阻塞方法。

        // 假设 Repository 有检查逻辑，或者你在这里简单调用
        // val count = taskRepository.getActiveTaskCountBlocking()
        // if (count >= 3) throw Exception(...)

        // 2. 直接保存
        // 时间 (createdAt) 已经在 ViewModel 创建 request 对象时填好了，
        // 或者在 RepositoryImpl 这一层填入。
        taskRepository.addTask(request)
    }
}
