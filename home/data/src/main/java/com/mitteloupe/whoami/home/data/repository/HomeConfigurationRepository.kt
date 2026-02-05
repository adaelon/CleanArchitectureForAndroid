package com.mitteloupe.whoami.home.data.repository

import com.mitteloupe.whoami.home.domain.model.ActivityCategory
import com.mitteloupe.whoami.home.domain.model.EnergyLevel
import com.mitteloupe.whoami.home.domain.repository.ConfigurationRepository
import javax.inject.Inject

/**
 * 这是一个具体的数据仓库实现。
 * 它负责告诉 App 有哪些“任务分类”和“能量等级”可供选择。
 */
class HomeConfigurationRepository @Inject constructor() : ConfigurationRepository {

    /**
     * 获取所有可用的任务类别 (例如：学习、清洁、运动)
     */
    override fun getAllCategories(): List<ActivityCategory> {
        // 假设 ActivityCategory 是一个 Enum，我们直接返回所有选项
        return ActivityCategory.values().toList()
    }

    /**
     * 获取所有可用的能量等级 (例如：低、中、高)
     */
    override fun getAllEnergyLevels(): List<EnergyLevel> {
        // 假设 EnergyLevel 是一个 Enum，我们直接返回所有选项
        return EnergyLevel.values().toList()
    }
}
