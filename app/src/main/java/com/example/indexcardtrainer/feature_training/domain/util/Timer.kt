package com.example.indexcardtrainer.feature_training.domain.util

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class Timer {
    private var isRunning : Boolean = false
    private var runtime : Long = 0
    private val scope = CoroutineScope(Dispatchers.Default)
    val timerRuntimeFlow = MutableStateFlow(runtime)

    fun start() {
        isRunning = true
        run()
    }

    private fun run() {
        if (!isRunning) {
            return
        }
        scope.launch {
            delay(1000L)
            runtime += 1
            timerRuntimeFlow.emit(runtime)
            run()
        }
    }

    fun stop() {
        isRunning = false
    }

    fun reset() {
        isRunning = false
        runtime = 0
    }
}