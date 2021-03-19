package id.bagaswirapradana.test.stockbit.repository

import android.util.Log
import id.bagaswirapradana.test.stockbit.data.api.CoinApi
import id.bagaswirapradana.test.stockbit.data.model.CoinResponse

/**
 * Created by bagaswirapradana on 3/17/21.
 */
class WatchListRepository(private val coinApi: CoinApi) {

    private val tag = WatchListRepository::class.java.name
    private val limit: Int = 50

    suspend fun getCoinsFromApi(page: Int): CoinResponse? {
        try {
            val response = coinApi.getAllAsync("IDR", page, limit)
            response.let {
                return it
            }
        } catch (error: Exception) {
            Log.e(tag, "Error: ${error.message}")
            return null
        }
    }
}