package `fun`.stak.paging

import `fun`.stak.data.retrofit.DiaryRepository
import `fun`.stak.model.diary.Diary
import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.*

class DiaryDataSource(
    private val repository: DiaryRepository,
    private val scope: CoroutineScope
) : PageKeyedDataSource<Int, Diary>() {
    // FOR DATA ---
    private var supervisorJob = SupervisorJob()

    //    private val networkState = MutableLiveData<NetworkState>()
    private var retryQuery: (() -> Any)? =
        null // Keep reference of the last query (to be able to retry it if necessary)

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Diary>
    ) {
        retryQuery = { loadInitial(params, callback) }

        executeQuery(1) {
            callback.onResult(it, null, 2)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Diary>) {
        val page = params.key
        retryQuery = { loadAfter(params, callback) }

        executeQuery(page) {
            callback.onResult(it, page + 1)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Diary>) {}

    // UTILS ---
    private fun executeQuery(page: Int, callback: (List<Diary>) -> Unit) {
//        networkState.postValue(NetworkState.RUNNING)
        scope.launch(getJobErrorHandler() + supervisorJob) {
            delay(200) // To handle user typing case
            val response = repository.getDiaryList("", "", page, 20)

            if (response.isSuccessful) {
                retryQuery = null
//                networkState.postValue(NetworkState.SUCCESS)

                if (response.body()?.data?.diarySet != null)
                    callback(response.body()!!.data.diarySet)
            } else {
//                networkState.postValue(NetworkState.FAILED)
            }
        }
    }

    private fun getJobErrorHandler() = CoroutineExceptionHandler { _, e ->
        /*Log.e(BroadcastDataSource::class.java.simpleName, "An error happened: $e")
        networkState.postValue(NetworkState.FAILED)*/
    }

    override fun invalidate() {
        super.invalidate()
        supervisorJob.cancelChildren()   // Cancel possible running job to only keep last result searched by user
    }

    // PUBLIC API ---
    /*fun getNetworkState(): LiveData<NetworkState> =
        networkState*/

    fun refresh() = this.invalidate()

    fun retryFailedQuery() {
        val prevQuery = retryQuery
        retryQuery = null
        prevQuery?.invoke()
    }
}