package com.moneylion.expensetracker.custom_views

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.viewpager.widget.ViewPager

/**
 * Non swipe View Pager
 * We override touch functionality in this to disable the swipe with touch
 * View pager will change pages only on a click
 */
class NonSwipeableViewPager : ViewPager {
    var mSwipeable = false
    private var mItemTouchListener: ItemTouchListener? = null
    fun setSwipeable(swipeable: Boolean) {
        mSwipeable = swipeable
    }

    constructor(context: Context?) : super(context!!) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!,
        attrs
    ) {
    }

    fun setItemTouchedListener(listener: ItemTouchListener?) {
        mItemTouchListener = listener
    }

    override fun canScroll(
        v: View,
        arg1: Boolean,
        arg2: Int,
        arg3: Int,
        arg4: Int
    ): Boolean {
        return if (v !== this && v is ViewPager) true else super.canScroll(
            v,
            arg1,
            arg2,
            arg3,
            arg4
        )
    }

    override fun onInterceptTouchEvent(arg0: MotionEvent): Boolean {
        if (mItemTouchListener != null) {
            mItemTouchListener!!.itemTouched(arg0)
        }
        try {
            if (mSwipeable) {
                return super.onInterceptTouchEvent(arg0)
            }
        } catch (var3: Exception) {
            var3.printStackTrace()
        }
        return mSwipeable
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        try {
            if (mSwipeable) {
                return super.onTouchEvent(event)
            }
        } catch (var3: Exception) {
            var3.printStackTrace()
        }
        return mSwipeable
    }

    interface ItemTouchListener {
        fun itemTouched(var1: MotionEvent?)
    }
}