package `fun`.stak.model.company

import com.google.gson.annotations.SerializedName

data class CompanySimpleData(
    @SerializedName("company_set")
    val companySet: List<CompanySimple>
)