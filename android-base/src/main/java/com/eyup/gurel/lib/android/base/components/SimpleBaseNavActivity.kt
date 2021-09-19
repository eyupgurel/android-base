package com.eyup.gurel.lib.android.base.components

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.eyup.gurel.lib.android.base.home.ActivityStateHandler
import com.eyup.gurel.lib.android.base.lifecycle.ActivityLifecycleTask
import com.eyup.gurel.lib.android.base.ui.ActivityViewInterceptor
import com.eyup.gurel.lib.dagger2.di.Injector
import com.eyup.gurel.lib.dagger2.di.InstanceInfoProvider
import com.eyup.gurel.lib.dagger2.di.ScreenInjector
import com.eyup.gurel.lib.dagger2.di.ScreenInjectorProvider
import java.util.*
import javax.inject.Inject


abstract class SimpleBaseNavActivity : AppCompatActivity(), InstanceInfoProvider, ScreenInjectorProvider {

    @Inject  override lateinit var screenInjector: ScreenInjector
        @Inject  lateinit var activityLifecycleTasks: Set<@JvmSuppressWildcards ActivityLifecycleTask>
    @Inject  lateinit var activityStateHandler: ActivityStateHandler

    override lateinit var instanceId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instanceId = if (savedInstanceState != null) {
            savedInstanceState.getString(INSTANCE_ID_KEY)!!
        } else {
            UUID.randomUUID().toString()
        }
        Injector.inject(this)
        activityLifecycleTasks.forEach{
            it.onCreate(this)
        }
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
        activityLifecycleTasks.forEach{it.onDestroy(this)}
    }

    companion object {
        private const val INSTANCE_ID_KEY = "instance_id"
    }
}