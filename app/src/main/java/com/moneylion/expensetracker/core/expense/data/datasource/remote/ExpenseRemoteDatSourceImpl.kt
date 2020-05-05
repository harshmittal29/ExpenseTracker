package com.moneylion.expensetracker.core.expense.data.datasource.remote

import com.moneylion.expensetracker.core.expense.data.api.AddExpenseAPI
import com.moneylion.expensetracker.core.expense.data.entity.Expense
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

/**
 * Expense remote data source implementation
 * Class to send Expense to server
 * This is dummy API class to showcase API structuring
 * It takes a AddExpenseAPI as a constructor param injected via Dependency Injection
 */

class ExpenseRemoteDatSourceImpl @Inject constructor(var api: AddExpenseAPI) :
    ExpenseRemoteDataSource {
    override suspend fun sendExpenseToRemote(json: JSONObject): Expense? {

        val body = RequestBody.create(
            MediaType.parse("application/json; charset=utf-8"), json.toString())

        val response = api.sendExpenseToServer(body)
        if (response != null) {
            if (response.code() == 200) {
                return response.body()
            } else {
                throw HttpException(response)
            }
        }else{
            throw Exception()
        }
    }

}