package com.moneylion.expensetracker.common.di

import android.app.Application
import com.moneylion.expensetracker.common.ExpenseTrackerApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, AndroidSupportInjectionModule::class, AppModule::class])
interface AppComponent : AndroidInjector<ExpenseTrackerApplication>  {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    override fun inject(application: ExpenseTrackerApplication)

    fun application(): ExpenseTrackerApplication
}
