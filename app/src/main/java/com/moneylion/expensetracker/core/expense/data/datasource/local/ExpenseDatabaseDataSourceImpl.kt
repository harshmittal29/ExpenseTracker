package com.moneylion.expensetracker.core.expense.data.datasource.local

import com.moneylion.expensetracker.core.expense.data.db.ExpenseDao
import com.moneylion.expensetracker.core.expense.data.entity.Expense
import javax.inject.Inject

/**
 * Expense local data source implementation
 * It takes a dao as a constructor param injected via Dependency Injection
 */
class ExpenseDatabaseDataSourceImpl @Inject constructor(private var dao: ExpenseDao?) :
    ExpenseDatabaseDataSource {

    override suspend fun saveExpense(expense: Expense): Long? {
        return dao?.addItem(expense)
    }

    override suspend fun getAllExpenses(): ArrayList<Expense> {
        return dao?.getAllItems() as ArrayList<Expense>
    }
}