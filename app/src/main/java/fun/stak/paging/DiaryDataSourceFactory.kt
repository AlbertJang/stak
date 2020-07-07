package `fun`.stak.paging

import `fun`.stak.data.retrofit.DiaryRepository
import `fun`.stak.model.diary.Diary
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import kotlinx.coroutines.CoroutineScope

class DiaryDataSourceFactory(
    private val repository: DiaryRepository,
    private val scope: CoroutineScope
) : DataSource.Factory<Int, Diary>() {
    val source = MutableLiveData<DiaryDataSource>()

    override fun create(): DataSource<Int, Diary> {
        val source = DiaryDataSource(repository, scope)
        this.source.postValue(source)
        return source
    }

    // --- PUBLIC API
    fun getSource() = source.value
}