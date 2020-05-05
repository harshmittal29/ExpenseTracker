package com.moneylion.expensetracker.common

import com.moneylion.expensetracker.core.expense.data.entity.Expense

fun mockExpenseEmptyObject() = Expense()

fun expenseMockList() = arrayListOf(mockExpenseObject())

fun mockExpenseObject(): Expense{
    val expense = Expense()
    expense.category = "Food & Beverages"
    expense.currency = "MYR"
    expense.description = "Lunch at Nandos"
    expense.amount = "40"
    return expense
}