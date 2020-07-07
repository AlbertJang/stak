package `fun`.stak.viewmodels

import `fun`.stak.data.local.Resource
import `fun`.stak.data.retrofit.CompanyRepository
import `fun`.stak.model.company.CompanyDetailResponse
import `fun`.stak.model.company.CompanyListResponse
import `fun`.stak.paging.CompanyDataSourceFactory
import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class CompanyViewModel(private val companyRepository: CompanyRepository) : ViewModel() {
    // FOR DATA ---
    private val companyDataSource = CompanyDataSourceFactory(repository = companyRepository, scope = viewModelScope)

    // OBSERVABLES ---
    val companyList = LivePagedListBuilder(companyDataSource, pagedListConfig()).build()
//    val networkState: LiveData<NetworkState>? = Transformations.switchMap(boardDataSource.source) { it.getNetworkState() }

    /**
     * Fetch a list of [User] by name
     * Called each time an user hits a key through [SearchView].
     */
    fun fetchSearchTxt(searchTxt: String?) {
        companyDataSource.setSearchTxt(searchTxt)
    }

    // PUBLIC API ---
    /**
     * Retry possible last paged request (ie: network issue)
     */
    fun refreshFailedRequest() =
        companyDataSource.getSource()?.retryFailedQuery()

    /**
     * Refreshes all list after an issue
     */
    fun refreshAllList() =
        companyDataSource.getSource()?.refresh()

    // UTILS ---
    private fun pagedListConfig() = PagedList.Config.Builder()
        .setInitialLoadSizeHint(20)
        .setEnablePlaceholders(true)
        .setPrefetchDistance(2)
        .setPageSize(20)
        .build()

    //Fetch total stats
    private val companyDetailStats = MutableLiveData<Resource<CompanyDetailResponse>>()
    val companyDetailStatus: LiveData<Resource<CompanyDetailResponse>>
        get() = companyDetailStats

    fun getCompanyDetail(id: Long?) {
        viewModelScope.launch {
            try {
                companyDetailStats.value = Resource.loading()
                val response = companyRepository.getCompanyDetail(id)
                companyDetailStats.value = Resource.success(response.body())
            } catch (e: Exception) {
                println("fetch stats failed ${e.message}")
                if (e is UnknownHostException) {
                    companyDetailStats.value = Resource.offlineError()
                } else {
                    companyDetailStats.value = Resource.error(e)
                }
            }
        }
    }
}