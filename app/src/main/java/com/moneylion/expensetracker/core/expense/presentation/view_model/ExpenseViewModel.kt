package com.moneylion.expensetracker.core.expense.presentation.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moneylion.expensetracker.common.GenericResult
import com.moneylion.expensetracker.core.expense.data.entity.Expense
import com.moneylion.expensetracker.core.expense.domain.ExpenseUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

/**
 * View model class
 * Views would be listening to view models in this class.
 * It follows Imperative programming
 */
class ExpenseViewModel(
    private val useCase: ExpenseUseCase,
    private val ioDispatcher: CoroutineDispatcher,
    private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _saveExpense = MutableLiveData<GenericResult<Boolean>>()
    val saveExpenseLiveData: LiveData<GenericResult<Boolean>>
        get() = _saveExpense


    private val _getExpenses = MutableLiveData<GenericResult<ArrayList<Expense>>>()

    val getExpensesLiveData: LiveData<GenericResult<ArrayList<Expense>>>
        get() = _getExpenses

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->

        viewModelScope.launch(mainDispatcher) {

            when (throwable) {
                is IOException -> _saveExpense.value = GenericResult.Error.NetworkError()
                else -> _saveExpense.value = GenericResult.Error.GenericError()
            }
        }
    }

    fun saveExpense(expense: Expense) {

        viewModelScope.launch(ioDispatcher + exceptionHandler) {

            val id = useCase.addExpense(expense)
            withContext(mainDispatcher) {
                if (id != null) {
                    _saveExpense.value = GenericResult.Success(true)
                }
            }
        }
    }

    fun getAllExpenses() {
        loadingState()
        viewModelScope.launch(ioDispatcher + getExpenseExceptionHandler) {
            val expenses = useCase.getTimeLine()
            withContext(mainDispatcher) {
                _getExpenses.value = GenericResult.Success(expenses)
            }
        }
    }

    fun loadingState(){
        _getExpenses.value = GenericResult.Loading()
    }

    private val getExpenseExceptionHandler = CoroutineExceptionHandler { _, throwable ->

        viewModelScope.launch(mainDispatcher) {

            when (throwable) {
                is IOException -> _getExpenses.value = GenericResult.Error.NetworkError()
                else -> _getExpenses.value = GenericResult.Error.GenericError()
            }
        }
    }
}