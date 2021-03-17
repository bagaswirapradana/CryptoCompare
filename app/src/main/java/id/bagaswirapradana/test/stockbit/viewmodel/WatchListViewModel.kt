package id.bagaswirapradana.test.stockbit.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.bagaswirapradana.test.stockbit.data.model.CoinData
import id.bagaswirapradana.test.stockbit.repository.WatchListRepository
import id.bagaswirapradana.test.stockbit.utils.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


/**
 * Created by bagaswirapradana on 3/16/21.
 */
class WatchListViewModel(private val watchListRepository: WatchListRepository) : ViewModel() {

    private var currentPage = 0
    private val coins = MutableLiveData<ResultState<ArrayList<CoinData>>>()

    fun getCoins() = coins

    fun setCurrentPage(page: Int) {
        currentPage = page
    }

    fun loadData() {
        try {
            if (currentPage == 0) {
                coins.postValue(ResultState.InProgress)
            }
            viewModelScope.launch(Job() + Dispatchers.IO) {
                val response = watchListRepository.getCoinsFromApi(currentPage)
                response?.let {
                    val coinList = it.data
                    coinList.let { list ->
                        if (currentPage == 0) { //set coins for first page
                            coins.postValue(ResultState.Success(list))
                        } else { //add coins to current list
                            coins.postValue(ResultState.Success(list))
                        }
                    }
                } ?: run {
                    coins.postValue(ResultState.Success(arrayListOf()))
                }
            }
        } catch (error: Exception) {
            coins.postValue(ResultState.Error(error))
        }
    }

    fun loadDataNextPage() {
        currentPage += 1
        loadData()
    }

    fun getCurrentPage() = currentPage
}