package com.moneylion.expensetracker

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.moneylion.expensetracker.common.*
import com.moneylion.expensetracker.core.expense.data.datasource.local.ExpenseDatabaseDataSource
import com.moneylion.expensetracker.core.expense.data.datasource.local.ExpenseDatabaseDataSourceImpl
import com.moneylion.expensetracker.core.expense.data.db.ExpenseDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class AddExpenseRemoteDataSourceImplTest {

    private lateinit var databaseDataSource: ExpenseDatabaseDataSource
    private lateinit var dao: ExpenseDao

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule =
        CoroutinesTestRule()

    @Before
    fun before() {
        dao = mock()
        databaseDataSource = ExpenseDatabaseDataSourceImpl(dao)
    }

    @Test
    fun testSaveExpense() {

        coroutinesTestRule.testDispatcher.runBlockingTest {

            whenever(
                dao.addItem(
                    any()
                )
            ).thenReturn(Mockito.anyLong())
            val id = databaseDataSource.saveExpense(mockExpenseObject())
            Mockito.verify(dao, Mockito.times(1)).addItem(any())
            Assert.assertNotNull(id)
        }
    }

    @Test
    fun testFetchAllExpenses() {

        coroutinesTestRule.testDispatcher.runBlockingTest {

            whenever(dao.getAllItems())
                .thenReturn(expenseMockList())
            val expenses = databaseDataSource.getAllExpenses()
            Mockito.verify(dao, Mockito.times(1)).getAllItems()
            Assert.assertNotNull(expenses)
            Assert.assertTrue(expenses.size > 0)
        }
    }
}