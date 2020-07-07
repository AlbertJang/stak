package `fun`.stak.data.retrofit

import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.awaitResponse

class UserRepository(private val retrofit: Retrofit) {
    suspend fun signUp(request: RequestBody) = retrofit.create(UserApi::class.java).signUp(request).awaitResponse()
    suspend fun signIn(request: RequestBody) = retrofit.create(UserApi::class.java).signIn(request).awaitResponse()
    suspend fun tokenRefresh() = retrofit.create(UserApi::class.java).tokenRefresh().awaitResponse()
}