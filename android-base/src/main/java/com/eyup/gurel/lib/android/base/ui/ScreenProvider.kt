package com.eyup.gurel.lib.android.base.ui

import androidx.fragment.app.Fragment

interface ScreenProvider {
    fun initialScreen(): Fragment
}