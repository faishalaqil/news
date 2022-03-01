package fintest.news.features.dashboard.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fintest.news.R
import kotlinx.android.synthetic.main.item_list_categories.view.*

class NewsCategoriesAdapter (
    private val context: Context,
    private val listCategories: List<String>
    , val onItemCallBack: OnItemCallBack) : RecyclerView.Adapter<NewsCategoriesAdapter.Holder>(){

    class Holder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_categories, parent, false))
    }

    override fun getItemCount(): Int = listCategories.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
            holder.view.btn_category.text = listCategories[position]

        holder.view.btn_category.setOnClickListener {
            onItemCallBack.onItemClicked(position, listCategories[position])
        }
    }

    interface OnItemCallBack {
        fun onItemClicked(position: Int, category: String?)
    }
}