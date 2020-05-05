package com.moneylion.expensetracker.core.expense.presentation.view.home

import android.util.SparseArray
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.moneylion.expensetracker.core.expense.presentation.view.home.fragments.DashboardFragment
import com.moneylion.expensetracker.core.expense.presentation.view.home.fragments.TimelineFragment
import java.lang.ref.SoftReference

class HomeAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var count = 2
    var fragments = SparseArray<SoftReference<Fragment>>()

    companion object {
        const val VIEWPAGER_INDEX_DASHBOARD_FRAGMENT = 0
        const val VIEWPAGER_INDEX_TIMELINE_FRAGMENT = 1
    }

    override fun getItem(position: Int): Fragment {
        when (position) {
            VIEWPAGER_INDEX_DASHBOARD_FRAGMENT -> {
                val dashboardFragment =
                    DashboardFragment()
                fragments.put(VIEWPAGER_INDEX_DASHBOARD_FRAGMENT, SoftReference(dashboardFragment))
                return dashboardFragment
            }
            VIEWPAGER_INDEX_TIMELINE_FRAGMENT ->{
                val timelineFragment =
                    TimelineFragment()
                fragments.put(VIEWPAGER_INDEX_TIMELINE_FRAGMENT, SoftReference(timelineFragment))
                return timelineFragment
            }
            else -> return Fragment()
        }
    }

    override fun getCount(): Int = count
}