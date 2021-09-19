package com.eyup.gurel.lib.android.base.settings

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DebugPreferences @Inject internal constructor(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("debug_settings", Context.MODE_PRIVATE)

    fun useMockResponsesEnabled(): Boolean {
        return sharedPreferences.getBoolean(MOCK_RESPONSES_KEY, false)
    }

    fun setUseMockResponses(useMockResponses: Boolean) {
        sharedPreferences.edit().putBoolean(MOCK_RESPONSES_KEY, useMockResponses).apply()
    }

    companion object {
        private const val MOCK_RESPONSES_KEY = "mock_response"
    }

}