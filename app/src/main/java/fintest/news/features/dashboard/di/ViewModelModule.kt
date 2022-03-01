package fintest.news.features.dashboard.di

import fintest.news.features.dashboard.viewmodel.NewsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel{
        NewsViewModel()
    }
}