package `fun`.stak.data.retrofit

import `fun`.stak.model.company.CompanyDetailResponse
import `fun`.stak.model.company.CompanyListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CompanyApi {
    @GET("/api/v1/company/")
    fun getCompanyList(
        @Query("search_txt") searchTxt: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Call<CompanyListResponse>

    @GET("/api/v1/company/{id}/")
    fun getCompanyDetail(@Path("id") id: Long?): Call<CompanyDetailResponse>

    @GET("/api/v1/company/{id}/memo/")
    fun getCompanyMemoList(@Path("id") id: Long): Call<Void>
}