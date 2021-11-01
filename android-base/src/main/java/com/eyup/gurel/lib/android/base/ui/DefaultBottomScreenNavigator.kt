package com.eyup.gurel.lib.android.base.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.eyup.gurel.lib.android.base.lifecycle.ActivityLifecycleTask
import com.eyup.gurel.lib.dagger2.di.ActivityScope
import com.squareup.moshi.Moshi
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject


@ActivityScope

class DefaultBottomScreenNavigator @Inject internal constructor(private val context: Context,
                                                                private val appCompatActivity: AppCompatActivity,
                                                                private val moshi: Moshi): ActivityLifecycleTask(), BottomScreenNavigator {
    private lateinit var navController: NavController
    override fun onSetupNavigator(navController: NavController) {
        this.navController = navController
    }

    override fun pop(): Boolean {
        return navController.popBackStack()
    }

    override fun navigate(actionId: Int): Single<Unit> {
        return Single.fromCallable{navController.navigate(actionId)}
    }

    override fun navigate(actionId: Int, argKey: String, argVal: String): Single<Unit> {
        return Single.fromCallable{ navController.navigate(actionId, bundleOf(argKey to argVal))}
    }

    override fun navigate(actionId: Int, argKey: String, argVal: Int): Single<Unit> {
        return Single.fromCallable{ navController.navigate(actionId, bundleOf(argKey to argVal))}
    }

    override fun navigateUp(): Single<Unit> {
        return Single.fromCallable{navController.navigateUp()}
    }

    override fun getNavigationController(): NavController {
        return navController
    }
}