package com.moneylion.expensetracker.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.moneylion.expensetracker.common.ExpenseTrackerApplication
import com.moneylion.expensetracker.common.extension.TaskExecutor.executeTask
import com.moneylion.expensetracker.core.expense.data.db.ExpenseDao
import com.moneylion.expensetracker.core.expense.data.entity.Expense

/**
 * Room database class to store the data locally
 * Increase the version by 1 if there is change in database schema
 */
@Database(entities = [
    Expense::class
    ],
        version = AppDatabase.DB_VERSION, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun expenseDao(): ExpenseDao

    companion object {
        private const val DB_NAME = "expense_app.db"
        private var INSTANCE: AppDatabase? = null
        const val DB_VERSION: Int = 1

        @JvmStatic
        fun getInstance(): AppDatabase? {
            if (INSTANCE == null) {
                try {
                    INSTANCE = Room.databaseBuilder(ExpenseTrackerApplication.appContext!!,
                            AppDatabase::class.java, DB_NAME)
                            .fallbackToDestructiveMigration()
                            .build()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            return INSTANCE
        }


        /**
         * this method clear all the channelData in all the tables in the database
         */
        fun clearDatabase() {
            val task = Runnable {
                INSTANCE?.clearAllTables()
            }
            executeTask(task)
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }

}