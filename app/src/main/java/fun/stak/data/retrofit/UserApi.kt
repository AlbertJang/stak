package `fun`.stak.data.retrofit

import `fun`.stak.model.user.UserResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface UserApi {
    @POST("api/v1/user/sign-up/")
    fun signUp(@Body request: RequestBody): Call<UserResponse>

    @POST("api/v1/user/sign-in/")
    fun signIn(@Body request: RequestBody): Call<UserResponse>

    @POST("/api/v1/user/token-refresh/")
    fun tokenRefresh(): Call<UserResponse>
}