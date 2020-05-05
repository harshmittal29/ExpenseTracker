package com.moneylion.expensetracker.core.expense.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.moneylion.expensetracker.core.expense.domain.ExpenseUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.MainCoroutineDispatcher
import javax.inject.Inject

class ExpenseViewModelFactory @Inject constructor(private val useCase: ExpenseUseCase,
                                                  private val ioDispatcher: CoroutineDispatcher,
                                                  private val mainDispatcher: MainCoroutineDispatcher
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ExpenseViewModel(
            useCase = useCase,
            ioDispatcher = ioDispatcher,
            mainDispatcher = mainDispatcher) as T
    }

}