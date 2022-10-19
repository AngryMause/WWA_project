package com.angrymause.wwa_project.data.remotedata

import android.util.Log
import com.google.firebase.database.*
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.tasks.await

private const val REMOTE_BOOLEAN_CONFIG = "Remote Config"
private const val REMOTE_URL_CONFIG = "webview_url"
private const val DEBOUNCE = 500L

class RealTimeDataBaseRepository {
    private val database =
        FirebaseDatabase.getInstance()
    private lateinit var databaseRef: DatabaseReference

    @OptIn(FlowPreview::class)
    suspend fun getRemoteConfig(): Flow<CheckRemote> = callbackFlow<CheckRemote> {
        databaseRef = database.getReference(REMOTE_BOOLEAN_CONFIG)
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val data = snapshot.getValue(Boolean::class.java)
                Log.d("DataBaseRepository", "$data")
                trySend(CheckRemote.RemoteConfig(data!!))
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("DataBaseRepository", "${error.code}")
            }
        })
        awaitClose { channel.close() }

    }.apply {
        debounce(DEBOUNCE)
            .distinctUntilChanged()
    }


    suspend fun getRemoteUrl(): String {
        databaseRef = database.getReference(REMOTE_URL_CONFIG)
        val url = databaseRef.get().await().getValue(String::class.java)
        return url.toString()
    }
}

sealed class CheckRemote {
    data class RemoteConfig(val isTru: Boolean) : CheckRemote()

}