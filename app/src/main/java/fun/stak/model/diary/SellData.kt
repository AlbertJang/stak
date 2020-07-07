package `fun`.stak.model.diary

import com.google.gson.annotations.SerializedName

data class SellData(
    val id: Long,
    @SerializedName("sell_reason")
    val sellReason: String,
    @SerializedName("sell_price")
    val sellPrice: Long,
    @SerializedName("sell_amount")
    val sellAmount: Long,
    @SerializedName("sell_date")
    val sellDate: String,
    val diary: Long
)