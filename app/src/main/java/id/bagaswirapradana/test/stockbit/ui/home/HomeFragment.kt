package id.bagaswirapradana.test.stockbit.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.airbnb.epoxy.EpoxyRecyclerView
import de.greenrobot.event.EventBus
import id.bagaswirapradana.test.stockbit.R
import id.bagaswirapradana.test.stockbit.data.model.CoinData
import id.bagaswirapradana.test.stockbit.databinding.FragmentHomeBinding
import id.bagaswirapradana.test.stockbit.event.ReselectedEvent
import id.bagaswirapradana.test.stockbit.utils.EndlessRecyclerViewScrollListener
import id.bagaswirapradana.test.stockbit.utils.ResultState
import id.bagaswirapradana.test.stockbit.viewmodel.WatchListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {

    private val watchListViewModel by viewModel<WatchListViewModel>()
    private val bus: EventBus = EventBus.getDefault()
    private var isLastPage: Boolean = false
    private lateinit var binding: FragmentHomeBinding
    private var watchlistController: WatchlistController? = null

    override fun onStart() {
        super.onStart()
        bus.register(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val linearlayoutManager = LinearLayoutManager(activity)
        val dividerItemDecoration = DividerItemDecoration(activity, linearlayoutManager.orientation)
        if (null == watchlistController) {
            watchlistController = WatchlistController()
        }

        val scrollListener = object : EndlessRecyclerViewScrollListener(linearlayoutManager) {
            override fun onLoadMore(page: Int) {
                if (!isLastPage) {
                    watchListViewModel.loadDataNextPage()
                }
            }
        }

        binding.recyclerView.run {
            setHasFixedSize(true)
            layoutManager = linearlayoutManager
            addItemDecoration(dividerItemDecoration)
            itemAnimator = DefaultItemAnimator()
            setController(watchlistController!!)
            addOnScrollListener(scrollListener)
            smoothScrollToPosition(0)
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            watchListViewModel.setCurrentPage(0)
            scrollListener.resetState()
            watchListViewModel.loadData()
            binding.progressBar.visibility = View.GONE
        }

        scrollListener.resetState()
        watchListViewModel.setCurrentPage(0)
        watchListViewModel.loadData()
        initObservers()
    }

    private fun initObservers() {
        watchListViewModel.getCoins().observe(viewLifecycleOwner, { result ->
            when (result) {
                is ResultState.Success -> {
                    renderList(result.data)
                    binding.progressBar.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                }
                is ResultState.InProgress -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                }
                is ResultState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(activity, result.exception.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun renderList(data: ArrayList<CoinData>) {
        if (data.isNotEmpty()) {
            //when screen starts
            if (watchListViewModel.getCurrentPage() == 0) {
                watchlistController!!.setCoins(data)
            } else {
                //when load more
                isLastPage = data.isEmpty() || data.size < 50
                watchlistController!!.setIsLoadMore(isLastPage)
                watchlistController!!.addCoins(data)
            }
        }
    }

    override fun onDestroyView() {
        binding.recyclerView.adapter = null
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
        bus.unregister(this)
    }

    fun onEvent(event: ReselectedEvent) {
        binding.recyclerView.smoothScrollToPosition(event.position)
    }
}