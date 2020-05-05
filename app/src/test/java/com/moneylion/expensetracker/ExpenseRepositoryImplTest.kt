package com.moneylion.expensetracker

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.moneylion.expensetracker.common.*
import com.moneylion.expensetracker.core.expense.data.datasource.local.ExpenseDatabaseDataSource
import com.moneylion.expensetracker.core.expense.data.datasource.remote.ExpenseRemoteDataSource
import com.moneylion.expensetracker.core.expense.data.repository.ExpenseRepository
import com.moneylion.expensetracker.core.expense.data.repository.ExpenseRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import java.lang.Exception

@ExperimentalCoroutinesApi
class ExpenseRepositoryImplTest {

    private lateinit var apiDataSource: ExpenseRemoteDataSource
    private lateinit var databaseDataSource: ExpenseDatabaseDataSource
    private lateinit var repository : ExpenseRepository

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule =
        CoroutinesTestRule()

    @Before
    fun before() {
        apiDataSource = mock()
        databaseDataSource = mock()
        repository = ExpenseRepositoryImpl(databaseDataSource, apiDataSource)
    }


    @Test
    fun testAddExpenseFromAPI_Success(){
        coroutinesTestRule.testDispatcher.runBlockingTest {

            whenever(
                apiDataSource.sendExpenseToRemote(
                    any()
                )
            )
                .thenReturn(mockExpenseEmptyObject())

            val expense = repository.addExpenseToRemote(any())

            Mockito.verify(apiDataSource, Mockito.times(1)).sendExpenseToRemote(any())

            Assert.assertNotNull(expense)
        }
    }

    @Test(expected = Exception::class)
    fun testSaveExpenseFromAPI_Error(){
        coroutinesTestRule.testDispatcher.runBlockingTest {

            whenever(
                apiDataSource.sendExpenseToRemote(
                    any()
                )
            ).then{
                throw Exception()
            }

            repository.addExpenseToRemote(any())
            Mockito.verify(apiDataSource, Mockito.times(1)).sendExpenseToRemote(any())
        }
    }

    @Test
    fun testSaveExpenseToDatabase_Success(){
        coroutinesTestRule.testDispatcher.runBlockingTest {

            whenever(
                databaseDataSource.saveExpense(
                    any()
                )
            ).thenReturn(Mockito.anyLong())

            val id = repository.addExpenseToLocal(mockExpenseObject())

            Mockito.verify(databaseDataSource, Mockito.times(1)).saveExpense(any())

            Assert.assertNotNull(id)
        }
    }

    @Test
    fun testExpensesFromDatabase_Success(){
        coroutinesTestRule.testDispatcher.runBlockingTest {

            whenever(databaseDataSource.getAllExpenses())
                .thenReturn(expenseMockList())

            val expenses = repository.getAllExpensesFromDatabase()

            Mockito.verify(databaseDataSource, Mockito.times(1)).getAllExpenses()

            Assert.assertNotNull(expenses)
        }
    }

    @Test
    fun testExpensesFromDatabase_Empty(){
        coroutinesTestRule.testDispatcher.runBlockingTest {

            whenever(databaseDataSource.getAllExpenses())
                .thenReturn(null)

            val expenses = repository.getAllExpensesFromDatabase()

            Mockito.verify(databaseDataSource, Mockito.times(1)).getAllExpenses()

            Assert.assertNull(expenses)
        }
    }
}