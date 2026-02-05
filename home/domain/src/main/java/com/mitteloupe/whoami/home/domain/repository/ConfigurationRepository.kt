package com.mitteloupe.whoami.home.domain.repository

import com.mitteloupe.whoami.home.domain.model.ActivityCategory
import com.mitteloupe.whoami.home.domain.model.EnergyLevel

/**
 * 这里的 Repository 更多是充当 "配置中心" 的角色
 * 也许未来你会想从服务器动态下发这些配置（比如圣诞节把 prompt 改成圣诞风）
 * 所以现在定义接口是很有远见的。
 */
interface ConfigurationRepository {
    // 获取所有支持的活动类别
    fun getAllCategories(): List<ActivityCategory>

    // 获取所有可选的能量等级
    fun getAllEnergyLevels(): List<EnergyLevel>
}
