package com.mitteloupe.whoami.di

import android.content.Context
import com.mitteloupe.whoami.analytics.Analytics
import com.mitteloupe.whoami.architecture.ui.navigation.mapper.NavigationEventDestinationMapper
import com.mitteloupe.whoami.home.presentation.navigation.HomePresentationNavigationEvent
import com.mitteloupe.whoami.home.presentation.viewmodel.HomeViewModel
import com.mitteloupe.whoami.home.ui.di.HomeDependencies
import com.mitteloupe.whoami.home.ui.mapper.HomeNotificationUiMapper
import com.mitteloupe.whoami.navigation.mapper.HomeNavigationEventDestinationMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext

// 定义别名方便阅读
private typealias HomeNavigationMapper =
    NavigationEventDestinationMapper<HomePresentationNavigationEvent>

@Module
@InstallIn(ActivityComponent::class)
object HomeUiModule {

    // ✅ 保留 1: 导航映射器 (用于处理跳转历史记录等)
    @Provides
    @JvmSuppressWildcards
    fun providesHomeNavigationEventDestinationMapper(): HomeNavigationMapper =
        HomeNavigationEventDestinationMapper()

    // ✅ 保留 2: 通知映射器 (用于弹出 Toast 或 Snackbar)
    @Provides
    fun providesHomeNotificationUiMapper(@ActivityContext context: Context) =
        HomeNotificationUiMapper(context)

    // ❌ 已删除: providesHomeViewStateUiMapper (不再需要)
    // ❌ 已删除: providesErrorUiMapper (不再需要)
    // ❌ 已删除: providesConnectionDetailsPresentationMapper (不再需要)
    // ❌ 已删除: providesConnectionDetailsUiMapper (不再需要)

    // ✅ 核心修改: 提供 HomeDependencies
    @Provides
    fun providesHomeDependencies(
        homeViewModel: HomeViewModel,
        homeNavigationMapper: @JvmSuppressWildcards HomeNavigationMapper,
        homeNotificationMapper: HomeNotificationUiMapper,
        analytics: Analytics
    ) = HomeDependencies(
        homeViewModel = homeViewModel,
        homeNavigationMapper = homeNavigationMapper,
        homeNotificationMapper = homeNotificationMapper,
        analytics = analytics
    )
}
