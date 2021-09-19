package com.eyup.gurel.lib.android.base.ui

import dagger.Binds
import dagger.Module

@Module
abstract class ActivityViewInterceptorModule {
    @Binds
    abstract fun bindDebugActivityViewInterceptor(activityViewInterceptor: DebugActivityViewInterceptor): ActivityViewInterceptor
}