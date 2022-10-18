package com.angrymause.wwa_project.ui.fragment.splashfragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.angrymause.wwa_project.databinding.FragmentSplashBinding
import com.angrymause.wwa_project.ui.fragment.BaseFragment


class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {
    private val splashViewModel: SplashViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        lifecycleScope.launch {
//            splashViewModel.resp.observe(viewLifecycleOwner) {
//                if (!it) {
//                    replaceFragment(GameFragment())
//                } else replaceFragment(WebViewFragment())
//            }
//        }

    }

}