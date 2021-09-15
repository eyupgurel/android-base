package com.eyup.gurel.lib.android.base.components

import com.eyup.gurel.lib.android.base.home.ActivityStateHandler
import com.eyup.gurel.lib.dagger2.di.ActivityScope
import com.eyup.gurel.lib.dagger2.di.ForActivityScreen
import com.eyup.gurel.lib.dagger2.lifecycle.DisposableManager
import dagger.Binds
import dagger.Module

@Module
abstract class ActivityModule {
    @Binds
    @ActivityScope
    @ForActivityScreen
    abstract fun provideDisposableManager(disposableManager: DisposableManager): DisposableManager

    @Binds
    abstract fun provideActivityState(activityState: ActivityState): ActivityStateHandler
}