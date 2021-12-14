package com.szenamartin.android.vehicle.di

import com.szenamartin.android.vehicle.data.VehicleApi
import com.szenamartin.android.vehicle.data.VehicleRemoteDataSource
import com.szenamartin.android.vehicle.data.VehicleRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
class DataModule {

    @Provides
    @Singleton
    fun provideMoviesRepository(vehicleApi: VehicleApi) = VehicleRepository(VehicleRemoteDataSource(vehicleApi))


}
