package `fun`.stak.paging

import `fun`.stak.data.retrofit.CompanyRepository
import `fun`.stak.model.company.CompanySimple
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import kotlinx.coroutines.CoroutineScope

class CompanyDataSourceFactory(
    private var searchTxt: String = "",
    private val repository: CompanyRepository,
    private val scope: CoroutineScope
) : DataSource.Factory<Int, CompanySimple>() {
    val source = MutableLiveData<CompanyDataSource>()

    override fun create(): DataSource<Int, CompanySimple> {
        val source = CompanyDataSource(searchTxt, repository, scope)
        this.source.postValue(source)
        return source
    }

    // --- PUBLIC API
    fun getSource() = source.value

    fun setSearchTxt(searchTxt: String?) {
        this.searchTxt = searchTxt ?: ""
        getSource()?.refresh()
    }
}