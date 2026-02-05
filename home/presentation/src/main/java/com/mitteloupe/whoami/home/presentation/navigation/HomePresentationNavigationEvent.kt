package com.mitteloupe.whoami.home.presentation.navigation

import com.mitteloupe.whoami.architecture.presentation.navigation.PresentationNavigationEvent

sealed interface HomePresentationNavigationEvent : PresentationNavigationEvent {

    // 📜 1. 跳转到历史记录 (Roadmap)
    // 用户想看今天到底干了什么的时候点击
    data object OnViewHistory : HomePresentationNavigationEvent

    // ℹ️ 2. 跳转到开源许可/关于页面 (保留)
    data object OnViewOpenSourceNotices : HomePresentationNavigationEvent

    // ⚙️ 3. (可选建议) 跳转到设置页面
    // 比如用户想调整小猫的音量，或者修改默认的能量等级时长
    data object OnViewSettings : HomePresentationNavigationEvent

    // ❌ 已删除: OnSavedDetails
    // 因为现在“完成任务”只是刷新当前页面的状态 (变成 Inputting)，不需要跳转页面。
}
