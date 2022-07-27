package dev.oaras.countdownms

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class CountdownApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CountdownApp)
            modules(module)
        }
    }
    private val module = module {
        viewModel { CountdownViewModel(get()) }
    }
}