package com.szenamartin.android.vehicle.di

import com.szenamartin.android.vehicle.di.scopes.ActivityScoped
import com.szenamartin.android.vehicle.map.MapActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector
    abstract fun provideMainActivity(): MapActivity

}
