package com.moneylion.expensetracker.common.di

import android.app.Application
import com.moneylion.expensetracker.common.ExpenseTrackerApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class AppModule {

    @Singleton
    @Provides
    open fun provideApp(application: Application) : ExpenseTrackerApplication {
        return application as ExpenseTrackerApplication
    }
}
