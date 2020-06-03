package com.android.recroassignment.di

import androidx.lifecycle.ViewModelProvider
import com.android.recroassignment.viewmodel.ViewModelProviderFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindsViewModel(factory: ViewModelProviderFactory): ViewModelProvider.Factory
}