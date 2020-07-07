package `fun`.stak.di

import com.google.gson.Gson
import com.securepreferences.SecurePreferences
import org.koin.dsl.module

val appModule = module {

    single { SecurePreferences(get(), "", "my_prefs.xml") }

    single {
        Gson()
    }

}