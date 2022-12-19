package info.inter_system.lumoplay

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentContainerView
import info.inter_system.lumoplay.common.NavigationService

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowCompat.getInsetsController(window, window.decorView).show(
            WindowInsetsCompat.Type.statusBars() or WindowInsetsCompat.Type.navigationBars()
        )

        setContentView(FragmentContainerView(this).apply {
            id = R.id.container
        })
        NavigationService.instance.setMainActivity(this)
        NavigationService.instance.navigateToPermissions()
    }

    override fun onDestroy() {
        super.onDestroy()
        NavigationService.instance.setMainActivity(null)
    }

    override fun onBackPressed() {
        NavigationService.instance.systemBack()
    }
}

