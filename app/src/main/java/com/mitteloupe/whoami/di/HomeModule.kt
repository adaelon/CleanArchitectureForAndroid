package com.mitteloupe.whoami.di

import com.mitteloupe.whoami.architecture.domain.UseCaseExecutor
import com.mitteloupe.whoami.coroutine.CoroutineContextProvider
import com.mitteloupe.whoami.home.domain.usecase.CreateUserTaskUseCase
import com.mitteloupe.whoami.home.domain.usecase.GetHomeConfigurationUseCase
import com.mitteloupe.whoami.home.presentation.mapper.ExceptionPresentationMapper
import com.mitteloupe.whoami.home.presentation.mapper.HomePresentationMapper
import com.mitteloupe.whoami.home.presentation.viewmodel.HomeViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object HomeModule {

    // ✅ 保留: 异常 Mapper (如果其他地方没提供的话)
    @Provides
    fun providesExceptionPresentationMapper() = ExceptionPresentationMapper()

    /**
     * ❌ 已删除旧的 UseCase 提供者 (GetConnectionDetails, SaveConnectionDetails)
     * 因为新的 UseCase (GetHomeConfiguration, CreateUserTask) 都在类上加了 @Inject，
     * 所以 Hilt 会自动识别，不需要在这里写 @Provides 方法。
     */

    // 🔄 更新: 适配新的 HomeViewModel 构造函数
    @Provides
    @Suppress("LongParameterList")
    fun providesHomeViewModel(
        getHomeConfigurationUseCase: GetHomeConfigurationUseCase,
        createUserTaskUseCase: CreateUserTaskUseCase,
        homePresentationMapper: HomePresentationMapper,
        useCaseExecutor: UseCaseExecutor,
        coroutineContextProvider: CoroutineContextProvider
    ) = HomeViewModel(
        getHomeConfigurationUseCase,
        createUserTaskUseCase,
        homePresentationMapper,
        useCaseExecutor,
        coroutineContextProvider
    )
}
