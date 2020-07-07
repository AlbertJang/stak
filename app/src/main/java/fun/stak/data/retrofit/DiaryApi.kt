package `fun`.stak.data.retrofit

import `fun`.stak.model.diary.DiaryDetailResponse
import `fun`.stak.model.diary.DiaryListResponse
import `fun`.stak.model.diary.PayListResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface DiaryApi {
    @GET("/api/v1/diary/")
    fun getDiaryList(
        @Query("filter_key") filterKey: String,
        @Query("search_txt") searchTxt: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Call<DiaryListResponse>

    @POST("/api/v1/diary/")
    fun addDiary(@Body request: RequestBody): Call<Void>

    @DELETE("/api/v1/diary/{id}/")
    fun deleteDiary(@Path("id") id: Long?): Call<Void>

    @GET("/api/v1/diary/{id}/")
    fun getDiaryDetail(@Path("id") id: Long?): Call<DiaryDetailResponse>

    @POST("/api/v1/diary/{id}/pay/")
    fun payDiary(@Path("id") id: Long?, @Body request: RequestBody): Call<Void>

    @DELETE("/api/v1/diary/{diary_id}/pay/{pay_type}/{pay_id}/")
    fun deletePay(
        @Path("diary_id") diaryId: Long?,
        @Path("pay_type") payType: String,
        @Path("pay_id") payId: Long
    ): Call<Void>

    @GET("/api/v1/diary/123/pay/")
    fun getDiaryPayList(
        @Query("pay_type") payType: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Call<PayListResponse>
}