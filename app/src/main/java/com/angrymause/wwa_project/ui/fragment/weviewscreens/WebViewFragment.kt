package com.angrymause.wwa_project.ui.fragment.weviewscreens

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.angrymause.wwa_project.databinding.FragmentWebviewBinding
import com.angrymause.wwa_project.ui.fragment.BaseFragment
import kotlinx.coroutines.launch

class WebViewFragment : BaseFragment<FragmentWebviewBinding>(FragmentWebviewBinding::inflate) {
    companion object {
        fun newInstance(): WebViewFragment {
            return WebViewFragment()
        }
    }

    private val webViewViewMode: WebViewViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpWebView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setUpWebView() {
        binding.myWebView.settings.javaScriptEnabled = true
        setUpWebViewUrl()
        binding.myWebView.webViewClient = WebViewClient()
        govBack()
    }

    private fun setUpWebViewUrl() {
        viewLifecycleOwner.lifecycleScope.launch {
            webViewViewMode.resp.observe(viewLifecycleOwner) { url ->
                binding.myWebView.loadUrl(url)
            }
        }
    }


    private fun govBack() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (binding.myWebView.canGoBack()) {
                        binding.myWebView.goBack()
                    } else {
                        isEnabled = false
                    }
                }
            })
    }

}