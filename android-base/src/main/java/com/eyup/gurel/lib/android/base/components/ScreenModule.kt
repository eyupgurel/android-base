package com.eyup.gurel.lib.android.base.components

import com.eyup.gurel.lib.android.base.lifecycle.ScreenLifecycleTask
import com.eyup.gurel.lib.dagger2.di.ForScreen
import com.eyup.gurel.lib.dagger2.di.ScreenScope
import com.eyup.gurel.lib.dagger2.lifecycle.DisposableManager
import dagger.Binds
import dagger.Module
import dagger.multibindings.Multibinds

@Module
abstract class ScreenModule {
    @Binds
    @ScreenScope
    @ForScreen
    abstract fun provideDisposableManager(disposableManager: DisposableManager): DisposableManager
    @Multibinds
    abstract fun screenLifecycleTasks(): Set<ScreenLifecycleTask>
}