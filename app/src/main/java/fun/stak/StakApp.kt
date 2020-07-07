package `fun`.stak

import `fun`.stak.di.appModule
import `fun`.stak.di.networkModule
import `fun`.stak.di.viewModelModule
import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class StakApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@StakApp)
            modules(listOf(appModule, networkModule, viewModelModule))
        }
    }
}