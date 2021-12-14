package com.szenamartin.android.vehicle.di

import android.app.Application
import com.szenamartin.android.vehicle.MyApp
import com.szenamartin.android.vehicle.di.viewmodel.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(modules = [(AndroidInjectionModule::class), (AndroidSupportInjectionModule::class),
    (AppModule::class),(DataModule::class),(ViewModelModule::class),(ActivityBindingModule::class)])
interface AppComponent : AndroidInjector<MyApp> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): AppComponent.Builder

        fun build(): AppComponent
    }

}
