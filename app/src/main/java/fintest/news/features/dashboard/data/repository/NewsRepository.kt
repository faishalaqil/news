package fintest.news.features.dashboard.data.repository

import fintest.news.features.dashboard.data.dto.*
import retrofit2.Response

interface NewsRepository {
    suspend fun getHeadlines(): Response<HeadlinesDto>
    suspend fun getCategory(category:String): Response<HeadlinesDto>
    suspend fun getSearch(search:String): Response<HeadlinesDto>
}