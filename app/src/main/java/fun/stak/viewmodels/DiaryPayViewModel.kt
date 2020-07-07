package `fun`.stak.viewmodels

import `fun`.stak.data.retrofit.DiaryRepository
import `fun`.stak.paging.PayDataSourceFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList

class DiaryPayViewModel(diaryRepository: DiaryRepository) : ViewModel() {
    // FOR DATA ---
    private val payDataSource =
        PayDataSourceFactory(repository = diaryRepository, scope = viewModelScope)

    // OBSERVABLES ---
    val payList = LivePagedListBuilder(payDataSource, pagedListConfig()).build()
//    val networkState: LiveData<NetworkState>? = Transformations.switchMap(boardDataSource.source) { it.getNetworkState() }

    /**
     * Fetch a list of [User] by name
     * Called each time an user hits a key through [SearchView].
     */
    fun fetchSearchTxt(searchTxt: String?) {
//        diaryDataSource.setSearchTxt(searchTxt)
    }

    // PUBLIC API ---
    /**
     * Retry possible last paged request (ie: network issue)
     */
    fun refreshFailedRequest() =
        payDataSource.getSource()?.retryFailedQuery()

    /**
     * Refreshes all list after an issue
     */
    fun refreshAllList() =
        payDataSource.getSource()?.refresh()

    // UTILS ---
    private fun pagedListConfig() = PagedList.Config.Builder()
        .setInitialLoadSizeHint(20)
        .setEnablePlaceholders(true)
        .setPrefetchDistance(2)
        .setPageSize(20)
        .build()
}