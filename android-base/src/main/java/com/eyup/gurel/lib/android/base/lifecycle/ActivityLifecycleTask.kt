package com.eyup.gurel.lib.android.base.lifecycle

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController

abstract class ActivityLifecycleTask {
    open fun onCreate(activity: AppCompatActivity) {}
    fun onStart(activity: AppCompatActivity) {}
    fun onResume(activity: AppCompatActivity) {}
    fun onPause(activity: AppCompatActivity) {}
    fun onStop(activity: AppCompatActivity) {}
    open fun onDestroy(activity: AppCompatActivity) {}
    open fun onSetupNavigator(navController: NavController) {}
}