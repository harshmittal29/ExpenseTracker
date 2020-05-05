package com.moneylion.expensetracker.core.expense.data.datasource.remote

import com.moneylion.expensetracker.core.expense.data.entity.Expense
import org.json.JSONObject

/**
 * Define a contract here to be used as remote data source
 */
interface ExpenseRemoteDataSource{

    suspend fun sendExpenseToRemote(json: JSONObject) : Expense?
}