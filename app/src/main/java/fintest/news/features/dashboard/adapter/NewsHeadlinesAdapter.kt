package fintest.news.features.dashboard.adapter

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import fintest.news.R
import fintest.news.features.dashboard.data.dto.HeadlinesDto
import fintest.news.features.dashboard.di.moshi
import fintest.news.features.dashboard.utils.Constant
import kotlinx.android.synthetic.main.item_list_news.view.*

class NewsHeadlinesAdapter(private val context: Context,
                           private val data: List<HeadlinesDto.ArticlesDto>)
    : RecyclerView.Adapter<NewsHeadlinesAdapter.Holder>(){

    class Holder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_news, parent, false))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: Holder, position: Int) {

        val news = data[position]
        holder.itemView.news_title.text = news.title.toString()
        holder.itemView.news_desc.text = news.description
        holder.itemView.run {
            try {
                var requestOptions = RequestOptions()
                requestOptions = requestOptions.transforms(RoundedCorners(25))
                Glide.with(holder.itemView)
                    .load(Uri.parse(news.urlToImage))
                    .apply(requestOptions)
                    .into(news_poster)
            }catch (e:Exception){
//                var requestOptions = RequestOptions()
//                requestOptions = requestOptions.transforms(RoundedCorners(25))
//                Glide.with(holder.itemView)
//                    .load()
//                    .apply(requestOptions)
//                    .into(news_poster)
            }

        }

        holder.itemView.setOnClickListener {
            val jsonAdapter = moshi.adapter(HeadlinesDto.ArticlesDto::class.java)
            val bundle = bundleOf(Constant.DATA_NEWS to jsonAdapter.toJson(news))
            holder.itemView.findNavController().navigate(R.id.fragment_news, bundle)
        }
    }
}