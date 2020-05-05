package com.moneylion.expensetracker.core.expense.data.datasource.local

import com.moneylion.expensetracker.core.expense.data.entity.Expense

/**
 * Define a contract here to be used as local data source
 */
interface ExpenseDatabaseDataSource{

    suspend fun saveExpense(expense: Expense) : Long?

    suspend fun getAllExpenses() : ArrayList<Expense>
}