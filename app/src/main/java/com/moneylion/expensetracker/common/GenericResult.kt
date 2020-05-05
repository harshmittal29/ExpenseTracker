package com.moneylion.expensetracker.common

/**
 * Sealed class to pass Generic result to the views
 */
sealed class GenericResult <out T: Any?>{


    data class Loading(val data: Int = 0) : GenericResult<Nothing>()

    data class Success<out T: Any?>(val data: T) : GenericResult<T>()

    data class Empty<out T: Any?>(val data: T ) : GenericResult<T>()

    sealed class Error(val exception: Any? = null) : GenericResult<Nothing>() {

        data class NetworkError(val exception: Any? = null) : GenericResult<Nothing>()

        data class GenericError(val exception: Any? = null) : GenericResult<Nothing>()

    }

}
