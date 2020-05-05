package com.moneylion.expensetracker.common

import android.content.Context
import com.moneylion.expensetracker.common.di.AppComponent
import com.moneylion.expensetracker.common.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

/**
 * Application class extending DaggerApplication for Dependency Injection
 */
open class ExpenseTrackerApplication : DaggerApplication(){

    lateinit var mAppComponent: AppComponent

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = mAppComponent

    override fun onCreate() {
        super.onCreate()
    }
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        appContext = this
        initDaggerComponent()
    }

    open fun initDaggerComponent() {
        mAppComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
    }

    companion object {
        @get:Synchronized
        var appContext: Context? = null
            private set
    }
}