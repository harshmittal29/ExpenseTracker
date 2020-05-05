package com.moneylion.expensetracker.core.expense.domain

import com.moneylion.expensetracker.core.expense.data.entity.Expense

/**
 * Define a contract here to be used as business logic
 */
interface ExpenseUseCase {

    suspend fun addExpense(expense: Expense) : Long?

    suspend fun getTimeLine() : ArrayList<Expense>

    suspend fun getExpenseByCategory() : ArrayList<Expense>
}