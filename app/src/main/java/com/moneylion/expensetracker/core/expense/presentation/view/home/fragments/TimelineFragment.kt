package com.moneylion.expensetracker.core.expense.presentation.view.home.fragments

import android.content.Context
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
import com.moneylion.expensetracker.core.expense.presentation.view.home.ExpenseAdapter
import com.moneylion.expensetracker.core.expense.presentation.view_model.ExpenseViewModel
import com.moneylion.expensetracker.core.expense.presentation.view_model.ExpenseViewModelFactory
import kotlinx.android.synthetic.main.fragment_timeline.*
import javax.inject.Inject

/**
 * Timeline fragment to show list of all expenditure
 */
class TimelineFragment : Fragment(){

    lateinit var mAdapter : ExpenseAdapter

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
        return inflater.inflate(R.layout.fragment_timeline, container, false)
    }

    fun getTimelineFromDatabase(){
        mViewModel.getAllExpenses()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mAdapter =
            ExpenseAdapter(
                activity as Context, arrayListOf()
            )
        timeline_recycler_view.adapter = mAdapter
        getTimelineFromDatabase()
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

    private fun showLoader(){
        timeline_progress.visibility = View.VISIBLE
        no_expense_text_view.visibility = View.GONE
        timeline_recycler_view.visibility = View.GONE
    }

    private fun refreshData(expenses: ArrayList<Expense>){
        timeline_progress.visibility = View.GONE
        if(expenses.isNullOrEmpty()){
            no_expense_text_view.visibility = View.VISIBLE
            no_expense_text_view.text = resources.getString(R.string.no_expense_added)
            timeline_recycler_view.visibility = View.GONE
        }else{
            no_expense_text_view.visibility = View.GONE
            timeline_recycler_view.visibility = View.VISIBLE
            mAdapter.notifyAdapter(expenses)
        }
    }


    private fun showError(){
        timeline_progress.visibility = View.GONE
        no_expense_text_view.visibility = View.VISIBLE
        timeline_recycler_view.visibility = View.GONE
        no_expense_text_view.text = resources.getString(R.string.error_message)
    }

    override fun onDestroy() {
        mViewModel.getExpensesLiveData.removeObserver(stateObserver)
        super.onDestroy()
    }
}