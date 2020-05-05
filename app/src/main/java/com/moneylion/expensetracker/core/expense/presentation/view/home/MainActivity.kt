package com.moneylion.expensetracker.core.expense.presentation.view.home

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.moneylion.expensetracker.R
import com.moneylion.expensetracker.common.Constants
import com.moneylion.expensetracker.core.expense.presentation.view.add_expense.AddExpenseActivity
import com.moneylion.expensetracker.common.extension.setTextViewDrawableColor
import com.moneylion.expensetracker.core.expense.presentation.view.home.fragments.DashboardFragment
import com.moneylion.expensetracker.core.expense.presentation.view.home.fragments.TimelineFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ref.SoftReference

/**
 * Main activity class
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUi()
    }

    /**
     * initialize the ui
     */
    private fun initUi(){
        setupViewPager()
        dashboard_tab_button.setOnClickListener {
            showDashboardFragment()
        }
        timeline_tab_button.setOnClickListener {
            showTimelineFragment()
        }
        add_expense_fab.setOnClickListener{
            AddExpenseActivity.startActivity(this)
        }
    }

    /**
     * set up view pager
     */
    private fun setupViewPager() {
        home_viewpager.adapter =
            HomeAdapter(
                supportFragmentManager
            )
        home_viewpager.offscreenPageLimit = 2
        home_viewpager.setSwipeable(false)
        showDashboardFragment()
    }

    private fun showDashboardFragment() {
        home_viewpager.setCurrentItem(HomeAdapter.VIEWPAGER_INDEX_DASHBOARD_FRAGMENT, true)
        selectTab(HomeAdapter.VIEWPAGER_INDEX_DASHBOARD_FRAGMENT)
    }

    private fun showTimelineFragment() {
        home_viewpager.setCurrentItem(HomeAdapter.VIEWPAGER_INDEX_TIMELINE_FRAGMENT, true)
        selectTab(HomeAdapter.VIEWPAGER_INDEX_TIMELINE_FRAGMENT)
    }

    /**
     * change the ui of the selected tab
     * @param type tab which is selected
     */
    private fun selectTab(type: Int) {

        dashboard_tab_button.setTextViewDrawableColor(R.color.hint_grey)
        timeline_tab_button.setTextViewDrawableColor(R.color.hint_grey)

        dashboard_tab_button.setTextColor(ContextCompat.getColor(this, R.color.hint_grey))
        timeline_tab_button.setTextColor(ContextCompat.getColor(this, R.color.hint_grey))

        when (type) {
            HomeAdapter.VIEWPAGER_INDEX_DASHBOARD_FRAGMENT -> {
                dashboard_tab_button.setTextColor(ContextCompat.getColor(this, R.color.colorAccent))
                val title = this.resources.getString(R.string.dashboard)
                home_toolbar.title = title
                dashboard_tab_button.setTextViewDrawableColor(R.color.colorAccent)
            }

            HomeAdapter.VIEWPAGER_INDEX_TIMELINE_FRAGMENT -> {
                timeline_tab_button.setTextColor(ContextCompat.getColor(this, R.color.colorAccent))
                val title = this.resources.getString(R.string.timeline)
                home_toolbar.title = title
                timeline_tab_button.setTextViewDrawableColor(R.color.colorAccent)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.REQUEST_CODE_ADD_EXPENSE) {
            if(resultCode == Activity.RESULT_OK){
                val timelineFragment = getFragmentFromType(HomeAdapter.VIEWPAGER_INDEX_TIMELINE_FRAGMENT) as TimelineFragment?
                timelineFragment?.getTimelineFromDatabase()

                val dashboardFragment = getFragmentFromType(HomeAdapter.VIEWPAGER_INDEX_DASHBOARD_FRAGMENT) as DashboardFragment?
                dashboardFragment?.getDashboardFromDatabase()
            }
        }
    }

    /**
     * Get a fragment in {@link HomeAdapter}
     * @see HomeAdapter
     */
    private fun getFragmentFromType(type: Int): Fragment? {
        var fragment: Fragment? = null
        val fragmentSoftReference: SoftReference<Fragment>?

        val adapter = home_viewpager.adapter as HomeAdapter?
        val fragments = adapter?.fragments
        if (fragments != null) {
            fragmentSoftReference = fragments.get(type)
            if (fragmentSoftReference == null) {
                try {
                    fragment = adapter.instantiateItem(home_viewpager, type) as Fragment
                } catch (e: Exception) {
                }
            } else {
                fragment = fragmentSoftReference.get()
            }
            return fragment
        }
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}