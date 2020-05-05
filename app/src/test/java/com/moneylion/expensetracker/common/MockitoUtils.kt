@file:Suppress("UNCHECKED_CAST")

package com.moneylion.expensetracker.common

import org.mockito.Mockito
import org.mockito.stubbing.OngoingStubbing

inline fun <reified T> mock() = Mockito.mock(T::class.java)
fun <T> whenever(methodCall: T) : OngoingStubbing<T> = Mockito.`when`(methodCall)

fun <T> any(): T {
    Mockito.any<T>()
    return uninitialized()
}
fun <T> uninitialized(): T = null as T