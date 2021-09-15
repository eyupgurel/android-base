package com.eyup.gurel.lib.android.base.components

import com.jakewharton.rxrelay3.BehaviorRelay
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.Consumer

open class BaseLoadingViewModel {
    private val loadingRelay = BehaviorRelay.create<Boolean>()
    private val errorRelay = BehaviorRelay.create<Int>()
    fun loading(): Observable<Boolean> = loadingRelay
    fun loadingUpdated(): Consumer<Boolean> = loadingRelay
    fun error(): Observable<Int> = errorRelay
    fun errorUpdated(): Consumer<Int> = errorRelay
}
