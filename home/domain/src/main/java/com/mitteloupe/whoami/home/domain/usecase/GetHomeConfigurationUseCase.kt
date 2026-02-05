package com.mitteloupe.whoami.home.domain.usecase

import com.mitteloupe.whoami.architecture.domain.usecase.BackgroundExecutingUseCase
import com.mitteloupe.whoami.coroutine.CoroutineContextProvider
import com.mitteloupe.whoami.home.domain.model.ActivityCategory
import com.mitteloupe.whoami.home.domain.model.EnergyLevel
import com.mitteloupe.whoami.home.domain.repository.ConfigurationRepository
import javax.inject.Inject

/**
 * 返回首页需要的所有静态配置数据
 */
data class HomeConfiguration(
    val categories: List<ActivityCategory>,
    val energyLevels: List<EnergyLevel>
)

class GetHomeConfigurationUseCase @Inject constructor(
    private val repository: ConfigurationRepository,
    coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<Unit, HomeConfiguration>(coroutineContextProvider) {

    override fun executeInBackground(request: Unit): HomeConfiguration {
        return HomeConfiguration(
            categories = repository.getAllCategories(),
            energyLevels = repository.getAllEnergyLevels()
        )
    }
}
