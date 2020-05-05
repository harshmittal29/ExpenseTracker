package com.moneylion.expensetracker.core.expense.di

import com.moneylion.expensetracker.core.expense.presentation.view.add_expense.AddExpenseActivity
import com.moneylion.expensetracker.common.di.ActivityScope
import com.moneylion.expensetracker.common.di.AppComponent
import com.moneylion.expensetracker.core.expense.presentation.view.home.fragments.DashboardFragment
import com.moneylion.expensetracker.core.expense.presentation.view.home.fragments.TimelineFragment
import dagger.Component

@ActivityScope
@Component(dependencies = [AppComponent::class], modules = [ExpenseModule::class])
interface ExpenseComponent {
    fun inject(addExpenseActivity: AddExpenseActivity)

    fun inject(timelineFragment: TimelineFragment)

    fun inject(dashboardFragment: DashboardFragment)
}