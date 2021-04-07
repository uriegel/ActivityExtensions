package de.uriegel.activityextensions.async

import java.util.*
import kotlin.concurrent.schedule
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

suspend fun delay(delayInMilliseconds: Long) {
    return suspendCoroutine { continuation ->
        Timer("Async Delay", false).schedule(delayInMilliseconds) {
            continuation.resume(Unit)
        }
    }
}