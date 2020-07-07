package `fun`.stak.views.diary.detail

import `fun`.stak.R
import `fun`.stak.model.diary.Pay
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_detail_pay.view.*

class DetailPayAdapter(
    private val deleteClick: (Pay?) -> Unit
) : PagedListAdapter<Pay, RecyclerView.ViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_detail_pay, parent, false)
        return PayViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PayViewHolder).bind(getItem(position))
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Pay>() {
            override fun areItemsTheSame(oldItem: Pay, newItem: Pay): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: Pay, newItem: Pay): Boolean =
                oldItem.id == newItem.id
        }
    }

    inner class PayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Pay?) {
            itemView.payDateTextView?.text = "${item?.payDate} / "
            itemView.payPriceTextView?.text = "${item?.price}원 / "
            itemView.payAmountTextView?.text = "${item?.amount}주"

            itemView.deleteButton?.setOnClickListener {
                deleteClick(item)
            }
        }
    }
}