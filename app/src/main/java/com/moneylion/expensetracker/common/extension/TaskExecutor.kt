package com.moneylion.expensetracker.common.extension

import android.os.Build
import android.os.Handler
import android.os.HandlerThread

object TaskExecutor{
    /**
     * this method execute any task in a background thread then end that thread automatically and safely
     */
    fun executeTask(task: Runnable) {
        try {
            val handlerThread = HandlerThread("MyHandlerThread")
            handlerThread.start()
            Handler(handlerThread.looper).post(task)
            handlerThread.quitSafely()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}