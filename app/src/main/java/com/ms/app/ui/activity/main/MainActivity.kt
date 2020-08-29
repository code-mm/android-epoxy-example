package com.ms.app.ui.activity.main

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ms.app.R;

import com.ms.app.ui.base.BaseAppCompatActivity
import com.ms.app.ui.base.StatusBarUtil
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseAppCompatActivity() {
    private var navController: NavController? = null
    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        super.initView()

        navController = Navigation.findNavController(this, R.id.fragment)
        navController!!.setGraph(R.navigation.nav_graph)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bottomNavigationView!!.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_message -> navController!!.navigate(R.id.messageFragment)
                R.id.navigation_nearby -> navController!!.navigate(R.id.nearbyFragment)
                R.id.navigation_addressbook -> navController!!.navigate(R.id.addressBookFragment)
                R.id.navigation_my -> navController!!.navigate(R.id.myFragment)
            }
            false
        }
    }

    override fun setStatusBar() {
        StatusBarUtil.setTransparent(this)
        StatusBarUtil.setLightMode(this)
    }
}