package com.ninos.kotlin_androidutils.bottom_navigation

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphNavigator
import androidx.navigation.NavigatorProvider
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ninos.kotlin_androidutils.R

class NavigationActivity : AppCompatActivity() {
    lateinit var navView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
        navView = findViewById(R.id.nav_view)
        val fragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        val navController = NavHostFragment.findNavController(fragment!!)
        val fragmentNavigator =
            ReuseFragmentNavigator(this, fragment.childFragmentManager, fragment.id)
        val provider = navController.navigatorProvider
        provider.addNavigator(fragmentNavigator)
        val navGraph: NavGraph = initNavGraph(provider, fragmentNavigator)
        navController.graph = navGraph
        navView.setOnNavigationItemSelectedListener { item: MenuItem ->
            navController.navigate(item.itemId)
            true
        }
    }

    private fun initNavGraph(
        provider: NavigatorProvider,
        fragmentNavigator: ReuseFragmentNavigator
    ): NavGraph {
        val navGraph = NavGraph(NavGraphNavigator(provider))

        //用自定义的导航器来创建目的地
        val destination1 = fragmentNavigator.createDestination()
        destination1.id = R.id.navigation_home
        destination1.className = NavigationFragment::class.java.canonicalName
        destination1.label = resources.getString(R.string.app_name)
        navGraph.addDestination(destination1)
        val destination2 = fragmentNavigator.createDestination()
        destination2.id = R.id.navigation_self
        destination2.className = NavigationFragment::class.java.canonicalName
        destination2.label = resources.getString(R.string.app_name)
        navGraph.addDestination(destination2)
        navGraph.startDestination = R.id.navigation_home
        return navGraph
    }
}