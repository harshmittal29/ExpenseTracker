package com.moneylion.expensetracker

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.moneylion.expensetracker.common.*
import com.moneylion.expensetracker.core.expense.data.api.AddExpenseAPI
import com.moneylion.expensetracker.core.expense.data.datasource.remote.ExpenseRemoteDatSourceImpl
import com.moneylion.expensetracker.core.expense.data.entity.Expense
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.json.JSONObject
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import retrofit2.HttpException
import retrofit2.Response

@ExperimentalCoroutinesApi
class AddExpenseAPIDataSourceImplTest {

    private lateinit var api: AddExpenseAPI
    private lateinit var apiDataSource: ExpenseRemoteDatSourceImpl

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule =
        CoroutinesTestRule()

    @Before
    fun before() {
        api = mock()
        apiDataSource = ExpenseRemoteDatSourceImpl(api)
    }

    @Test
    fun testValidAddExpenseResponse() {

        coroutinesTestRule.testDispatcher.runBlockingTest {

            val response: Response<Expense>? = Response.success(200, mockExpenseEmptyObject())
            whenever(api.sendExpenseToServer(any())).thenReturn(response)
            val json = JSONObject()
            val expense = apiDataSource.sendExpenseToRemote(json)
            Mockito.verify(api, Mockito.times(1)).sendExpenseToServer(any())

            Assert.assertNotNull(expense)
        }
    }

    @Test(expected = HttpException::class)
    fun testErrorAddExpenseResponse() {

        coroutinesTestRule.testDispatcher.runBlockingTest {

            val response: Response<Expense>? = Response.error(400, ResponseBody
                .create(MediaType.parse("application/json; charset=utf-8"), mockExpenseEmptyObject().toString()))

            whenever(api.sendExpenseToServer(any())).thenReturn(response)
            val json = JSONObject()
            apiDataSource.sendExpenseToRemote(json)
            Mockito.verify(api, Mockito.times(1)).sendExpenseToServer(any())
        }
    }
}