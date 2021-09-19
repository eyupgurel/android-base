package com.eyup.gurel.lib.android.base.ui

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import com.eyup.gurel.lib.android.base.R
import com.eyup.gurel.lib.android.base.settings.DebugPreferences
import com.jakewharton.rxbinding4.widget.checkedChanges
import com.google.android.material.switchmaterial.SwitchMaterial
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

class DebugActivityViewInterceptor @Inject internal constructor(private val debugPreferences: DebugPreferences) :ActivityViewInterceptor {
    private lateinit var mockResponseSwitch: SwitchMaterial
    private val disposables = CompositeDisposable()
    override fun setContentView(activity: Activity, layoutRes: Int) {
        val debugLayout = LayoutInflater.from(activity).inflate(R.layout.debug_drawer, null)
        mockResponseSwitch = debugLayout.findViewById(R.id.sw_mock_responses)
        initializePrefs()
        val activityLayout = LayoutInflater.from(activity).inflate(layoutRes, null)
        debugLayout.findViewById<ViewGroup>(R.id.activity_layout_container).addView(activityLayout)
        activity.setContentView(debugLayout)
    }

    override fun clear() {
        disposables.clear()
    }

    private fun initializePrefs() {
        mockResponseSwitch.isChecked = debugPreferences.useMockResponsesEnabled()
        disposables.addAll(
            mockResponseSwitch.checkedChanges()
                .subscribe { useMockResponses: Boolean? -> debugPreferences.setUseMockResponses(useMockResponses!!) }
        )
    }
}