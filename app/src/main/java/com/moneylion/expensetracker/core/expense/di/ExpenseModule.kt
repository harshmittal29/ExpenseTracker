package com.moneylion.expensetracker.core.expense.di

import com.moneylion.expensetracker.core.expense.data.api.AddExpenseAPI
import com.moneylion.expensetracker.core.expense.data.datasource.local.ExpenseDatabaseDataSource
import com.moneylion.expensetracker.core.expense.data.datasource.local.ExpenseDatabaseDataSourceImpl
import com.moneylion.expensetracker.core.expense.data.datasource.remote.ExpenseRemoteDatSourceImpl
import com.moneylion.expensetracker.core.expense.data.datasource.remote.ExpenseRemoteDataSource
import com.moneylion.expensetracker.core.expense.data.db.ExpenseDao
import com.moneylion.expensetracker.core.expense.data.repository.ExpenseRepository
import com.moneylion.expensetracker.core.expense.data.repository.ExpenseRepositoryImpl
import com.moneylion.expensetracker.core.expense.domain.ExpenseUseCase
import com.moneylion.expensetracker.core.expense.domain.ExpenseUseCaseImpl
import com.moneylion.expensetracker.core.expense.presentation.view_model.ExpenseViewModelFactory
import com.moneylion.expensetracker.common.di.ActivityScope
import com.moneylion.expensetracker.database.AppDatabase
import com.moneylion.expensetracker.network.APIUtil
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers

@Module
class ExpenseModule {

    @Provides
    @ActivityScope
    fun providesAddExpenseAPI(): AddExpenseAPI {
        return APIUtil.getRetrofit().create(AddExpenseAPI::class.java)
    }

    @Provides
    @ActivityScope
    fun providesExpenseDao(): ExpenseDao? {
        return AppDatabase.getInstance()?.expenseDao()
    }

    @Provides
    @ActivityScope
    fun providesAddExpenseLocalDataImpl(dao: ExpenseDao?): ExpenseDatabaseDataSource {
        return ExpenseDatabaseDataSourceImpl(dao)
    }

    @Provides
    @ActivityScope
    fun providesAddExpenseRemoteDataImpl(masterClassAPI: AddExpenseAPI): ExpenseRemoteDataSource {
        return ExpenseRemoteDatSourceImpl(masterClassAPI)
    }

    @Provides
    @ActivityScope
    fun providesAddExpenseDownloader(localDataSource: ExpenseDatabaseDataSource, remoteDataSource: ExpenseRemoteDataSource): ExpenseRepository {
        return ExpenseRepositoryImpl(localDataSource, remoteDataSource)
    }

    @Provides
    @ActivityScope
    fun providesAddExpenseUseCase(repository: ExpenseRepository): ExpenseUseCase {
        return ExpenseUseCaseImpl(repository)
    }

    @Provides
    @ActivityScope
    fun providesAddExpenseViewModelFactory(useCase: ExpenseUseCase): ExpenseViewModelFactory {
        return ExpenseViewModelFactory(useCase, Dispatchers.IO, Dispatchers.Main)
    }
}