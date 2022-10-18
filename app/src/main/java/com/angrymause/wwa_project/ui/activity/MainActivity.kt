package com.angrymause.wwa_project.ui.activity

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.angrymause.wwa_project.R
import com.angrymause.wwa_project.databinding.ActivityMainBinding
import com.angrymause.wwa_project.network.NetworkStatus
import com.angrymause.wwa_project.network.NetworkStatusTracker
import com.angrymause.wwa_project.ui.fragment.gamescreens.GameFragment
import com.angrymause.wwa_project.ui.fragment.splashfragment.SplashFragment
import com.angrymause.wwa_project.ui.fragment.splashfragment.SplashViewModel
import com.angrymause.wwa_project.ui.fragment.weviewscreens.WebViewFragment
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private val networkStatus by lazy { NetworkStatusTracker(this) }
    private var binding: ActivityMainBinding? = null
    private val splashViewModel: SplashViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        checkInternetConnection()
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun checkInternetConnection() {
        lifecycleScope.launch {
            networkStatus.networkStatus.collect {
                when (it) {
                    is NetworkStatus.Available -> {
                        lifecycleScope.launch {
                            splashViewModel.resp.observe(this@MainActivity) { isTrue ->
                                if (!isTrue) {
                                    replaceFragment(GameFragment())
                                } else replaceFragment(WebViewFragment())
                            }
                        }
                    }
                    is NetworkStatus.LostConnection -> {
                        Toast.makeText(this@MainActivity,
                            " Internet connection lost",
                            Toast.LENGTH_LONG)
                            .show()

                    }
                    is NetworkStatus.Unavailable -> {
                        Toast.makeText(this@MainActivity,
                            "No Internet Connection",
                            Toast.LENGTH_LONG)
                            .show()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.my_container, SplashFragment())
                            .commit()
                    }
                }
            }
        }
    }


    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager?.beginTransaction()?.apply {
            replace(R.id.my_container, fragment)
            addToBackStack(null)
                .commit()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null!!
    }


}
