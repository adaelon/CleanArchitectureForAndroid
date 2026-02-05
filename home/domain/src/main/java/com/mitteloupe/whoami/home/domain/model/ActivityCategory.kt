package com.mitteloupe.whoami.home.domain.model

/**
 * 对应你设想的 A. ActivityCategory (线头)
 * 使用 enum 因为这三个类别是固定的
 */
enum class ActivityCategory(
    val prompt: String // 对应的引导语
) {
    LEARNING("想学点什么新东西？"),
    CLEANING("想让哪里变干净？"),
    MOVEMENT("想怎么动一动？");

    // 可以扩展：获取对应的图标资源ID等
}
