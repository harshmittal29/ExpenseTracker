package com.moneylion.expensetracker

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.moneylion.expensetracker.core.expense.data.db.ExpenseDao
import com.moneylion.expensetracker.database.AppDatabase
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExpenseDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var dao: ExpenseDao

    @Before
    fun before() {
        database = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            AppDatabase::class.java).build()

        dao = database.expenseDao()
    }

    @After
    fun after() {
        database.close()
    }

    @Test
    fun testInsertion() {
        dao.addItem(mockExpenseObject())
        val result = dao.getItemById(1)
        Assert.assertNotNull(result)
        Assert.assertTrue(result.id == 1)
    }

    @Test
    fun testGetAllExpenses() {
        dao.addItem(mockExpenseObject())
        val result = dao.getAllItems()
        Assert.assertNotNull(result)
        Assert.assertTrue(result.isNotEmpty())
    }

    @Test
    fun testGetExpensesByCategory_Success() {
        dao.addItem(mockExpenseObject())
        val result = dao.getAllItemsByCategory("Food & Beverages")
        Assert.assertTrue(result.isNotEmpty())

        val result1 = dao.getAllItemsByCategory("Telecommunication")
        Assert.assertTrue(result1.isEmpty())
    }

    @Test
    fun testGetExpensesByCategory_Empty() {
        dao.addItem(mockExpenseObject())
        val result = dao.getAllItemsByCategory("Telecommunication")
        Assert.assertTrue(result.isEmpty())
    }
}