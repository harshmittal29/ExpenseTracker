package com.moneylion.expensetracker.core.expense.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.moneylion.expensetracker.core.expense.data.entity.Expense

/**
 * Dao class to provide data base functionalities
 */
@Dao
interface ExpenseDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addItem(item: Expense): Long

    @Query("delete from Expense where id = :id")
    fun deleteItem(id: Long)

    @Query("select * from Expense")
    fun getAllItems(): List<Expense>

    @Query("select * from Expense where category=:category")
    fun getAllItemsByCategory(category: String): List<Expense>

    @Query("select * from Expense where id=:id Limit 1")
    fun getItemById(id: Long): Expense

    @Query("delete from Expense")
    fun clear()
}