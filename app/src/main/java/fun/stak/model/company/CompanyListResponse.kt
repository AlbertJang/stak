package `fun`.stak.model.company

data class CompanyListResponse(
    val code: Int,
    val message: String,
    val data: CompanySimpleData
)