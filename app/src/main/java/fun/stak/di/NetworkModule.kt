package `fun`.stak.di

import `fun`.stak.BuildConfig
import `fun`.stak.data.retrofit.CompanyRepository
import `fun`.stak.data.retrofit.DiaryRepository
import `fun`.stak.data.retrofit.UserRepository
import `fun`.stak.network.AddCookiesInterceptor
import `fun`.stak.utils.Constants
import android.util.Log
import com.securepreferences.SecurePreferences
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    factory { provideInterceptor() }
    single { provideRetrofit(get(), get()) }

    // repository
    single { UserRepository(get()) }
    single { DiaryRepository(get()) }
    single { CompanyRepository(get()) }
}

fun provideInterceptor(): Interceptor {
    val interceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
        override fun log(message: String) {
            Log.d("API", message)
        }
    })
    interceptor.level = HttpLoggingInterceptor.Level.BODY
    return interceptor
}

fun provideRetrofit(interceptor: Interceptor, secuerPreferences: SecurePreferences): Retrofit {
    return Retrofit.Builder().baseUrl(Constants.BASE_URL).client(provideOkHttpClient(interceptor, secuerPreferences))
        .addConverterFactory(GsonConverterFactory.create()).build()
}

fun provideOkHttpClient(interceptor: Interceptor, secuerPreferences: SecurePreferences): OkHttpClient {
    val builder = OkHttpClient()
        .newBuilder()
    builder.addInterceptor(AddCookiesInterceptor(secuerPreferences))
    if(BuildConfig.DEBUG){
        builder.addInterceptor(interceptor)
    }
    return  builder.build()
}