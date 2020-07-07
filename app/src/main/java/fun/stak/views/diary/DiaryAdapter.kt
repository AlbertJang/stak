package `fun`.stak.views.diary.adddiary.fragment.addcompany.adapter

import `fun`.stak.R
import `fun`.stak.model.diary.Diary
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_diary.view.*

class DiaryAdapter(
    private val diaryClick: (Diary?) -> Unit
) : PagedListAdapter<Diary, RecyclerView.ViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_diary, parent, false)
        return CompanyViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CompanyViewHolder).bind(getItem(position))
    }

    inner class CompanyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Diary?) {
            itemView.setOnClickListener {
                diaryClick(item)
            }

            // simple
            val resultPrice = item?.resultPrice

            if(resultPrice != null) {
                when {
                    resultPrice > 0 -> {
                        itemView.earnPriceTextView?.setTextColor(Color.RED)
                        itemView.earnPercentTextView?.setTextColor(Color.RED)
                    }
                    resultPrice < 0 -> {
                        itemView.earnPriceTextView?.setTextColor(Color.BLUE)
                        itemView.earnPercentTextView?.setTextColor(Color.BLUE)
                    }
                    else -> {
                        itemView.earnPriceTextView?.setTextColor(Color.BLACK)
                        itemView.earnPercentTextView?.setTextColor(Color.BLACK)
                    }
                }
            }

            itemView.companyNameTextView?.text = "${item?.company?.companyName}"
            itemView.earnPriceTextView?.text = "${item?.resultPrice ?: 0}원"
            itemView.earnPercentTextView?.text = "${item?.resultPer ?: 0}%"

           /* // price
            itemView.priceTextView?.text = "${item?.company?.tradeData?.tradePrice}"
            itemView.changePriceTextView?.text = "${item?.company?.tradeData?.changePrice}"
            itemView.changePercentTextView?.text = "${item?.company?.tradeData?.per}"

            // status
            itemView.amountTextView?.text
            itemView.dayTextView?.text

            // goal
            itemView.reBuyAmountTextView?.text = "${item?.rePrice}"
            itemView.goalPriceTextView?.text = "${item?.goalPrice}"
            itemView.cutPriceTextView?.text = "${item?.cutPrice}"

            // buy
            itemView.buyPriceTextView?.text
            itemView.buyAmountTextView?.text

            //sell
            itemView.sellPriceTextView?.text
            itemView.sellAmountTextView?.text

            // click
            itemView.payButton?.setOnClickListener {
                payClick(item)
            }

            itemView.deleteButton?.setOnClickListener {
                deleteClick(item)
            }*/
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Diary>() {
            override fun areItemsTheSame(oldItem: Diary, newItem: Diary): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: Diary, newItem: Diary): Boolean =
                oldItem.id == newItem.id
        }
    }
}