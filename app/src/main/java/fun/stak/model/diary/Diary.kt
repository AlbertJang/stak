package `fun`.stak.model.diary

import `fun`.stak.model.company.Company
import com.google.gson.annotations.SerializedName

data class Diary(
    val id: Long,
    val company: Company,
    @SerializedName("first_buy_date")
    val firstBuyDate: String,
    @SerializedName("last_sell_date")
    val lastSellDate: String?,
    @SerializedName("buy_amount")
    val buyAmount: Long?,
    @SerializedName("avg_buy_price")
    val avgBuyPrice: Long?,
    @SerializedName("sell_amount")
    val sellAmount: Long?,
    @SerializedName("avg_sell_price")
    val avgSellPrice: Long?,
    @SerializedName("re_price")
    val rePrice: Long?,
    val etc: String?,
    @SerializedName("result_per")
    val resultPer: Double?,
    @SerializedName("result_price")
    val resultPrice: Long?,
    @SerializedName("goal_price")
    val goalPrice: Long,
    @SerializedName("cut_price")
    val cutPrice: Long,
    @SerializedName("is_end")
    val isEnd: Boolean,
    @SerializedName("reg_date")
    val regDate: String
)