package fintest.news.features.dashboard.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fintest.news.BuildConfig
import fintest.news.features.dashboard.data.dto.*
import fintest.news.features.dashboard.data.repository.impl.NewsRepositoryImpl
import fintest.news.features.dashboard.di.moshi
import fintest.news.features.dashboard.utils.*
import kotlinx.coroutines.launch
import okio.IOException
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.io.InterruptedIOException
import java.net.*

class NewsViewModel: ViewModel(), KoinComponent {
    private val networkHelper by inject<NetworkHelper>()
    private val newsRepository by inject<NewsRepositoryImpl>()
    private val resourceHelper by inject<ResourcesHelper>()

    //Status balikan server
    private val _headlines  = MutableLiveData<Resource<HeadlinesDto>>()
    //public untuk di observe di Fragment,LiveData
    val headlines: LiveData<Resource<HeadlinesDto>>
        get() = _headlines

    //Status balikan server
    private val _category  = MutableLiveData<Resource<HeadlinesDto>>()
    //public untuk di observe di Fragment,LiveData
    val category: LiveData<Resource<HeadlinesDto>>
        get() = _category

    //Status balikan server
    private val _search  = MutableLiveData<Resource<HeadlinesDto>>()
    //public untuk di observe di Fragment,LiveData
    val search: LiveData<Resource<HeadlinesDto>>
        get() = _search

