package com.eyup.gurel.lib.android.base.components

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.eyup.gurel.lib.android.base.lifecycle.ActivityLifecycleTask
import com.eyup.gurel.lib.android.base.ui.ActivityViewInterceptor
import com.eyup.gurel.lib.android.base.ui.BottomScreenNavigator
import com.eyup.gurel.lib.android.base.ui.ScreenProvider
import com.eyup.gurel.lib.dagger2.di.Injector
import com.eyup.gurel.lib.dagger2.di.InstanceInfoProvider
import com.eyup.gurel.lib.dagger2.di.ScreenInjector
import com.eyup.gurel.lib.dagger2.di.ScreenInjectorProvider
import java.util.*
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity(), ScreenProvider, InstanceInfoProvider,ScreenInjectorProvider {
    @Inject
    override lateinit var screenInjector: ScreenInjector
    @Inject
    lateinit var bottomScreenNavigator: BottomScreenNavigator
    @Inject
    lateinit  var activityViewInterceptor: ActivityViewInterceptor
    @Inject
    lateinit var activityLifecycleTasks: Set<@JvmSuppressWildcards ActivityLifecycleTask>

    companion object {
        private const val INSTANCE_ID_KEY = "instance_id"
    }
    override lateinit var instanceId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        instanceId = if (savedInstanceState != null) {
            savedInstanceState.getString(INSTANCE_ID_KEY)!!
        } else {
            UUID.randomUUID().toString()
        }
        Injector.inject(this)
        super.onCreate(savedInstanceState)
        activityViewInterceptor.setContentView(this, layoutRes())
        activityLifecycleTasks.forEach { it.onCreate(this) }
    }

    override fun onStart() {
        super.onStart()
        activityLifecycleTasks.forEach { it.onStart(this) }
    }

    override fun onResume() {
        super.onResume()
        activityLifecycleTasks.forEach { it.onResume(this) }
    }

    override fun onPause() {
        super.onPause()
        activityLifecycleTasks.forEach { it.onPause(this) }
    }

    override fun onStop() {
        super.onStop()
        activityLifecycleTasks.forEach { it.onStop(this) }
    }

    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(INSTANCE_ID_KEY, instanceId)
    }

    override fun onBackPressed() {
        if (!bottomScreenNavigator.pop()) {
            super.onBackPressed()
        }
    }

    @LayoutRes
    protected abstract fun layoutRes(): Int
    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing) {
            Injector.clearComponent(this)
        }
        activityViewInterceptor.clear()
        activityLifecycleTasks.forEach{it.onDestroy(this)}
    }
}