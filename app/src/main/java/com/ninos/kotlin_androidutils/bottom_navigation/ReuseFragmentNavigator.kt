package com.ninos.kotlin_androidutils.bottom_navigation

import android.content.Context
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator
import java.util.*

@Navigator.Name("reuseFragment")
class ReuseFragmentNavigator(val context: Context, val manager: FragmentManager,
                             val containerId: Int) : FragmentNavigator(context, manager, containerId) {
    override fun navigate(destination: Destination, args: Bundle?, navOptions: NavOptions?, navigatorExtras: Navigator.Extras?): NavDestination? {
        if (manager.isStateSaved) {
            return null
        }
        var className = destination.className
        if (className[0] == '.') {
            className = context.packageName + className
        }

        var frag = manager.findFragmentByTag(className)
        if (null == frag) {
            frag = instantiateFragment(context, manager, className, args)
        }

        frag.arguments = args
        val ft = manager.beginTransaction()

        var enterAnim = navOptions?.enterAnim ?: -1
        var exitAnim = navOptions?.exitAnim ?: -1
        var popEnterAnim = navOptions?.popEnterAnim ?: -1
        var popExitAnim = navOptions?.popExitAnim ?: -1
        if (enterAnim != -1 || exitAnim != -1 || popEnterAnim != -1 || popExitAnim != -1) {
            enterAnim = if (enterAnim != -1) enterAnim else 0
            exitAnim = if (exitAnim != -1) exitAnim else 0
            popEnterAnim = if (popEnterAnim != -1) popEnterAnim else 0
            popExitAnim = if (popExitAnim != -1) popExitAnim else 0
            ft.setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim)
        }

        val fragments = manager.fragments
        for (fragment in fragments) {
            ft.hide(fragment)
        }
        if (!frag.isAdded) {
            ft.add(containerId, frag, className)
        }
        ft.show(frag)
        ft.setPrimaryNavigationFragment(frag)

        @IdRes val destId = destination.id

        val mBackStack: ArrayDeque<Int>
        try {
            val field = FragmentNavigator::class.java.getDeclaredField("mBackStack")
            field.isAccessible = true
            mBackStack = field[this] as ArrayDeque<Int>
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

        val initialNavigation = mBackStack.isEmpty()
        val isSingleTopReplacement = (navOptions != null && !initialNavigation
                && navOptions.shouldLaunchSingleTop()
                && mBackStack.peekLast() == destId)

        val isAdded: Boolean
        isAdded = if (initialNavigation) {
            true
        } else if (isSingleTopReplacement) {
            if (mBackStack.size > 1) {
                manager.popBackStack(
                        "${mBackStack.size}-${mBackStack.peekLast()}",
                        FragmentManager.POP_BACK_STACK_INCLUSIVE)
                ft.addToBackStack("${mBackStack.size}-${destId}")
            }
            false
        } else {
            ft.addToBackStack("${mBackStack.size + 1}-${destId}")
            true
        }
        if (navigatorExtras is Extras) {
            for ((key, value) in navigatorExtras.sharedElements) {
                ft.addSharedElement(key, value)
            }
        }
        ft.setReorderingAllowed(true)
        ft.commit()
        return if (isAdded) {
            mBackStack.add(destId)
            destination
        } else {
            null
        }
    }
}