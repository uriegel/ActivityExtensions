package de.uriegel.extensionslibrary

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ActivityRequest(activity: ComponentActivity) {

    suspend fun launch(intent: Intent): ActivityResult {
        return suspendCoroutine { continuation ->
            this@ActivityRequest.continuation = continuation
            starter.launch(intent)
        }
    }

    private val starter: ActivityResultLauncher<Intent>
    private lateinit var continuation: Continuation<ActivityResult>

    init {
        starter = activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            continuation.resume(it)
        }
    }
}