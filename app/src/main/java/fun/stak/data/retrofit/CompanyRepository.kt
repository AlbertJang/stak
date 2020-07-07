package `fun`.stak.data.retrofit

import retrofit2.Retrofit
import retrofit2.awaitResponse

class CompanyRepository(private val retrofit: Retrofit) {
    suspend fun getCompanyList(searchTxt: String, page: Int, size: Int) =
        retrofit.create(CompanyApi::class.java).getCompanyList(searchTxt, page, size).awaitResponse()
    suspend fun getCompanyDetail(id: Long?) = retrofit.create(CompanyApi::class.java).getCompanyDetail(id).awaitResponse()
}