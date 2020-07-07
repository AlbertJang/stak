package `fun`.stak.paging

import `fun`.stak.data.retrofit.DiaryRepository
import `fun`.stak.model.diary.Pay
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import kotlinx.coroutines.CoroutineScope

class PayDataSourceFactory(
    private val repository: DiaryRepository,
    private val scope: CoroutineScope
) : DataSource.Factory<Int, Pay>() {
    val source = MutableLiveData<PayDataSource>()

    override fun create(): DataSource<Int, Pay> {
        val source = PayDataSource(repository, scope)
        this.source.postValue(source)
        return source
    }

    // --- PUBLIC API
    fun getSource() = source.value
}