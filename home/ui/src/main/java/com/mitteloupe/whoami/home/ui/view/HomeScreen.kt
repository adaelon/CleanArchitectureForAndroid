package com.mitteloupe.whoami.home.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mitteloupe.whoami.analytics.Analytics
import com.mitteloupe.whoami.analytics.AnalyticsEvent
import com.mitteloupe.whoami.architecture.ui.view.ScreenEnterObserver
import com.mitteloupe.whoami.home.domain.model.ActivityCategory
import com.mitteloupe.whoami.home.domain.model.EnergyLevel
import com.mitteloupe.whoami.home.presentation.model.HomeViewState
import com.mitteloupe.whoami.home.ui.R
import com.mitteloupe.whoami.home.ui.content.SelectionContent
import com.mitteloupe.whoami.home.ui.content.SmartCat
import com.mitteloupe.whoami.home.ui.di.HomeDependencies
import com.mitteloupe.whoami.home.ui.view.widget.LoadingAnimationContainer

@Composable
fun HomeDependencies.Home(backStack: MutableList<Any>, modifier: Modifier = Modifier) {
    // 1. 监听进入事件
    ScreenEnterObserver {
        analytics.logScreen("Home")
        homeViewModel.onEnter()
    }

    // 2. 监听 ViewModel 事件 (导航/通知)
    ViewModelObserver(backStack)

    // 3. 收集 UI 状态
    // 注意：这里直接收集 Presentation 层的 State，不再需要 UI Mapper 转换
    val viewState by homeViewModel.viewState.collectAsState(HomeViewState.Loading)

    HomeContents(
        viewState = viewState,
        analytics = analytics,
        // 绑定 ViewModel 的动作
        onCategorySelected = homeViewModel::onCategorySelected,
        onTaskStarted = homeViewModel::onTaskStarted,
        onTaskCompleted = homeViewModel::onTaskCompleted,
        onViewHistoryClick = homeViewModel::onViewHistoryAction,
        onOpenSourceNoticesClick = homeViewModel::onOpenSourceNoticesAction,
        modifier = modifier
    )
}

@Composable
private fun HomeContents(
    viewState: HomeViewState,
    analytics: Analytics,
    onCategorySelected: (ActivityCategory, EnergyLevel) -> Unit,
    onTaskStarted: (String, String?) -> Unit,
    onTaskCompleted: () -> Unit,
    onViewHistoryClick: () -> Unit,
    onOpenSourceNoticesClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // 根据状态显示不同的全屏内容
        when (viewState) {
            is HomeViewState.Loading -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center // 顺便让它居中
                ) {
                    LoadingAnimationContainer(visible = true)
                }
            }

            is HomeViewState.Selection -> {
                // 🆕 首页：智能小猫 + 三个大按钮
                SelectionContent(
                    onCategorySelected = onCategorySelected,
                    modifier = Modifier.fillMaxSize()
                )
            }

            is HomeViewState.Inputting -> {
                // TODO: 下一步我们会创建 InputtingContent
                // 暂时先用简单的 Text 占位，防止编译报错
                Column(
                    modifier = Modifier.fillMaxSize().padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("这里是输入页面 (Inputting)", style = MaterialTheme.typography.titleLarge)
                    Text("当前类别: ${viewState.category}")
                }
            }

            is HomeViewState.Focusing -> {
                // TODO: 下一步我们会创建 FocusingContent
                // 这里暂时展示一个简单的专注界面占位
                Column(
                    modifier = Modifier.fillMaxSize().padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // 复用 SmartCat，根据是否 meowing 决定状态
                    SmartCat(
                        lottieResId = if (viewState.isCatMeowing) R.raw.loadercat else R.raw.loadercat, // 暂时都用 loader_cat
                        message = if (viewState.isCatMeowing) "喵！你还在吗？" else "专注中..."
                    )
                    Text(
                        text = viewState.currentTask.content,
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(top = 24.dp)
                    )
                }
            }

            is HomeViewState.Error -> {
//                ErrorContentContainer(
//                    visible = true,
//                    errorText = viewState.message // 使用新的 message 字段
//                )
            }
        }
    }
}

// --- Preview 部分 ---

@Preview
@Composable
private fun PreviewSelection() {
    HomeContents(
        viewState = HomeViewState.Selection(),
        analytics = object : Analytics {
            override fun logScreen(screenName: String) = Unit
            override fun logEvent(event: AnalyticsEvent) = Unit
        },
        onCategorySelected = { _, _ -> },
        onTaskStarted = { _, _ -> },
        onTaskCompleted = {},
        onViewHistoryClick = {},
        onOpenSourceNoticesClick = {}
    )
}

@Preview
@Composable
private fun PreviewLoading() {
    HomeContents(
        viewState = HomeViewState.Loading,
        analytics = object : Analytics {
            override fun logScreen(screenName: String) = Unit
            override fun logEvent(event: AnalyticsEvent) = Unit
        },
        onCategorySelected = { _, _ -> },
        onTaskStarted = { _, _ -> },
        onTaskCompleted = {},
        onViewHistoryClick = {},
        onOpenSourceNoticesClick = {}
    )
}
