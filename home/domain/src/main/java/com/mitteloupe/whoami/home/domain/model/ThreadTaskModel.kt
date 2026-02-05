package com.mitteloupe.whoami.home.domain.model

/**
 * 1. 定义任务的状态
 * 使用枚举 (Enum) 来限制状态，防止出现 "doing" 这种拼写错误的字符串。
 */
enum class TaskStatus {
    PENDING,   // 待办 (线结还未解开)
    COMPLETED  // 完成 (线结已解开)
}

/**
 * 2. 核心实体：ThreadTask (线结)
 * 这是你业务逻辑中最基本的数据单元。
 */
data class ThreadTask(
    val id: String,           // 唯一标识 (通常用 UUID)
    val content: String,      // 任务内容，例如 "阅读论文第一页"
    val parentId: String?,     // 关联的大目标，例如 "学习 PyTorch"
    val status: TaskStatus = TaskStatus.PENDING, // 默认状态是待办
    val createdAt: Long = System.currentTimeMillis() // 记录创建时间，方便排序
)
