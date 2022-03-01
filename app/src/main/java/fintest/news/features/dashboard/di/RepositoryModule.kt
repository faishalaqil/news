package fintest.news.features.dashboard.di

import fintest.news.features.dashboard.data.repository.impl.NewsRepositoryImpl
import org.koin.dsl.module

private const val BACKGROUND_DISPATCHER = "background_dispatcher"
private const val MAIN_DISPATCHER = "main_dispatcher"

val repoModule = module {
    single{
        //get() is constructor injection
        NewsRepositoryImpl(get())
    }
}
