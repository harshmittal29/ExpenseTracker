package com.moneylion.expensetracker.core.expense.data.repository

import com.moneylion.expensetracker.core.expense.data.datasource.local.ExpenseDatabaseDataSource
import com.moneylion.expensetracker.core.expense.data.datasource.remote.ExpenseRemoteDataSource
import com.moneylion.expensetracker.core.expense.data.entity.Expense
import org.json.JSONObject
import javax.inject.Inject

/**
 * Expense repository implementation
 * It takes a local data source and remote data source as a constructor param injected via Dependency Injection
 */
class ExpenseRepositoryImpl @Inject constructor(private val cachedDataStore: ExpenseDatabaseDataSource,
                                                private val remoteDataStore: ExpenseRemoteDataSource
): ExpenseRepository{
    override suspend fun addExpenseToLocal(expense: Expense): Long? {
        return cachedDataStore.saveExpense(expense)
    }

    override suspend fun getAllExpensesFromDatabase(): ArrayList<Expense> {
        return cachedDataStore.getAllExpenses()
    }

    /**
     * This is dummy method to showcase API
     * Add expense to server
     * @param expense expense object
     * TODO: convert expense object to JSON
     */
    override suspend fun addExpenseToRemote(expense: Expense): Expense? {
        val json = JSONObject()
        return remoteDataStore.sendExpenseToRemote(json)
    }

    /**
     * Future implementation to get list of currencies from server
     */
    override suspend fun getCurrencyList(): ArrayList<String> {
        TODO("Not yet implemented")
    }

}