package id.bagaswirapradana.test.stockbit

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import id.bagaswirapradana.test.stockbit.di.apiModule
import id.bagaswirapradana.test.stockbit.di.netModule
import id.bagaswirapradana.test.stockbit.di.repositoryModule
import id.bagaswirapradana.test.stockbit.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 * Created by bagaswirapradana on 3/15/21.
 */
class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BaseApplication)
            androidLogger(Level.INFO)
            modules(listOf(netModule, apiModule, viewModelModule, repositoryModule))
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}