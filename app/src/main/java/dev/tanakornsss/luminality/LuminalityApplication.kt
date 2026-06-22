package dev.tanakornsss.luminality

import android.app.Application
import dev.tanakornsss.luminality.module.dbModule
import dev.tanakornsss.luminality.module.repositoryModule
import dev.tanakornsss.luminality.module.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class LuminalityApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@LuminalityApplication)
            androidLogger()
            modules(
                repositoryModule,
                viewModelModule,
                dbModule
            )
        }
    }
}