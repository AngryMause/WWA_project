package com.angrymause.wwa_project.ui.fragment.splashfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angrymause.wwa_project.data.remotedata.RealTimeDataBaseRepository
import kotlinx.coroutines.launch


class SplashViewModel() : ViewModel() {
    private val fire: RealTimeDataBaseRepository by lazy { RealTimeDataBaseRepository() }
    private val _resp = MutableLiveData<Boolean>()
    val resp: LiveData<Boolean> get() = _resp


    init {
        viewModelScope.launch {
            getRemoteData()
        }
    }


    private suspend fun getRemoteData() {
        _resp.value = fire.getRemoteData().isTrue
    }
}