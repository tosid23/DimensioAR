package com.sid.home.di

import com.sid.home.data.repository.MeasurementRepository
import com.sid.home.data.repository.MeasurementRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class HomeModule {
    @Binds
    abstract fun bindMeasurementRepository(
        measurementRepositoryImpl: MeasurementRepositoryImpl
    ): MeasurementRepository
}