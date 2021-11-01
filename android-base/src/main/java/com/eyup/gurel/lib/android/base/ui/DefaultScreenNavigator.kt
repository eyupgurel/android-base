package com.eyup.gurel.lib.android.base.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.eyup.gurel.lib.android.base.home.ActivityStateHandler
import com.eyup.gurel.lib.android.base.lifecycle.ActivityLifecycleTask
import com.eyup.gurel.lib.dagger2.di.ActivityScope
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject


@ActivityScope
open class DefaultScreenNavigator @Inject  constructor(private val context: Context,
                                                       private val appCompatActivity: AppCompatActivity,
                                                       private val activityStateHandler: ActivityStateHandler): ActivityLifecycleTask(), ScreenNavigator {

    override fun pop(): Boolean {
        return activityStateHandler.navController!!.popBackStack()
    }

    override fun navigate(actionId: Int): Single<Unit> {
        return Single.fromCallable { activityStateHandler.navController!!.navigate(actionId) }
    }


    override fun navigate(actionId: Int, argKey: String, argVal: String): Single<Unit> {
        return Single.fromCallable {
            activityStateHandler.navController!!.navigate(
                actionId,
                bundleOf(argKey to argVal)
            )
        }
    }

    override fun navigate(actionId: Int, argKey: String, argVal: Int): Single<Unit> {
        return Single.fromCallable {
            activityStateHandler.navController!!.navigate(
                actionId,
                bundleOf(argKey to argVal)
            )
        }
    }


    override fun navigateUp(): Single<Unit> {
        return Single.fromCallable { activityStateHandler.navController!!.navigateUp() }
    }

    override fun getNavigationController(): NavController {
        return activityStateHandler.navController!!
    }
}