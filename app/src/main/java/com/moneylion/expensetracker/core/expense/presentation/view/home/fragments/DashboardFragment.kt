package com.moneylion.expensetracker.core.expense.presentation.view.home.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.moneylion.expensetracker.R
import com.moneylion.expensetracker.common.ExpenseTrackerApplication
import com.moneylion.expensetracker.common.GenericResult
import com.moneylion.expensetracker.core.expense.data.entity.Expense
import com.moneylion.expensetracker.core.expense.di.DaggerExpenseComponent
import com.moneylion.expensetracker.core.expense.presentation.view_model.ExpenseViewModel
import com.moneylion.expensetracker.core.expense.presentation.view_model.ExpenseViewModelFactory
import kotlinx.android.synthetic.main.fragment_dashboard.*
import javax.inject.Inject

/**
 * Dashboard fragment to show total expenditure
 */
class DashboardFragment : Fragment(){

    @Inject
    lateinit var mwModelFactory: ExpenseViewModelFactory

    private lateinit var mViewModel: ExpenseViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerExpenseComponent
            .builder()
            .appComponent((activity?.applicationContext as ExpenseTrackerApplication).mAppComponent)
            .build()
            .inject(this)

        mViewModel = ViewModelProvider(this, mwModelFactory).get(ExpenseViewModel::class.java)
        mViewModel.getExpensesLiveData.observe(this, stateObserver)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    private val stateObserver = Observer<GenericResult<ArrayList<Expense>>> { state ->
        state?.let {
            when (it) {
                is GenericResult.Loading -> {
                    showLoader()
                }
                is GenericResult.Success -> {
                    refreshData(it.data)
                }
                else ->{
                    showError()
                }
            }
        }
    }

    private fun refreshData(expenses: ArrayList<Expense>){
        dashboard_progress.visibility = View.GONE
        add_expense_text_view.visibility = View.VISIBLE
        if(expenses.isNullOrEmpty()){
            add_expense_text_view.text = resources.getString(R.string.click_to_add_expense)
        }else{
            var amount = 0.0f
            for(expense in expenses){
                val a = expense.amount.toFloat()
                amount += a
            }
            add_expense_text_view.text = resources.getString(R.string.total_spend, "MYR", amount.toString())
        }
    }

    private fun showError(){
        dashboard_progress.visibility = View.GONE
        add_expense_text_view.visibility = View.VISIBLE
        add_expense_text_view.text = resources.getString(R.string.error_message)
    }

    private fun showLoader(){
        dashboard_progress.visibility = View.VISIBLE
        add_expense_text_view.visibility = View.GONE
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getDashboardFromDatabase()
    }

    fun getDashboardFromDatabase(){
        mViewModel.getAllExpenses()
    }
}