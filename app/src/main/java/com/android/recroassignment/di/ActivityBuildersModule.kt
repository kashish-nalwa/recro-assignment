package com.android.recroassignment.di

import com.android.recroassignment.di.main.MainModule
import com.android.recroassignment.di.main.MainViewModelsModule
import com.android.recroassignment.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {
    @ContributesAndroidInjector(
        modules = [MainModule::class, MainViewModelsModule::class]
    )
    abstract fun contributesMainActivity(): MainActivity
}