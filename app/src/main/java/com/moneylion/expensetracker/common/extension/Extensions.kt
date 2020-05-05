package com.moneylion.expensetracker.common.extension

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat

/**
 * Inflate a layout to the view
 * @param layoutRes layout resource to be inflated
 */
fun ViewGroup.inflate(layoutRes: Int): View =
    LayoutInflater.from(context).inflate(layoutRes, this, false)

/**
 * Set the color for compound drawable of a TextView
 * @param color int resource of a color
 */
fun TextView.setTextViewDrawableColor(color: Int) {
    for (drawable in this.compoundDrawables) {
        if (drawable != null) {
            drawable.colorFilter = PorterDuffColorFilter(
                ContextCompat.getColor(this.context, color), PorterDuff.Mode.SRC_IN
            )
        }
    }
}

/**
 * Set HTML text on a TextView
 * @param data HTML data
 */
fun TextView.setHtmlText(data: String){
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
        this.text = Html.fromHtml(data)
    }else{
        this.text = Html.fromHtml(data, Html.FROM_HTML_MODE_COMPACT)
    }
}