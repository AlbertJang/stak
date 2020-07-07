package `fun`.stak.network

import com.securepreferences.SecurePreferences
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AddCookiesInterceptor(private val securePreferences: SecurePreferences) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        //Build new request
        val builder = request.newBuilder()
        builder.addHeader("Accept", "application/json")

        val token = securePreferences.getEncryptedString("token", "")

        if (token != null && token.isNotEmpty()) {
            setAuthHeader(builder, token)
        }

        request = builder.build()

        return chain.proceed(request)
    }

    private fun setAuthHeader(builder: Request.Builder?, token: String) {
        builder?.header("Authorization", "STAK $token")
    }
}