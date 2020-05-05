package com.moneylion.expensetracker

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.moneylion.expensetracker.common.*
import com.moneylion.expensetracker.core.expense.domain.ExpenseUseCase
import com.moneylion.expensetracker.core.expense.presentation.view_model.ExpenseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import java.lang.Exception

@ExperimentalCoroutinesApi
class ExpenseViewModelTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule =
        CoroutinesTestRule()

    lateinit var  useCase : ExpenseUseCase
    lateinit var viewModel: ExpenseViewModel

    @Before
    fun initTest() {
        useCase = mock()
        viewModel = ExpenseViewModel(
            useCase,
            coroutinesTestRule.testDispatcher,
            coroutinesTestRule.testDispatcher
        )
    }

    @Test
    fun testGetExpensesLoadingState() {

        coroutinesTestRule.testDispatcher.runBlockingTest {

            val liveData = viewModel.getExpensesLiveData
            val loadingState = GenericResult.Loading()

            viewModel.loadingState()

            liveData.observeForTesting {
                assert(loadingState == liveData.value)
            }
        }
    }

    @Test
    fun testGetExpensesSuccessState() {

        coroutinesTestRule.testDispatcher.runBlockingTest {

            val expenseList = expenseMockList()

            whenever(useCase.getTimeLine()).thenReturn(expenseList)

            val liveData = viewModel.getExpensesLiveData

            viewModel.getAllExpenses()

            val loadedState = GenericResult.Success(expenseList)

            Mockito.verify(useCase).getTimeLine()
            liveData.observeForTesting {
                assert(loadedState == liveData.value)
            }
        }
    }

    @Test
    fun testGetExpensesErrorState() {

        coroutinesTestRule.testDispatcher.runBlockingTest {

            val expenseList = expenseMockList()

            whenever(useCase.getTimeLine()).then{
                throw Exception()
            }

            val liveData = viewModel.getExpensesLiveData

            viewModel.getAllExpenses()

            val errorState = GenericResult.Error.GenericError()

            Mockito.verify(useCase).getTimeLine()
            liveData.observeForTesting {
                assert(errorState == liveData.value)
            }
        }
    }

    @Test
    fun testSaveSuccessState() {

        coroutinesTestRule.testDispatcher.runBlockingTest {

            whenever(useCase.addExpense(any())).thenReturn(Mockito.anyLong())

            val liveData = viewModel.saveExpenseLiveData

            viewModel.saveExpense(mockExpenseObject())

            val loadedState = GenericResult.Success(true)

            Mockito.verify(useCase).addExpense(any())
            liveData.observeForTesting {
                assert(loadedState == liveData.value)
            }
        }
    }
}