package id.bagaswirapradana.test.stockbit.di

import android.app.Application
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import id.bagaswirapradana.test.stockbit.data.api.CoinApi
import id.bagaswirapradana.test.stockbit.repository.WatchListRepository
import id.bagaswirapradana.test.stockbit.viewmodel.WatchListViewModel
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by bagaswirapradana on 3/15/21.
 */

val viewModelModule = module {
    single { WatchListViewModel(get()) }
}

val apiModule = module {
    fun provideCoinApi(retrofit: Retrofit): CoinApi {
        return retrofit.create(CoinApi::class.java)
    }

    single { provideCoinApi(get()) }
}

val netModule = module {
    fun provideCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    fun provideHttpClient(cache: Cache): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .cache(cache)

        return okHttpClientBuilder.build()
    }

    fun provideGson(): Gson {
        return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()
    }


    fun provideRetrofit(factory: Gson, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://min-api.cryptocompare.com/data/")
            .addConverterFactory(GsonConverterFactory.create(factory))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(client)
            .build()
    }

    single { provideCache(androidApplication()) }
    single { provideHttpClient(get()) }
    single { provideGson() }
    single { provideRetrofit(get(), get()) }
}

val repositoryModule = module {
    fun provideCoinRepository(api: CoinApi): WatchListRepository {
        return WatchListRepository(api)
    }

    single { provideCoinRepository(get()) }
}
