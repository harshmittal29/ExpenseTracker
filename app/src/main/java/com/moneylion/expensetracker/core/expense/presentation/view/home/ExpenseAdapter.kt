package com.moneylion.expensetracker.core.expense.presentation.view.home

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.moneylion.expensetracker.R
import com.moneylion.expensetracker.common.extension.inflate
import com.moneylion.expensetracker.common.extension.setHtmlText
import com.moneylion.expensetracker.core.expense.data.entity.Expense
import kotlinx.android.synthetic.main.item_timeline_expense.view.*

class ExpenseAdapter(var context: Context, var items : ArrayList<Expense>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){


    companion object {
        val TYPE_EXPENSE = 0x1f
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        lateinit var holder: RecyclerView.ViewHolder
        when (viewType) {
            TYPE_EXPENSE -> holder =
                TimelineExpenseAdapter(
                    parent.inflate(R.layout.item_timeline_expense)
                )
        }
        return holder
    }

    fun notifyAdapter(items : ArrayList<Expense>){
        this.items = items
        notifyDataSetChanged()
    }

    class TimelineExpenseAdapter(var view: View) : RecyclerView.ViewHolder(view) {

        init {
        }

        fun setData(expense: Expense) {
            view.timeline_category_text_view.setHtmlText(expense.category)
            view.timeline_description_text_view.text = expense.description
            val amount = expense.currency + expense.amount
            view.timeline_price_text_view.text = amount
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = items[position]
        when (getItemViewType(holder.adapterPosition))   {
            TYPE_EXPENSE -> {
                val timelineAdapter = holder as TimelineExpenseAdapter
                timelineAdapter.setData(data)
            }
        }
    }

    override fun getItemViewType(position: Int): Int =
        TYPE_EXPENSE
}