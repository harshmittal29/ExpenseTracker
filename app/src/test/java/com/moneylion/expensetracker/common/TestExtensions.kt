package com.moneylion.expensetracker.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LiveData<T>.observeForTesting(codeBlock: () -> Unit) {
    val observer = Observer<T> {}
    try {

        observeForever(observer)
        codeBlock()

    } finally {
        removeObserver(observer)
    }

}