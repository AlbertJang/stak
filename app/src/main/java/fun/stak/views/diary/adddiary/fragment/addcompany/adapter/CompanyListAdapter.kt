package `fun`.stak.views.diary.adddiary.fragment.addcompany.adapter

import `fun`.stak.R
import `fun`.stak.model.company.CompanySimple
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_company_simple.view.*

class CompanyListAdapter(private val companyClickListener: (CompanySimple?) -> Unit) : PagedListAdapter<CompanySimple, RecyclerView.ViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_company_simple, parent, false)
        return CompanyViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CompanyViewHolder).bind(getItem(position))
    }

    inner class CompanyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(item: CompanySimple?) {
            itemView.setOnClickListener {
                companyClickListener(item)
            }

            itemView.companyNameTextView?.text = "${item?.companyName}"
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<CompanySimple>() {
            override fun areItemsTheSame(oldItem: CompanySimple, newItem: CompanySimple): Boolean = oldItem == newItem
            override fun areContentsTheSame(oldItem: CompanySimple, newItem: CompanySimple): Boolean = oldItem.id == newItem.id
        }
    }
}