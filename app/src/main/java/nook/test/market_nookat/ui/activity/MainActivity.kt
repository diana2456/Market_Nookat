package nook.test.market_nookat.ui.activity


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import nook.test.market_nookat.R
import nook.test.market_nookat.databinding.ActivityMainBinding
import nook.test.market_nookat.ui.base.BaseActivity
import nook.test.market_nookat.ui.fragment.util.Pref
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity: BaseActivity<MainViewModel, ActivityMainBinding>() {

    override val viewModel: MainViewModel by viewModel()
    private lateinit var back: Toast
    private var backPressedTime: Long = 0
    override fun inflateViewBinding(inflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initActivity() {
        val navView: BottomNavigationView = binding.navView
        binding.navView.background = null
        supportActionBar?.hide()

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_dashboard,
                R.id.navigation_profile,
                R.id.navigation_settings
            )
        )

        binding.fab.setOnClickListener {
            navController.navigate(R.id.addFragment)
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.addFragment) {
                navView.visibility = View.GONE
                binding.bottomAppBar.isVisible = false
                binding.fab.isVisible = false
            } else {
                navView.visibility = View.VISIBLE
                binding.bottomAppBar.isVisible = true
                binding.fab.isVisible = true
            }
        }

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        if (!Pref(applicationContext).isBoardingFire()) {
            navController.navigate(R.id.adsFireFragment)
            Toast.makeText(applicationContext, "работай пожалуйста ", Toast.LENGTH_SHORT).show()
        } else {
            navController.navigateUp()
        }
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.adsFireFragment) {
                navView.visibility = View.GONE
            } else {
                navView.visibility = View.VISIBLE
            }

        }
    }

    override fun onBackPressed() {
        if (::back.isInitialized && backPressedTime + 2000 > System.currentTimeMillis()) {
            back.cancel()
            super.onBackPressed()
            return
        } else {
            back = Toast.makeText(
                applicationContext,
                "Нажмите чтобы ещё раз выйти!",
                Toast.LENGTH_SHORT
            )
            back.show()
        }
        backPressedTime = System.currentTimeMillis()
    }

    override fun initResume() {
        TODO("Not yet implemented")
    }

    override fun initPause() {
        TODO("Not yet implemented")
    }

    override fun initDestroy() {
        TODO("Not yet implemented")
    }

}