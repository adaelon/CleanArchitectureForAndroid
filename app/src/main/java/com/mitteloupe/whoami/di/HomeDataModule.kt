package com.mitteloupe.whoami.di

import com.mitteloupe.whoami.home.data.repository.HomeConfigurationRepository
import com.mitteloupe.whoami.home.domain.repository.ConfigurationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class HomeDataModule {

    /**
     * ✅ 核心修复：
     * 1. 使用 abstract class (而不是 object)，这样才能包含 abstract 方法。
     * 2. 绑定 ConfigurationRepository 到具体的 HomeConfigurationRepository 实现。
     */
    @Binds
    abstract fun bindConfigurationRepository(
        impl: HomeConfigurationRepository
    ): ConfigurationRepository

    // 🗑️ 已删除：所有 Connection/IP 相关的 Provider
    // (ConnectionDetailsRepository, IpAddressDataSource 等)
    // 这些属于旧功能，如果不删除，会导致 "Unresolved Reference" 或者是 Dagger 依赖图报错。
}
