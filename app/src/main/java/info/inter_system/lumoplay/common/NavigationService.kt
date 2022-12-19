package info.inter_system.lumoplay.common

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import info.inter_system.lumoplay.MainActivity
import info.inter_system.lumoplay.R
import info.inter_system.lumoplay.details.DetailsFragment
import info.inter_system.lumoplay.permissions.PermissionsFragment
import info.inter_system.lumoplay.scan.ScanFragment
import java.lang.ref.WeakReference

class NavigationService {
    var activityWR: WeakReference<MainActivity>? = null

    var systemBackHandler: (() -> Unit)? = null

    fun setMainActivity(activity: MainActivity?) {
        activityWR = if (activity != null) {
            WeakReference(activity)
        } else {
            null
        }
    }

    fun goBack() {
        activityWR?.get()?.let { activity ->
            with(activity) {
                runOnUiThread {
                    supportFragmentManager.popBackStack()
                }
            }
        }
    }

    fun systemBack() {
        systemBackHandler?.let { it() } ?: goBack()
    }

    fun navigateToPermissions() {
        navigateTo(PermissionsFragment(), PermissionsFragment::class.simpleName, false)
    }

    fun navigateToScan() {
        clearBackStack()
        navigateTo(ScanFragment(), ScanFragment::class.simpleName)
    }

    fun navigateToDetails(name: String) {
        navigateTo(DetailsFragment().apply { arguments = Bundle().apply { putString("name", name) } }, DetailsFragment::class.simpleName)
    }

    private fun clearBackStack() {
        activityWR?.get()?.let { activity ->
            with(activity) {
                runOnUiThread {
                    if (supportFragmentManager.backStackEntryCount > 0 ) {
                        val entry = supportFragmentManager.getBackStackEntryAt(0)
                        supportFragmentManager.popBackStackImmediate(
                            entry.id,
                            FragmentManager.POP_BACK_STACK_INCLUSIVE
                        )
                    }
                }
            }
        }
    }

    private fun navigateTo(fragment: Fragment, name: String?, addToBackStack: Boolean = true) {
        activityWR?.get()?.let { activity ->
            with(activity) {
                supportFragmentManager.commit {
                    if (addToBackStack) {
                        addToBackStack(name)
                    }
                    replace(R.id.container, fragment)
                }
            }
        }
    }

    companion object {
        private const val TAG = "NavigationService"

        lateinit var instance: NavigationService

        fun create() {
            instance = NavigationService()
        }
    }
}