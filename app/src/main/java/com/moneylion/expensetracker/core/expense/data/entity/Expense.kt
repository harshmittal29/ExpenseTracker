package com.moneylion.expensetracker.core.expense.data.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**
 * Entity class
 */
@Entity
@Parcelize
data class Expense(@PrimaryKey (autoGenerate = true) var id: Int,
                   var description: String,
                   var category: String,
                   var currency: String,
                   var amount: String): Parcelable {

    @Ignore
    constructor() :this(0, "", "", "","0.0")


}