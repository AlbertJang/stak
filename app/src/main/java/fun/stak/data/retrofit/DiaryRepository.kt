package `fun`.stak.data.retrofit

import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.awaitResponse

class DiaryRepository(private val retrofit: Retrofit) {
    suspend fun getDiaryList(filterKey: String, searchTxt: String, page: Int, size: Int) =
        retrofit.create(DiaryApi::class.java).getDiaryList(filterKey, searchTxt, page, size)
            .awaitResponse()
    suspend fun addDiary(request: RequestBody) = retrofit.create(DiaryApi::class.java).addDiary(request).awaitResponse()
    suspend fun deleteDiary(id: Long?) = retrofit.create(DiaryApi::class.java).deleteDiary(id).awaitResponse()
    suspend fun getDiaryDetail(id: Long?) = retrofit.create(DiaryApi::class.java).getDiaryDetail(id).awaitResponse()
    suspend fun payDiary(id: Long?, request: RequestBody) = retrofit.create(DiaryApi::class.java).payDiary(id, request).awaitResponse()
    suspend fun deletePay(diaryId: Long?, payType: String, payId: Long) = retrofit.create(DiaryApi::class.java).deletePay(diaryId, payType, payId).awaitResponse()
    suspend fun getDiaryPayList(payType: String, page: Int, size: Int) = retrofit.create(DiaryApi::class.java).getDiaryPayList(payType, page, size).awaitResponse()
}