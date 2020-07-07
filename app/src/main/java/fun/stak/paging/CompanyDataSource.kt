package `fun`.stak.paging

import `fun`.stak.data.retrofit.CompanyRepository
import `fun`.stak.model.company.CompanySimple
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.*

class CompanyDataSource(
    private val searchTxt: String,
    private val repository: CompanyRepository,
    private val scope: CoroutineScope
) : PageKeyedDataSource<Int, CompanySimple>() {
    // FOR DATA ---
    private var supervisorJob = SupervisorJob()

    //    private val networkState = MutableLiveData<NetworkState>()
    private var retryQuery: (() -> Any)? =
        null // Keep reference of the last query (to be able to retry it if necessary)

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, CompanySimple>
    ) {
        retryQuery = { loadInitial(params, callback) }

        executeQuery(1) {
            callback.onResult(it, null, 2)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, CompanySimple>) {
        val page = params.key
        retryQuery = { loadAfter(params, callback) }

        executeQuery(page) {
            callback.onResult(it, page + 1)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, CompanySimple>) {}

    // UTILS ---
    private fun executeQuery(page: Int, callback: (List<CompanySimple>) -> Unit) {
//        networkState.postValue(NetworkState.RUNNING)
        scope.launch(getJobErrorHandler() + supervisorJob) {
            delay(200) // To handle user typing case
            val response = repository.getCompanyList(searchTxt, page, 20)

            if (response.isSuccessful) {
                retryQuery = null
//                networkState.postValue(NetworkState.SUCCESS)

                if (response.body()?.data?.companySet != null)
                    callback(response.body()!!.data.companySet)
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