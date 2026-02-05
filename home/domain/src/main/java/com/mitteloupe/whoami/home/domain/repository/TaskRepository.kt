package com.mitteloupe.whoami.home.domain.repository


import com.mitteloupe.whoami.home.domain.model.TaskStatus
import com.mitteloupe.whoami.home.domain.model.ThreadTask
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    /**
     * 👁️ 观察者模式：获取当前所有任务的实时流
     * 返回 Flow，意味着只要数据库有变动，这里会自动吐出最新的 List
     */
    fun getTasks(): Flow<List<ThreadTask>>

    /**
     * 🔢 查数：获取当前“进行中”的任务数量
     * 用于 UseCase 判断是否超过 3 个
     */
    fun getActiveTaskCount(): Int

    /**
     * ➕ 增：添加一个新任务
     */
    fun addTask(task: ThreadTask)

    /**
     * ✅ 改：更新任务状态 (比如变成 COMPLETED)
     */
    fun updateTaskStatus(taskId: String, status: TaskStatus)

    /**
     * 🗑️ 删：彻底删除 (可选，ADHD有时候需要彻底遗忘)
     */
    fun deleteTask(taskId: String)
}
