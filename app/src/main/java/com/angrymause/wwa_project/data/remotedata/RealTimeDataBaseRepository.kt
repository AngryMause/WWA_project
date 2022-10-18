package com.angrymause.wwa_project.data.remotedata

import com.angrymause.wwa_project.model.remotemodel.RemoteData
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

private const val REMOTE_CONFIG = "Remote Config"

class RealTimeDataBaseRepository {
    private val database =
        FirebaseDatabase.getInstance("https://wwa-project-12bfb-default-rtdb.europe-west1.firebasedatabase.app/")
    private val databaseRef = database.getReference(REMOTE_CONFIG)


    suspend fun getRemoteData(): RemoteData {
        val data = databaseRef.get().await().getValue(Boolean::class.java)
        return RemoteData(data!!)
    }
}