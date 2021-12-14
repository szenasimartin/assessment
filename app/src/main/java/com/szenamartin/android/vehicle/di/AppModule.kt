package com.szenamartin.android.vehicle.di

import android.app.Application
import com.szenamartin.android.vehicle.utils.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable


@Module
class AppModule {

    @Provides
    fun provideContext(application: Application) = application.applicationContext

    @Provides
    internal fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()

    @Provides
    internal fun provideSchedulerProvider(): SchedulerProvider = SchedulerProvider()

}
