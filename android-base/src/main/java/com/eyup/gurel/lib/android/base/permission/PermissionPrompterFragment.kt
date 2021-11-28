package com.eyup.gurel.lib.android.base.permission

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.eyup.gurel.lib.android.base.R
import com.eyup.gurel.lib.android.base.components.BaseFragment
import com.eyup.gurel.lib.android.base.contract.Prompter
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber


abstract class PermissionPrompterFragment: BaseFragment() {
    protected abstract var presenter: Prompter
    protected abstract var permission: String
    protected abstract var requestCode: Int
    protected abstract var permissionDenialExplanation: Int
    protected abstract var permissionRationale: Int
    protected abstract var frame: View
    protected abstract var applicationId:String
    protected abstract var snackbarActionTitle:String

    // If the user denied a previous permission request, but didn't check "Don't ask again", these
    // Snackbars provided an explanation for why user should approve, i.e., the additional
    // rationale.
    private val rationalSnackbar by lazy {
        Snackbar.make(
            frame,
            permissionRationale,
            Snackbar.LENGTH_LONG
        )
            .setAction(R.string.ok) {
                requestPermissions(
                    arrayOf(permission),
                    requestCode
                )
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val permissionApproved = hasPermission(requireContext(),permission) ?: return
        if (permissionApproved) {
            presenter.prompt()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        Timber.d("onRequestPermissionResult")

        when (requestCode) {
            requestCode -> when {
                grantResults.isEmpty() ->
                    // If user interaction was interrupted, the permission request
                    // is cancelled and you receive an empty array.
                    Timber.d("User interaction was cancelled.")

                grantResults[0] == PackageManager.PERMISSION_GRANTED ->
                    presenter.prompt()
                else -> {
                    val permissionDeniedExplanation =
                        if (requestCode == requestCode) {
                            permissionDenialExplanation
                        } else {
                            throw Exception("Unexpected request code")
                        }
                    Snackbar.make(
                        frame,
                        permissionDeniedExplanation,
                        Snackbar.LENGTH_LONG
                    )
                        .setAction(snackbarActionTitle) {
                            // Build intent that displays the App settings screen.
                            val intent = Intent()
                            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                            val uri = Uri.fromParts(
                                "package",
                                applicationId, //BuildConfig.APPLICATION_ID,
                                null
                            )
                            intent.data = uri
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                        }
                        .show()
                }
            }
        }
    }

    protected fun requestPermission() {
        val permissionApproved =  hasPermission(requireContext(), permission) ?: return
        if (permissionApproved) {
            presenter.prompt()
        } else {
            requestPermissionWithRationale(
                this,
                permission,
                requestCode,
                rationalSnackbar)
        }
    }

    companion object{
        /**
         * Helper functions to simplify permission checks/requests.
         */
        fun hasPermission(context:Context, permission: String): Boolean {

            // Background permissions didn't exit prior to Q, so it's approved by default.
            if (permission == Manifest.permission.ACCESS_BACKGROUND_LOCATION &&
                android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.Q) {
                return true
            }

            return ActivityCompat.checkSelfPermission(context, permission) ==
                    PackageManager.PERMISSION_GRANTED
        }
        /**
         * Requests permission and if the user denied a previous request, but didn't check
         * "Don't ask again", we provide additional rationale.
         *
         * Note: The Snackbar should have an action to request the permission.
         */
        fun requestPermissionWithRationale(
            fragment:Fragment,
            permission: String,
            requestCode: Int,
            snackbar: Snackbar
        ) {
            val provideRationale = fragment.shouldShowRequestPermissionRationale(permission)

            if (provideRationale) {
                snackbar.show()
            } else {
                fragment.requestPermissions(arrayOf(permission), requestCode)
            }
        }
    }
}