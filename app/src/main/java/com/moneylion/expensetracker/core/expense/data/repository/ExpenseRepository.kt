package com.moneylion.expensetracker.core.expense.data.repository

import com.moneylion.expensetracker.core.expense.data.entity.Expense

/**
 * Define a contract here to be used as local or remote data source
 * Its purpose is to navigate the request either to remote or local data source
 */
interface ExpenseRepository {

    suspend fun addExpenseToLocal(expense: Expense) : Long?

    suspend fun getAllExpensesFromDatabase() : ArrayList<Expense>

    suspend fun addExpenseToRemote(expense: Expense) : Expense?

    suspend fun getCurrencyList() : ArrayList<String>
}