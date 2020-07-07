package `fun`.stak.model.diary

import com.google.gson.annotations.SerializedName

data class Pay(
    val amount: Long,
    val reason: String,
    @SerializedName("pay_type")
    val payType: String,
    val id: Long,
    val price: Long,
    @SerializedName("pay_date")
    val payDate: String
)