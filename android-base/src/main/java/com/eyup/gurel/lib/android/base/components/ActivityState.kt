package com.eyup.gurel.lib.android.base.components

import androidx.navigation.NavController
import com.eyup.gurel.lib.android.base.home.ActivityStateHandler
import com.eyup.gurel.lib.dagger2.di.ActivityScope
import javax.inject.Inject

@ActivityScope
class ActivityState @Inject internal constructor (): ActivityStateHandler {
    override var navController: NavController? = null
}