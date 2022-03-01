package fintest.news.features.dashboard.data.repository.impl

import fintest.news.features.dashboard.data.api.ApiService
import fintest.news.features.dashboard.data.dto.*
import fintest.news.features.dashboard.data.repository.NewsRepository
import org.koin.core.KoinComponent
import retrofit2.Response

class NewsRepositoryImpl(
    private val apiService: ApiService //constructor injection
) : NewsRepository, KoinComponent {

    override suspend fun getHeadlines(): Response<HeadlinesDto> {
        return apiService.getHeadlines()
    }

    override suspend fun getCategory(category:String): Response<HeadlinesDto> {
        return apiService.getCategory(category)
    }

    override suspend fun getSearch(search:String): Response<HeadlinesDto> {
        return apiService.getSearch(search)
    }
}