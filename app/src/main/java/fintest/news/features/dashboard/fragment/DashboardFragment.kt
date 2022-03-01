package fintest.news.features.dashboard.fragment

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import fintest.news.R
import fintest.news.features.dashboard.adapter.NewsCategoriesAdapter
import fintest.news.features.dashboard.adapter.NewsHeadlinesAdapter
import fintest.news.features.dashboard.data.dto.HeadlinesDto
import fintest.news.features.dashboard.utils.Status
import fintest.news.features.dashboard.viewmodel.NewsViewModel
import kotlinx.android.synthetic.main.fragment_dashboard.view.*
import org.koin.android.viewmodel.ext.android.viewModel
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.EditorInfo

import android.widget.TextView

import android.widget.EditText
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast


class DashboardFragment : Fragment(), NewsCategoriesAdapter.OnItemCallBack {

    private val viewModel : NewsViewModel by viewModel()
    lateinit var fragmentView: View
    private var listHeadlines = mutableListOf<HeadlinesDto.ArticlesDto>()
    lateinit var swipeRefresh: SwipeRefreshLayout
    var arrayCategories : List<String> = listOf("top headlines","business","entertainment",
        "general","health","science","sports","technology")

    override fun onCreate(savedInstanceState: Bundle?) {

        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                AlertDialog.Builder(requireContext())
                        .setMessage("Are you sure you want to exit?")
                        .setCancelable(false)
                        .setPositiveButton(getString(R.string.common_yes)) { _, _ ->
                            requireActivity().moveTaskToBack(true)
                            requireActivity().finish()
                        }
                        .setNegativeButton(getString(R.string.common_no), null)
                        .show()
            }
        })
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        //setHasOptionsMenu(false)
        requireActivity().window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        fragmentView = view
        // Hide the status bar.
        (activity as AppCompatActivity).supportActionBar?.hide()
        swipeRefresh = view.swipe_refresh

        swipeRefresh.setOnRefreshListener {
            Handler().postDelayed(Runnable {
                swipeRefresh.isRefreshing = false
                viewModel.getHeadlines()
            }, 2000)
        }

        fragmentView.bt_search.setOnClickListener {
            val data = fragmentView.et_search.text.toString()
            fragmentView.et_search.isFocusable = false
            hideKeyboardFrom(requireContext(), fragmentView)
            viewModel.getSearch(data)
        }

        fragmentView.et_search.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                fragmentView.bt_search.performClick()
                return@OnEditorActionListener true
            }
            false
        })

        observerHeadlines()
        observerCategory()
        observerSearch()
        viewModel.getHeadlines()

        fragmentView.list_categories?.layoutManager = LinearLayoutManager(this.activity, LinearLayoutManager.HORIZONTAL, false)
        val adapter = NewsCategoriesAdapter(requireContext(), arrayCategories, this)
        adapter.notifyDataSetChanged()
        fragmentView.list_categories?.adapter = adapter

        return view
    }

    private fun observerHeadlines() {
        viewModel.headlines.observe(viewLifecycleOwner,
            androidx.lifecycle.Observer {
                when(it.status) {
                    Status.SUCCESS -> {
                        listHeadlines.clear()
                        fragmentView.list_news?.layoutManager = LinearLayoutManager(this.activity, LinearLayoutManager.VERTICAL, false)
                        it.data?.articles?.iterator()?.forEach {
                            listHeadlines.add(it)
                        }
                        val adapter = NewsHeadlinesAdapter(requireContext(), listHeadlines)
                        adapter.notifyDataSetChanged()
                        fragmentView.list_news?.adapter = adapter
                        Log.d("TAG", "observerLogin: sukses" )
                    }
                    Status.LOADING -> {
                        Log.d("TAG", "observerLogin: loading" )
                    }
                    Status.ERROR -> {
                        if (!it.message.isNullOrEmpty()) {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        }
                        else Toast.makeText(requireContext(), "ERROR", Toast.LENGTH_SHORT).show()
                        Log.d("TAG", "observerLogin: error" )
                    }
                    Status.DONE -> {
                        Log.d("TAG", "observerLogin: done" )
                    }
                }
            })
    }

    private fun observerCategory() {
        viewModel.category.observe(viewLifecycleOwner,
            androidx.lifecycle.Observer {
                when(it.status) {
                    Status.SUCCESS -> {
                        listHeadlines.clear()
                        fragmentView.list_news?.layoutManager = LinearLayoutManager(this.activity, LinearLayoutManager.VERTICAL, false)
                        it.data?.articles?.iterator()?.forEach {
                            listHeadlines.add(it)
                        }
                        val adapter = NewsHeadlinesAdapter(requireContext(), listHeadlines)
                        adapter.notifyDataSetChanged()
                        fragmentView.list_news?.adapter = adapter
                        Log.d("TAG", "observerLogin: sukses" )
                    }
                    Status.LOADING -> {
                        Log.d("TAG", "observerLogin: loading" )
                    }
                    Status.ERROR -> {
                        if (!it.message.isNullOrEmpty()) {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        }
                        else Toast.makeText(requireContext(), "ERROR", Toast.LENGTH_SHORT).show()
                        Log.d("TAG", "observerLogin: error" )
                    }
                    Status.DONE -> {
                        Log.d("TAG", "observerLogin: done" )
                    }
                }
            })
    }

    private fun observerSearch() {
        viewModel.search.observe(viewLifecycleOwner,
            androidx.lifecycle.Observer {
                when(it.status) {
                    Status.SUCCESS -> {
                        listHeadlines.clear()
                        fragmentView.list_news?.layoutManager = LinearLayoutManager(this.activity, LinearLayoutManager.VERTICAL, false)
                        it.data?.articles?.iterator()?.forEach {
                            listHeadlines.add(it)
                        }
                        val adapter = NewsHeadlinesAdapter(requireContext(), listHeadlines)
                        adapter.notifyDataSetChanged()
                        fragmentView.list_news?.adapter = adapter
                        Log.d("TAG", "observerLogin: sukses" )
                    }
                    Status.LOADING -> {
                        Log.d("TAG", "observerLogin: loading" )
                    }
                    Status.ERROR -> {
                        if (!it.message.isNullOrEmpty()) {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        }
                        else Toast.makeText(requireContext(), "ERROR", Toast.LENGTH_SHORT).show()
                        Log.d("TAG", "observerLogin: error" )
                    }
                    Status.DONE -> {
                        Log.d("TAG", "observerLogin: done" )
                    }
                }
            })
    }

    override fun onItemClicked(position: Int, category: String?) {
        if (category == "top headlines") viewModel.getHeadlines()
        else viewModel.getCategory(category!!)
    }

    fun hideKeyboardFrom(context: Context, view: View) {
        val imm: InputMethodManager =
            context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}