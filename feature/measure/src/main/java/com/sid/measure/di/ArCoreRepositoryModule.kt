package com.sid.measure.di

import com.sid.measure.data.repository.ArCoreRepository
import com.sid.measure.data.repository.ArCoreRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ArCoreRepositoryModule {
    @Binds
    fun bindArCoreRepository(impl: ArCoreRepositoryImpl): ArCoreRepository
}