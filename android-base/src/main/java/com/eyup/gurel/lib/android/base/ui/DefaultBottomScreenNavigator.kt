package com.eyup.gurel.lib.android.base.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.eyup.gurel.lib.android.base.lifecycle.ActivityLifecycleTask
import com.eyup.gurel.lib.dagger2.di.ActivityScope
import com.squareup.moshi.Moshi
import io.reactivex.rxjava3.core.Completable
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

    override fun navigate(actionId: Int): Completable {
        return Completable.fromCallable{navController.navigate(actionId)}
    }

    override fun navigate(actionId: Int, argKey: String, argVal: String): Completable {
        return Completable.fromCallable{ navController.navigate(actionId, bundleOf(argKey to argVal))}
    }

    override fun navigate(actionId: Int, argKey: String, argVal: Int): Completable {
        return Completable.fromCallable{ navController.navigate(actionId, bundleOf(argKey to argVal))}
    }

    override fun navigateUp(): Completable {
        return Completable.fromCallable{navController.navigateUp()}
    }
}