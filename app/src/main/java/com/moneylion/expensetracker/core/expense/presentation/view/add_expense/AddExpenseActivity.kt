package com.moneylion.expensetracker.core.expense.presentation.view.add_expense

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.moneylion.expensetracker.R
import com.moneylion.expensetracker.common.Constants
import com.moneylion.expensetracker.common.ExpenseTrackerApplication
import com.moneylion.expensetracker.common.GenericResult
import com.moneylion.expensetracker.core.expense.data.entity.Expense
import com.moneylion.expensetracker.core.expense.di.DaggerExpenseComponent
import com.moneylion.expensetracker.core.expense.presentation.view_model.ExpenseViewModel
import com.moneylion.expensetracker.core.expense.presentation.view_model.ExpenseViewModelFactory
import kotlinx.android.synthetic.main.activity_add_expense.*
import javax.inject.Inject


class AddExpenseActivity : AppCompatActivity(){

    @Inject
    lateinit var mwModelFactory: ExpenseViewModelFactory

    private lateinit var mViewModel: ExpenseViewModel

    companion object {

        fun startActivity(context: Context) {
            val intent = Intent(context, AddExpenseActivity::class.java)
            (context as Activity).startActivityForResult(intent, Constants.REQUEST_CODE_ADD_EXPENSE)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)
        overridePendingTransition(R.anim.slide_in_bottom, 0)
        DaggerExpenseComponent
            .builder()
            .appComponent((application as ExpenseTrackerApplication).mAppComponent)
            .build()
            .inject(this)

        mViewModel = ViewModelProvider(this, mwModelFactory).get(ExpenseViewModel::class.java)
        mViewModel.saveExpenseLiveData.observe(this, stateObserver)
        initUi()
    }

    private val stateObserver = Observer<GenericResult<Boolean>?> { state ->
        state?.let {
            when (it) {
                is GenericResult.Loading -> {
                }
                is GenericResult.Success -> {
                    setResult(Activity.RESULT_OK)
                    this.finish()
                }
                is GenericResult.Error.NetworkError -> {
                }
                is GenericResult.Error.GenericError -> {
                }
                is GenericResult.Empty -> {
                }
            }
        }
    }

    private fun initUi(){

        val items = resources.getStringArray(R.array.expense_categories);
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            R.layout.custom_spinner_items, R.id.spinner_item_text_view, items)
        expense_category_spinner.adapter = adapter
        adapter.setDropDownViewResource(R.layout.custom_spinner_items)

        val currencyAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            R.layout.custom_spinner_items, R.id.spinner_item_text_view, arrayOf("MYR"))
        expense_currency_spinner.isEnabled = false
        expense_currency_spinner.isClickable = false
        currencyAdapter.setDropDownViewResource(R.layout.custom_spinner_items)
        expense_currency_spinner.adapter = currencyAdapter
        save_expense_button.setOnClickListener {
            if(sanitizeInput()){
                saveExpense()
            }
        }

        expense_description_edit_text.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                expense_description_layout.error = null
            }
        })

        expense_amount_edit_text.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                expense_amount_layout.error = null
            }
        })
        setupToolbar()
    }

    /**
     * Save expense to local database
     */
    private fun saveExpense(){
        val description = expense_description_edit_text.text.toString()
        val amount = expense_amount_edit_text.text.toString()
        val expense = Expense()
        expense.description = description
        expense.amount = amount
        expense.currency = expense_currency_spinner.selectedItem.toString()
        expense.category = expense_category_spinner.selectedItem.toString()
        mViewModel.saveExpense(expense)
    }

    private fun sanitizeInput(): Boolean{
        val description = expense_description_edit_text.text.toString()
        val amount = expense_amount_edit_text.text.toString()

        if(description.isEmpty()){
            expense_description_layout.error = resources.getString(R.string.description_required)
            expense_description_edit_text.requestFocus()
            return false
        }else if(amount.isEmpty()){
            expense_amount_layout.error = resources.getString(R.string.amount_required)
            expense_amount_edit_text.requestFocus()
            return false
        }
        return true
    }

    private fun setupToolbar() {
        setSupportActionBar(add_expense_toolbar)
        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)
        actionbar?.setDisplayShowHomeEnabled(true)
        actionbar?.setHomeButtonEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0, R.anim.slide_out_down)
    }

    override fun onDestroy() {
        mViewModel.saveExpenseLiveData.removeObserver(stateObserver)
        super.onDestroy()
    }
}