package id.bagaswirapradana.test.stockbit.data.api

import id.bagaswirapradana.test.stockbit.data.model.CoinResponse
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 * Created by bagaswirapradana on 3/16/21.
 */
interface CoinApi {

    @Headers("authorization: Apikey {9fbd496305d20fb6ae21a81409adab3424dfb07c0234dcdab90a6ad0381f65bb}")
    @GET("top/totaltoptiervolfull")
    suspend fun getAllAsync(
        @Query("tsym") currency: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int): CoinResponse

}