package `fun`.stak.viewmodels

import `fun`.stak.data.local.Resource
import `fun`.stak.data.retrofit.DiaryRepository
import `fun`.stak.model.diary.DiaryDetailResponse
import `fun`.stak.model.diary.DiaryListResponse
import `fun`.stak.paging.CompanyDataSourceFactory
import `fun`.stak.paging.DiaryDataSourceFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import java.net.UnknownHostException

class DiaryViewModel(private val diaryRepository: DiaryRepository) : ViewModel() {
    // FOR DATA ---
    private val diaryDataSource = DiaryDataSourceFactory(repository = diaryRepository, scope = viewModelScope)

    // OBSERVABLES ---
    val diaryList = LivePagedListBuilder(diaryDataSource, pagedListConfig()).build()
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
        diaryDataSource.getSource()?.retryFailedQuery()

    /**
     * Refreshes all list after an issue
     */
    fun refreshAllList() =
        diaryDataSource.getSource()?.refresh()

    // UTILS ---
    private fun pagedListConfig() = PagedList.Config.Builder()
        .setInitialLoadSizeHint(20)
        .setEnablePlaceholders(true)
        .setPrefetchDistance(2)
        .setPageSize(20)
        .build()

    // add diary
    private val addDiaryStats = MutableLiveData<Resource<Void>>()
    val addDiaryStatus: LiveData<Resource<Void>>
        get() = addDiaryStats

    fun addDiary(request: RequestBody) {
        viewModelScope.launch {
            try {
                addDiaryStats.value = Resource.loading()
                val response = diaryRepository.addDiary(request)
                addDiaryStats.value = Resource.success(response.body())
            } catch (e: Exception) {
                println("fetch stats failed ${e.message}")
                if (e is UnknownHostException) {
                    addDiaryStats.value = Resource.offlineError()
                } else {
                    addDiaryStats.value = Resource.error(e)
                }
            }
        }
    }

    // delete diary
    private val delDiaryStats = MutableLiveData<Resource<Void>>()
    val delDiaryStatus: LiveData<Resource<Void>>
        get() = delDiaryStats

    fun deleteDiary(id: Long?) {
        viewModelScope.launch {
            try {
                delDiaryStats.value = Resource.loading()
                val response = diaryRepository.deleteDiary(id)
                delDiaryStats.value = Resource.success(response.body())
            } catch (e: Exception) {
                println("fetch stats failed ${e.message}")
                if (e is UnknownHostException) {
                    delDiaryStats.value = Resource.offlineError()
                } else {
                    delDiaryStats.value = Resource.error(e)
                }
            }
        }
    }

    // detail diary
    private val detailDiaryStats = MutableLiveData<Resource<DiaryDetailResponse>>()
    val detailDiaryStatus: LiveData<Resource<DiaryDetailResponse>>
        get() = detailDiaryStats

    fun getDiaryDetail(id: Long?) {
        viewModelScope.launch {
            try {
                detailDiaryStats.value = Resource.loading()
                val response = diaryRepository.getDiaryDetail(id)
                detailDiaryStats.value = Resource.success(response.body())
            } catch (e: Exception) {
                println("fetch stats failed ${e.message}")
                if (e is UnknownHostException) {
                    detailDiaryStats.value = Resource.offlineError()
                } else {
                    detailDiaryStats.value = Resource.error(e)
                }
            }
        }
    }

    // pay diary
    private val payDiaryStats = MutableLiveData<Resource<Void>>()
    val payDiaryStatus: LiveData<Resource<Void>>
        get() = payDiaryStats

    fun payDiary(id: Long?, request: RequestBody) {
        viewModelScope.launch {
            try {
                payDiaryStats.value = Resource.loading()
                val response = diaryRepository.payDiary(id, request)
                payDiaryStats.value = Resource.success(response.body())
            } catch (e: Exception) {
                println("fetch stats failed ${e.message}")
                if (e is UnknownHostException) {
                    payDiaryStats.value = Resource.offlineError()
                } else {
                    payDiaryStats.value = Resource.error(e)
                }
            }
        }
    }

    // delete pay
    private val delPayDiaryStats = MutableLiveData<Resource<Void>>()
    val delPayDiaryStatus: LiveData<Resource<Void>>
        get() = delPayDiaryStats

    fun deletePay(diaryId: Long?, payType: String, payId: Long) {
        viewModelScope.launch {
            try {
                delPayDiaryStats.value = Resource.loading()
                val response = diaryRepository.deletePay(diaryId, payType, payId)
                delPayDiaryStats.value = Resource.success(response.body())
            } catch (e: Exception) {
                println("fetch stats failed ${e.message}")
                if (e is UnknownHostException) {
                    delPayDiaryStats.value = Resource.offlineError()
                } else {
                    delPayDiaryStats.value = Resource.error(e)
                }
            }
        }
    }
}