package com.eyup.gurel.lib.android.base.ui

import androidx.navigation.NavController
import io.reactivex.rxjava3.core.Single

interface ScreenNavigator {
    fun pop(): Boolean
    fun pop (id:Int, inclusive:Boolean): Boolean
    fun navigate(actionId:Int): Single<Unit>
    fun navigate(actionId:Int,  argKey:String, argVal:String): Single<Unit>
    fun navigate(actionId:Int,  argKey:String, argVal:Int): Single<Unit>
    fun navigateUp(): Single<Unit>
    fun getNavigationController():NavController
}