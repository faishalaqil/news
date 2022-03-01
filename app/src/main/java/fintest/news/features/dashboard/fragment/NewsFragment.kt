package fintest.news.features.dashboard.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fintest.news.R
import fintest.news.features.dashboard.viewmodel.NewsViewModel
import kotlinx.android.synthetic.main.fragment_news.view.*
import org.koin.android.viewmodel.ext.android.viewModel
import androidx.recyclerview.widget.RecyclerView
import android.os.Handler
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import fintest.news.features.dashboard.data.dto.HeadlinesDto
import fintest.news.features.dashboard.di.moshi
import fintest.news.features.dashboard.utils.Constant


class NewsFragment : Fragment() {
    private val viewModel : NewsViewModel by viewModel()
    lateinit var fragmentView: View
    lateinit var dataNews : HeadlinesDto.ArticlesDto

    override fun onCreate(savedInstanceState: Bundle?) {
        val jsonAdapter = moshi.adapter(HeadlinesDto.ArticlesDto::class.java)
        arguments?.let { it -> dataNews =
            jsonAdapter.fromJson(it.getString(Constant.DATA_NEWS))!!
        }
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_news, container, false)
        fragmentView = view

        fragmentView.webview.settings.javaScriptEnabled = true

        fragmentView.webview.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url!!)
                return true
            }
        }
        dataNews.url?.let { fragmentView.webview.loadUrl(it) }
        return view
    }
}