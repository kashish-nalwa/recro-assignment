package com.android.recroassignment.di.articles

import androidx.lifecycle.ViewModel
import com.android.recroassignment.di.ViewModelKey
import com.android.recroassignment.ui.articles.ArticlesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ArticlesViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(ArticlesViewModel::class)
    abstract fun bindsMainViewModel(viewModel: ArticlesViewModel): ViewModel
}
