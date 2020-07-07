package `fun`.stak.model.diary

import com.google.gson.annotations.SerializedName

data class DiaryData(
    @SerializedName("diary_set")
    val diarySet: List<Diary>
)