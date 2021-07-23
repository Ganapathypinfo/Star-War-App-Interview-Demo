package com.assignment.demo.starwarsapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.assignment.demo.starwarsapp.R
import com.assignment.demo.starwarsapp.base.BaseActivity
import com.assignment.demo.starwarsapp.home.HomeFragment

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        openHomeFragment()
    }

    private fun openHomeFragment() {
        val homeFragment = HomeFragment()
        replaceFragment(homeFragment)
    }

     public override fun addFragment(fragment: Fragment) {
        super.addFragment(fragment)
    }

    fun setStatusBarGreen() {
        super.setStatusBar(resources.getColor(R.color.fragment_background))
    }
}