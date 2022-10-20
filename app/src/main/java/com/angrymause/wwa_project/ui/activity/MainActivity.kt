package com.angrymause.wwa_project.ui.activity

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.angrymause.wwa_project.R
import com.angrymause.wwa_project.databinding.ActivityMainBinding
import com.angrymause.wwa_project.ui.fragment.splashfragment.SplashFragment


class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        replaceFragment(SplashFragment.newInstance())
    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null!!
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.my_container, fragment)
            addToBackStack(null).commit()
        }
    }
}

