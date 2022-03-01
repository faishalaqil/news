package fintest.news.features.dashboard.data.api

import fintest.news.features.dashboard.data.dto.*
import fintest.news.features.dashboard.utils.Constant
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines")
    suspend fun getHeadlines(@Query("apiKey") apiKey:String = Constant.API_KEY,
                             @Query("country") country:String = "us"): Response<HeadlinesDto>

    @GET("top-headlines")
    suspend fun getCategory(@Query("category") category:String = "",
                            @Query("apiKey") apiKey:String = Constant.API_KEY,
                             @Query("country") country:String = "us"): Response<HeadlinesDto>

    @GET("top-headlines")
    suspend fun getSearch(@Query("q") search:String = "",
                            @Query("apiKey") apiKey:String = Constant.API_KEY,
                            @Query("country") country:String = "us"): Response<HeadlinesDto>
}