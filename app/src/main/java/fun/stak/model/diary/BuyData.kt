package `fun`.stak.model.diary

import com.google.gson.annotations.SerializedName

data class BuyData(
    val id: Long,
    @SerializedName("buy_reason")
    val buyReason: String,
    @SerializedName("buy_price")
    val buyPrice: Long,
    @SerializedName("buy_amount")
    val buyAmount: Long,
    @SerializedName("buy_date")
    val buyDate: String,
    val diary: Long
)