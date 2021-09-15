package com.eyup.gurel.lib.android.base.components

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.eyup.gurel.lib.android.base.lifecycle.ActivityLifecycleTask
import com.eyup.gurel.lib.android.base.ui.ActivityViewInterceptor
import com.eyup.gurel.lib.dagger2.di.Injector
import com.eyup.gurel.lib.dagger2.di.InstanceInfoProvider
import com.eyup.gurel.lib.dagger2.di.ScreenInjector
import com.eyup.gurel.lib.dagger2.di.ScreenInjectorProvider
import java.util.*
import javax.inject.Inject

abstract class BaseBottomNavActivity : AppCompatActivity(), InstanceInfoProvider, ScreenInjectorProvider {

    @Inject
    override lateinit var screenInjector: ScreenInjector
    @Inject
    lateinit  var activityViewInterceptor: ActivityViewInterceptor
    @Inject
    lateinit var activityLifecycleTasks: Set<@JvmSuppressWildcards ActivityLifecycleTask>

    protected lateinit var currentNavController: LiveData<NavController>

    companion object {
        private const val INSTANCE_ID_KEY = "instance_id"
    }



    override lateinit var instanceId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instanceId = if (savedInstanceState != null) {
            savedInstanceState.getString(INSTANCE_ID_KEY)!!
        } else {
            UUID.randomUUID().toString()
        }
        Injector.inject(this)
        activityViewInterceptor.setContentView(this, layoutRes())
        activityLifecycleTasks.forEach{it.onCreate(this)}
    }

    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(INSTANCE_ID_KEY, instanceId)
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