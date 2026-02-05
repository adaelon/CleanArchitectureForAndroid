package com.mitteloupe.whoami.navigation.mapper

import com.mitteloupe.whoami.architecture.ui.navigation.mapper.NavigationEventDestinationMapper
import com.mitteloupe.whoami.architecture.ui.navigation.model.UiDestination
import com.mitteloupe.whoami.home.presentation.navigation.HomePresentationNavigationEvent
import com.mitteloupe.whoami.home.presentation.navigation.HomePresentationNavigationEvent.OnViewHistory
import com.mitteloupe.whoami.home.presentation.navigation.HomePresentationNavigationEvent.OnViewOpenSourceNotices
import com.mitteloupe.whoami.home.presentation.navigation.HomePresentationNavigationEvent.OnViewSettings // 👈 1. 引入这个新事件
import com.mitteloupe.whoami.ui.main.route.History
import com.mitteloupe.whoami.ui.main.route.OpenSourceNotices
// import com.mitteloupe.whoami.ui.main.route.Settings // 👈 如果你有 Settings 路由类，记得引入它

class HomeNavigationEventDestinationMapper :
    NavigationEventDestinationMapper<HomePresentationNavigationEvent>(
        HomePresentationNavigationEvent::class
    ) {
    override fun mapTypedEvent(navigationEvent: HomePresentationNavigationEvent): UiDestination =
        when (navigationEvent) {
            OnViewHistory -> history(null)
            OnViewOpenSourceNotices -> openSourceNotices()
            // 👇 2. 必须添加这一行分支
            OnViewSettings -> settings()
        }

    private fun history(highlightedIpAddress: String?): UiDestination =
        UiDestination { backStack -> backStack.add(History(highlightedIpAddress)) }

    private fun openSourceNotices(): UiDestination = UiDestination { backStack ->
        backStack.add(OpenSourceNotices)
    }

    // 👇 3. 实现跳转逻辑 (哪怕暂时是空的)
    private fun settings(): UiDestination = UiDestination { backStack ->
        // TODO: 如果你已经创建了 Settings 页面路由，把下面这行取消注释
        // backStack.add(Settings)

        // 暂时什么都不做，或者先跳转到 OpenSourceNotices 代替一下，防止报错
    }
}
