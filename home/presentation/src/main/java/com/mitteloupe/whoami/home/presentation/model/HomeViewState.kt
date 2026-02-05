package com.mitteloupe.whoami.home.presentation.model

import com.mitteloupe.whoami.home.domain.model.ActivityCategory
import com.mitteloupe.whoami.home.domain.model.EnergyLevel
import com.mitteloupe.whoami.home.domain.model.ThreadTask

/**
 * UI 状态机：告诉 Compose 界面此刻应该画什么
 */
sealed interface HomeViewState {

    // 🔄 1. 加载中 (比如刚打开 App 正在读配置)
    data object Loading : HomeViewState

    // 🎛️ 2. 选择模式 (初始页)
    // 界面显示：三个类别大按钮 (学习/清洁/运动) + 能量选择器 (高/中/低)
    data class Selection(
        // 如果你的类别是动态的（比如后台下发），这里可以放 List
        // 如果是写死的 Enum，这里留空或者是 object 也可以
        val lastSelectedCategory: ActivityCategory? = null
    ) : HomeViewState

    // ⌨️ 3. 输入模式 (决定下一步)
    // 界面显示：一个大大的输入框 + 下方的灵感胶囊 (Chips)
    data class Inputting(
        val category: ActivityCategory, // 当前是在哪个赛道 (比如 "清洁")
        val energyLevel: EnergyLevel,   // 当前的电量 (决定小猫多久叫一次)
        val parentGoal: String?,        // 大目标 (比如 "清理书桌")
        val suggestions: List<String> = emptyList() // 灵感胶囊：["先扔垃圾", "收起水杯"]
    ) : HomeViewState

    // 🧶 4. 专注模式 (执行中)
    // 界面显示：当前任务的大字 + 右下角的奔跑小猫
    data class Focusing(
        val currentTask: ThreadTask,    // 当前正在做的任务对象
        val energyLevel: EnergyLevel,   // 用于 UI 倒计时显示
        val isCatMeowing: Boolean = false // 如果超时了，这里变成 true，UI 让猫叫
    ) : HomeViewState

    // ❌ 5. 出错模式
    // 界面显示：红色错误提示
    data class Error(val message: String) : HomeViewState
}
