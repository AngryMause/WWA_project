package com.angrymause.wwa_project.ui.fragment.splashfragment

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.angrymause.wwa_project.databinding.FragmentSplashBinding
import com.angrymause.wwa_project.network.NetworkStatus
import com.angrymause.wwa_project.network.NetworkStatusTracker
import com.angrymause.wwa_project.ui.fragment.BaseFragment
import com.angrymause.wwa_project.ui.fragment.gamescreens.GameFragment
import com.angrymause.wwa_project.ui.fragment.weviewscreens.WebViewFragment
import kotlinx.coroutines.launch


class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {
    private val splashViewModel: SplashViewModel by viewModels()
    private val networkStatusTracker by lazy { NetworkStatusTracker(requireContext()) }

    companion object {
        fun newInstance(): SplashFragment {
            return SplashFragment()
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkInternetConnection()
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun checkInternetConnection() {
        lifecycleScope.launch {
            networkStatusTracker.networkStatus.collect { networkStatus ->
                when (networkStatus) {
                    is NetworkStatus.Available -> {
                        try {
                            splashViewModel.resp.observe(viewLifecycleOwner) { isTrue ->
                                if (isTrue) {
                                    replaceFragment(WebViewFragment.newInstance())
                                } else replaceFragment(GameFragment.newInstance())
                            }
                        } catch (e: IllegalStateException) {
                            Log.d("SplashFrag ", "exception: ${e.message} ")
                        }
                    }
                    is NetworkStatus.LostConnection -> {
                        Toast.makeText(requireContext(),
                            " Internet connection is lost",
                            Toast.LENGTH_LONG)
                            .show()
                    }
                    is NetworkStatus.Unavailable -> {
                        Toast.makeText(requireContext(),
                            "No Internet Connection",
                            Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }
        }
    }


}