package `fun`.stak.model.diary

import com.google.gson.annotations.SerializedName

data class PayList(
    @SerializedName("pay_set")
    val paySet: List<Pay>
)