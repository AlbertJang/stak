package `fun`.stak.model.company

import com.google.gson.annotations.SerializedName

data class TradeData(
    @SerializedName("month_chart")
    val monthChart: String,
    @SerializedName("change_price")
    val changePrice: String,
    val per: Double,
    @SerializedName("trade_price")
    val tradePrice: Long,
    val change: String,
    @SerializedName("day_chart")
    val dayChart: String,
    val summary: String?
)