    fun getHeadlines () {
        viewModelScope.launch {
            //loading
            _headlines.postValue(Resource.loading(null))
            //jika terkoneksi internet
            if (networkHelper.isNetworkConnected()) {
                try {
                    newsRepository.getHeadlines().let {
                        //2xx,3xx response code
                        if (it.isSuccessful) {
                            _headlines.postValue(Resource.success(it.body()))
                        } else {
                            val errorBody = it.errorBody()?.string()
                            if(!errorBody.isNullOrEmpty()){
                                try {
                                    val jsonAdapter = moshi.adapter(ResponseDto::class.java)
                                    val moshiBean = jsonAdapter.fromJson(errorBody)
                                    _headlines.postValue(Resource.error(moshiBean!!.message!!, null))
                                }catch (e :java.lang.Exception){
                                    _headlines.postValue(Resource.error("ERROR", null))
                                }
                            }
                            else _headlines.postValue(Resource.error("ERROR", null))
                        }
                    }
                } catch (e: IOException) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _headlines.value = Resource.error(resourceHelper.cannotConnectToServer, null)
                } catch (e: SocketTimeoutException) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _headlines.value = Resource.error(resourceHelper.timeOutConnetion, null)
                } catch (e: ConnectException) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _headlines.value = Resource.error(resourceHelper.cannotConnectToServer, null)
                } catch (e: UnknownHostException) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _headlines.value = Resource.error(resourceHelper.cannotConnectToServer, null)
                } catch (e: InterruptedIOException) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _headlines.value = Resource.error(resourceHelper.timeOutConnetion, null)
                } catch (e: NoRouteToHostException) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _headlines.value = Resource.error(resourceHelper.cannotConnectToServer, null)
                } catch (e: SocketException) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _headlines.value = Resource.error(resourceHelper.cannotConnectToServer, null)
                } catch (e:IllegalStateException) {
                    //inernal error
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _headlines.postValue(Resource.error(resourceHelper.errorSystem, null))
                } catch (e: Exception) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _headlines.postValue(Resource.error(resourceHelper.errorSystem, null))
                } catch (e: Throwable) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _headlines.postValue(Resource.error(resourceHelper.errorSystem, null))
                }
            } else {
                _headlines.value = Resource.error(resourceHelper.noInternetConnection, null)
            }
        }
    }

    fun getCategory (category: String) {
        viewModelScope.launch {
            //loading
            _category.postValue(Resource.loading(null))
            //jika terkoneksi internet
            if (networkHelper.isNetworkConnected()) {
                try {
                    newsRepository.getCategory(category).let {
                        //2xx,3xx response code
                        if (it.isSuccessful) {
                            _category.postValue(Resource.success(it.body()))
                        } else {
                            val errorBody = it.errorBody()?.string()
                            if(!errorBody.isNullOrEmpty()){
                                try {
                                    val jsonAdapter = moshi.adapter(ResponseDto::class.java)
                                    val moshiBean = jsonAdapter.fromJson(errorBody)
                                    _category.postValue(Resource.error(moshiBean!!.message!!, null))
                                }catch (e :java.lang.Exception){
                                    _category.postValue(Resource.error("ERROR", null))
                                }
                            }
                            else _category.postValue(Resource.error("ERROR", null))
                        }
                    }
                } catch (e: IOException) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _category.value = Resource.error(resourceHelper.cannotConnectToServer, null)
                } catch (e: SocketTimeoutException) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _category.value = Resource.error(resourceHelper.timeOutConnetion, null)
                } catch (e: ConnectException) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _category.value = Resource.error(resourceHelper.cannotConnectToServer, null)
                } catch (e: UnknownHostException) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _category.value = Resource.error(resourceHelper.cannotConnectToServer, null)
                } catch (e: InterruptedIOException) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _category.value = Resource.error(resourceHelper.timeOutConnetion, null)
                } catch (e: NoRouteToHostException) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _category.value = Resource.error(resourceHelper.cannotConnectToServer, null)
                } catch (e: SocketException) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _category.value = Resource.error(resourceHelper.cannotConnectToServer, null)
                } catch (e:IllegalStateException) {
                    //inernal error
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _category.postValue(Resource.error(resourceHelper.errorSystem, null))
                } catch (e: Exception) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _category.postValue(Resource.error(resourceHelper.errorSystem, null))
                } catch (e: Throwable) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _category.postValue(Resource.error(resourceHelper.errorSystem, null))
                }
            } else {
                _category.value = Resource.error(resourceHelper.noInternetConnection, null)
            }
        }
    }

    fun getSearch (search: String) {
        viewModelScope.launch {
            //loading
            _search.postValue(Resource.loading(null))
            //jika terkoneksi internet
            if (networkHelper.isNetworkConnected()) {
                try {
                    newsRepository.getSearch(search).let {
                        //2xx,3xx response code
                        if (it.isSuccessful) {
                            _search.postValue(Resource.success(it.body()))
                        } else {
                            val errorBody = it.errorBody()?.string()
                            if(!errorBody.isNullOrEmpty()){
                                try {
                                    val jsonAdapter = moshi.adapter(ResponseDto::class.java)
                                    val moshiBean = jsonAdapter.fromJson(errorBody)
                                    _search.postValue(Resource.error(moshiBean!!.message!!, null))
                                }catch (e :java.lang.Exception){
                                    _search.postValue(Resource.error("ERROR", null))
                                }
                            }
                            else _search.postValue(Resource.error("ERROR", null))
                        }
                    }
                } catch (e: IOException) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _search.value = Resource.error(resourceHelper.cannotConnectToServer, null)
                } catch (e: SocketTimeoutException) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _search.value = Resource.error(resourceHelper.timeOutConnetion, null)
                } catch (e: ConnectException) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _search.value = Resource.error(resourceHelper.cannotConnectToServer, null)
                } catch (e: UnknownHostException) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _search.value = Resource.error(resourceHelper.cannotConnectToServer, null)
                } catch (e: InterruptedIOException) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _search.value = Resource.error(resourceHelper.timeOutConnetion, null)
                } catch (e: NoRouteToHostException) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _search.value = Resource.error(resourceHelper.cannotConnectToServer, null)
                } catch (e: SocketException) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _search.value = Resource.error(resourceHelper.cannotConnectToServer, null)
                } catch (e:IllegalStateException) {
                    //inernal error
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _search.postValue(Resource.error(resourceHelper.errorSystem, null))
                } catch (e: Exception) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _search.postValue(Resource.error(resourceHelper.errorSystem, null))
                } catch (e: Throwable) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    _search.postValue(Resource.error(resourceHelper.errorSystem, null))
                }
            } else {
                _search.value = Resource.error(resourceHelper.noInternetConnection, null)
            }
        }
    }
}