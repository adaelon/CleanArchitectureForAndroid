package com.mitteloupe.whoami.home.domain.usecase

import com.mitteloupe.whoami.architecture.domain.usecase.BackgroundExecutingUseCase
import com.mitteloupe.whoami.coroutine.CoroutineContextProvider
import com.mitteloupe.whoami.home.domain.model.ThreadTask
import com.mitteloupe.whoami.home.domain.model.TaskStatus
import java.util.UUID
import javax.inject.Inject

/**
 * 输入：用户输入的文本 (content) 和 当前的大目标 (parentGoal)
 */
data class UserTaskRequest(
    val content: String,    // 用户填的："打开书"
    val parentGoal: String? // 大目标："复习考试" (可选)
)

class CreateUserTaskUseCase @Inject constructor(
    coroutineContextProvider: CoroutineContextProvider
    // 如果需要存数据库，这里注入 Repository
    // private val repository: TaskRepository
) : BackgroundExecutingUseCase<UserTaskRequest, ThreadTask>(coroutineContextProvider) {

    override fun executeInBackground(request: UserTaskRequest): ThreadTask {
        // 这里不做复杂逻辑，只是封装对象
        // 如果需要存库，就在这里 repository.addTask(...)

        return ThreadTask(
            id = UUID.randomUUID().toString(),
            content = request.content,
            parentId = request.parentGoal,
            status = TaskStatus.PENDING,
            createdAt = System.currentTimeMillis()
        )
    }
}
