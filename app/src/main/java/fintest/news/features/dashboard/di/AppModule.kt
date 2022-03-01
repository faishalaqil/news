package fintest.news.features.dashboard.di

import android.content.Context
import android.util.Log
import com.squareup.moshi.Moshi
import fintest.news.features.dashboard.data.api.ApiService
import fintest.news.features.dashboard.utils.Constant
import fintest.news.features.dashboard.utils.NetworkHelper
import fintest.news.features.dashboard.utils.ResourcesHelper
import okhttp3.*
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import okio.IOException
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {
    single { provideOkHttpClient() }
    single { provideRetrofit(get(), Constant.BASE_URL) }
    single { provideApiService(get()) }
    single { provideNetworkHelper(androidContext()) }
    single { ResourcesHelper(androidContext()) }
}

private fun provideNetworkHelper(context: Context) = NetworkHelper(context)
private fun provideOkHttpClient(): OkHttpClient {

    val interceptorData: Interceptor = object : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            var newRequest: Request = chain.request()

                newRequest = newRequest.newBuilder()
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-Type", "application/json")
                    .header("Connection", "close")
                    .build()

            val response = chain.proceed(newRequest)
            if (response.body != null) {
                var originalBody = response.body!!.string()
                val contentType = response.body!!.contentType()

                Log.d("OkHttpClient","=============== RESPONSE ===============")
                Log.d("OkHttpClient", newRequest.url.encodedPath)
                Log.d("OkHttpClient", originalBody)
                Log.d("OkHttpClient", "---------------------------------------")

                return response.newBuilder().body(originalBody.toResponseBody(contentType)).build()
            }

            return response
        }
    }

    val okHttp = OkHttpClient.Builder()
        .addNetworkInterceptor(
            HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
                .setLevel(HttpLoggingInterceptor.Level.HEADERS)
        )
        .addInterceptor(interceptorData)
        .callTimeout(35, TimeUnit.SECONDS)
        .connectTimeout(35, TimeUnit.SECONDS)
        .readTimeout(35, TimeUnit.SECONDS)
        .writeTimeout(35, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .followRedirects(false)
        .followSslRedirects(false)
        .build()
    return okHttp
}

val moshi: Moshi = Moshi.Builder()
    //.addLast(KotlinJsonAdapterFactory())
    .build()

private fun provideRetrofit(
        okHttpClient: OkHttpClient,
        BASE_URL: String
): Retrofit =
        Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(okHttpClient)
                .build()

private fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)