package com.eyup.gurel.lib.android.base.ui

import com.eyup.gurel.lib.android.base.lifecycle.ActivityLifecycleTask
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet

@Module
abstract class ScreenNavigatorModule {
    @Binds
    abstract fun provideDefaultScreenNavigator(defaultScreenNavigator: DefaultScreenNavigator): ScreenNavigator
    @Binds
    @IntoSet
    abstract fun bindDefaultScreenNavigatorTask(defaultScreenNavigator: DefaultScreenNavigator): ActivityLifecycleTask
}