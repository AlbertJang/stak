package `fun`.stak.model.diary

data class DiaryListResponse(
    val data: DiaryData,
    val message: String,
    val code: Int
)