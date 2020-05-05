package com.moneylion.expensetracker.core.expense.domain

import com.moneylion.expensetracker.core.expense.data.entity.Expense
import com.moneylion.expensetracker.core.expense.data.repository.ExpenseRepository
import javax.inject.Inject

/**
 * Expense use case implementation
 * It takes a Repository as a constructor param injected via Dependency Injection
 * Define business logic use cases in this layer
 */
class ExpenseUseCaseImpl@Inject constructor(private val repository: ExpenseRepository) : ExpenseUseCase{

    override suspend fun addExpense(expense: Expense): Long? {
        return repository.addExpenseToLocal(expense)
    }

    override suspend fun getTimeLine(): ArrayList<Expense> {
        return repository.getAllExpensesFromDatabase()
    }

    override suspend fun getExpenseByCategory(): ArrayList<Expense> {
        TODO("Not yet implemented")
    }
}