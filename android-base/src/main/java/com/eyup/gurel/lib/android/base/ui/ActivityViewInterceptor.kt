package com.eyup.gurel.lib.android.base.ui

import android.app.Activity
import androidx.annotation.LayoutRes

interface ActivityViewInterceptor {
    fun setContentView(activity: Activity, @LayoutRes layoutRes: Int)
    fun clear()

    companion object {
        val DEFAULT: ActivityViewInterceptor = object : ActivityViewInterceptor {
            override fun setContentView(activity: Activity, layoutRes: Int) {
                activity.setContentView(layoutRes)
            }
            override fun clear() {}
        }
        fun getDefault() = DEFAULT
    }
}