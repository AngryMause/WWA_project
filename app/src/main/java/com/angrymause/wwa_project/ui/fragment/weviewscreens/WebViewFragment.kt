package com.angrymause.wwa_project.ui.fragment.weviewscreens

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import com.angrymause.wwa_project.databinding.FragmentWebviewBinding
import com.angrymause.wwa_project.ui.fragment.BaseFragment

class WebViewFragment : BaseFragment<FragmentWebviewBinding>(FragmentWebviewBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpWebView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setUpWebView() {
        binding.myWebView.settings.javaScriptEnabled = true
        binding.myWebView.loadUrl("https://www.pinterest.com/search/pins/?q=adnroid&rs=typed")
        binding.myWebView.webViewClient = WebViewClient()
        govBack()
    }


    private fun govBack() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (binding.myWebView.canGoBack()) {
                        binding.myWebView.goBack()
                    } else {
                        isEnabled = false
                        activity?.finish()
                    }
                }
            })
    }

}