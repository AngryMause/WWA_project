package com.angrymause.wwa_project.ui.fragment.weviewscreens

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angrymause.wwa_project.data.remotedata.RealTimeDataBaseRepository
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.launch

class WebViewViewModel : ViewModel() {
    private val fire: RealTimeDataBaseRepository by lazy { RealTimeDataBaseRepository() }
    private val _resp = MutableLiveData<String>()
    val resp: LiveData<String> get() = _resp

    init {
        viewModelScope.launch {
            Log.d("DataBaseRepository", "webview ${fire.getRemoteUrl()}")
            getUrl()
        }
    }

    private suspend fun getUrl() {
        _resp.value = fire.getRemoteUrl()
        awaitCancellation()
    }


}