package com.moneylion.expensetracker

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.moneylion.expensetracker.common.*
import com.moneylion.expensetracker.core.expense.data.repository.ExpenseRepository
import com.moneylion.expensetracker.core.expense.domain.ExpenseUseCase
import com.moneylion.expensetracker.core.expense.domain.ExpenseUseCaseImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class ExpenseUseCaseImplTest {

    private lateinit var repository : ExpenseRepository
    private lateinit var useCase : ExpenseUseCase

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule =
        CoroutinesTestRule()

    @Before
    fun before() {
        repository = mock()
        useCase = ExpenseUseCaseImpl(repository)
    }

    @Test
    fun testAddExpenseToRepository(){
        coroutinesTestRule.testDispatcher.runBlockingTest {

            whenever(
                repository.addExpenseToLocal(
                    any()
                )
            ).thenReturn(Mockito.anyLong())

            val id = useCase.addExpense(mockExpenseObject())

            Mockito.verify(repository, Mockito.times(1)).addExpenseToLocal(any())

            Assert.assertNotNull(id)
        }
    }

    @Test
    fun testGetTimelineFromRepository_Success(){
        coroutinesTestRule.testDispatcher.runBlockingTest {

            whenever(repository.getAllExpensesFromDatabase())
                .thenReturn(expenseMockList())

            val expenseList = useCase.getTimeLine()

            Mockito.verify(repository, Mockito.times(1)).getAllExpensesFromDatabase()

            Assert.assertNotNull(expenseList)
            Assert.assertTrue(expenseList.size > 0)
        }
    }

    @Test
    fun testGetTimelineFromRepository_Empty(){
        coroutinesTestRule.testDispatcher.runBlockingTest {

            whenever(repository.getAllExpensesFromDatabase())
                .thenReturn(null)

            val expenseList = useCase.getTimeLine()

            Mockito.verify(repository, Mockito.times(1)).getAllExpensesFromDatabase()

            Assert.assertNull(expenseList)
        }
    }
}