package `fun`.stak.model.company

import com.google.gson.annotations.SerializedName

data class Company(
    val id: Long,
    @SerializedName("fund_type")
    val fundType: String,
    @SerializedName("company_name")
    val companyName: String,
    @SerializedName("companyupstream_set")
    val companyUpstreamSet: List<String>?,
    @SerializedName("companytheme_set")
    val companyThemeSet: List<String>?,
    @SerializedName("trade_data")
    val tradeData: TradeData,
    val status: String
)