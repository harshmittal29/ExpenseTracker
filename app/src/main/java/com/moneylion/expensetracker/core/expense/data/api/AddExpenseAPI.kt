package com.moneylion.expensetracker.core.expense.data.api

import com.moneylion.expensetracker.core.expense.data.entity.Expense
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Future implementation of API.
 * We add more API's here related to expenses
 * //Todo : Implementation of Add Expense API
 */
interface AddExpenseAPI{

    @POST("./")
    suspend fun sendExpenseToServer(@Body params: RequestBody?): Response<Expense>?
}