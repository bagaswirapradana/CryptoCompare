package id.bagaswirapradana.test.stockbit.ui.home

import com.airbnb.epoxy.EpoxyAsyncUtil
import com.airbnb.epoxy.EpoxyController
import id.bagaswirapradana.test.stockbit.data.model.CoinData
import id.bagaswirapradana.test.stockbit.ui.ProgressView_
import id.bagaswirapradana.test.stockbit.ui.home.view.coinView
import id.bagaswirapradana.test.stockbit.utils.Helpers

/**
 * Created by bagaswirapradana on 3/16/21.
 */
class WatchlistController : EpoxyController() {

    private val coins: MutableList<CoinData> = mutableListOf()
    private var isLastPage: Boolean = false

    fun setCoins(data: MutableList<CoinData>) {
        this.coins.clear()
        this.coins.addAll(data)
        requestModelBuild()
    }

    fun addCoins(data: MutableList<CoinData>) {
        this.coins.addAll(data)
        requestModelBuild()
    }

    override fun buildModels() {
        coins.forEach {
            coinView {
                id(it.coinInfo?.id)
                name(it.coinInfo?.name)
                fullname(it.coinInfo?.fullName)
                url(it.coinInfo?.imageUrl)
                price(
                    if (it.raw != null) {
                        "IDR " + Helpers.prettyCount(it.raw.currency.price)
                    } else {
                        "IDR 0"
                    }
                )
                change24Hours(
                    if (it.raw != null) {
                        Helpers.change24HoursPercentage(it.raw.currency.changePct24Hour)
                    } else {
                        "0 %"
                    }
                )
            }
        }
        ProgressView_().id("loading").addIf(!isLastPage, this)
    }

    fun setIsLoadMore(b: Boolean) {
        this.isLastPage = b
        requestModelBuild()
    }

}