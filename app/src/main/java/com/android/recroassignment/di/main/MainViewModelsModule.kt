package com.android.recroassignment.di.main

import androidx.lifecycle.ViewModel
import com.android.recroassignment.di.ViewModelKey
import com.android.recroassignment.ui.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindsMainViewModel(viewModel: MainViewModel): ViewModel
}
