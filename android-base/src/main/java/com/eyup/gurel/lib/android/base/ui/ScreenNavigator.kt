package com.eyup.gurel.lib.android.base.ui

import io.reactivex.rxjava3.core.Completable

interface ScreenNavigator {
    fun pop(): Boolean
    fun navigate(actionId:Int): Completable
    fun navigate(actionId:Int,  argKey:String, argVal:String): Completable
    fun navigate(actionId:Int,  argKey:String, argVal:Int): Completable
    fun navigateUp(): Completable
}