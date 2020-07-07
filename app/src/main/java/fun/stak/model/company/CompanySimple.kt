package `fun`.stak.model.company

import com.google.gson.annotations.SerializedName

data class CompanySimple(
    val id: Long,
    @SerializedName("fund_type")
    val fundType: String,
    val upstream: String,
    @SerializedName("company_name")
    val companyName: String,
    @SerializedName("memo_count")
    val memoCount: Long,
    @SerializedName("analysis_count")
    val analysisCount: Long,
    val status: String
)