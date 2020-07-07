package `fun`.stak.viewmodels

import `fun`.stak.data.local.Resource
import `fun`.stak.data.retrofit.UserRepository
import `fun`.stak.model.user.UserResponse
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import java.net.UnknownHostException

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {
    //Fetch total stats
    private val performFetchStats = MutableLiveData<Resource<UserResponse>>()
    val performFetchStatsStatus: LiveData<Resource<UserResponse>>
        get() = performFetchStats

    fun signUp(request: RequestBody) {
        viewModelScope.launch {
            try {
                performFetchStats.value = Resource.loading()
                val response = userRepository.signUp(request)
                performFetchStats.value = Resource.success(response.body())
            } catch (e: Exception) {
                println("fetch stats failed ${e.message}")
                if (e is UnknownHostException) {
                    performFetchStats.value = Resource.offlineError()
                } else {
                    performFetchStats.value = Resource.error(e)
                }
            }
        }
    }

    fun signIn(request: RequestBody) {
        viewModelScope.launch {
            try {
                performFetchStats.value = Resource.loading()
                val response = userRepository.signIn(request)
                performFetchStats.value = Resource.success(response.body())
            } catch (e: Exception) {
                println("fetch stats failed ${e.message}")
                if (e is UnknownHostException) {
                    performFetchStats.value = Resource.offlineError()
                } else {
                    performFetchStats.value = Resource.error(e)
                }
            }
        }
    }

    // token refresh
    private val tokenStats = MutableLiveData<Resource<UserResponse>>()
    val tokenStatus: LiveData<Resource<UserResponse>>
        get() = tokenStats

    fun tokenRefresh() {
        viewModelScope.launch {
            try {
                tokenStats.value = Resource.loading()
                val response = userRepository.tokenRefresh()
                tokenStats.value = Resource.success(response.body())
            } catch (e: Exception) {
                println("fetch stats failed ${e.message}")
                if (e is UnknownHostException) {
                    tokenStats.value = Resource.offlineError()
                } else {
                    tokenStats.value = Resource.error(e)
                }
            }
        }
    }
}