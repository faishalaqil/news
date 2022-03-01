package fintest.news.features.dashboard.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HeadlinesDto(
    @field:Json(name = "status")
    val status: String?,
    @field:Json(name = "articles")
    val articles: List<ArticlesDto>?,
    @field:Json(name = "totalResults")
    val totalResults: Int?
) {

    @JsonClass(generateAdapter = true)
    data class ArticlesDto(
        @Json(name = "source")
        val source: SourceDto?,
        @Json(name = "author")
        val author: String?,
        @Json(name = "title")
        val title: String?,
        @Json(name = "description")
        val description: String?,
        @Json(name = "url")
        val url: String?,
        @Json(name = "urlToImage")
        val urlToImage: String?,
        @Json(name = "publishedAt")
        val publishedAt: String?,
        @Json(name = "content")
        val content: String?
    )

    @JsonClass(generateAdapter = true)
    data class SourceDto(
        @Json(name = "id")
        val id: String?,
        @Json(name = "name")
        val name: String?
    )
}