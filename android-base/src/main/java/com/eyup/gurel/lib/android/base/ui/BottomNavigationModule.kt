package com.eyup.gurel.lib.android.base.ui

import com.eyup.gurel.lib.android.base.lifecycle.ActivityLifecycleTask
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet

@Module
abstract class BottomNavigationModule {
    @Binds
    abstract fun provideDefaultBottomScreenNavigator(defaultBottomScreenNavigator: DefaultBottomScreenNavigator): BottomScreenNavigator
    @Binds
    @IntoSet
    abstract fun bindDefaultBottomScreenNavigatorTask(defaultBottomScreenNavigator: DefaultBottomScreenNavigator): ActivityLifecycleTask
}