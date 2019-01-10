package us.wmwm.stroll

import android.support.transition.*
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateInterpolator
import us.wmwm.stroll.back.BackFragment

fun Fragment.showFragmentExtension(id:Int, fragment: Fragment) {
    val set = TransitionSet()
    set.duration = resources.getInteger(R.integer.stroll_animation_duration).toLong()
    val slide = Slide(Gravity.END);
    set.interpolator = AnticipateInterpolator()
    set.addTransition(slide);
    val view = view as? ViewGroup ?: return
    val fl = view.findViewById<ViewGroup>(id)
    val fragmentExisting = childFragmentManager.findFragmentById(id)
    if(fragmentExisting!=null) {
        return replaceFragmentExtension(id, fragment)
    }
    TransitionManager.endTransitions(fl)
    TransitionManager.beginDelayedTransition(fl, set)
    fl.bringToFront()
    fl.visibility = View.VISIBLE
    childFragmentManager.beginTransaction()
            .replace(id, fragment).commit()
}

fun findDeepestFragment(childFragmentManager: FragmentManager): Fragment? {
    var fragment = childFragmentManager.findFragmentById(R.id.fragmentContainer)
    while(fragment!=null && fragment.isAdded) {
        val frag = fragment.childFragmentManager.findFragmentById(R.id.fragmentContainer) ?: break
        fragment = frag
    }
    return fragment
}

fun Fragment.onBackExtension(): Boolean {
    val fragment = findDeepestFragment(childFragmentManager) ?: return false
    val parent = fragment.parentFragment ?: return false

    val fragmentAsBack = fragment as? BackFragment
    when(fragmentAsBack!=null) {
        true->when(fragmentAsBack.onBack()) {
            true-> return true
        }
    }

    val thisFragment = this
    if(thisFragment==fragment) {
        return false
    }

    val listener = object : TransitionListenerAdapter() {

        override fun onTransitionEnd(transition: Transition) {
            val frag = parent.childFragmentManager.findFragmentById(R.id.fragmentContainer)
            frag?.let { it->
                childFragmentManager
                        .beginTransaction()
                        .remove(it)
                        .commit()
            }
        }

    }
    val set = TransitionSet()
    set.duration = resources.getInteger(R.integer.stroll_animation_duration).toLong()
    val slide = Slide(Gravity.END);
    set.addTransition(slide);
    val view = parent.view as? ViewGroup ?: return false
    val fl = view.findViewById<ViewGroup>(R.id.fragmentContainer)
    set.addListener(listener)
    TransitionManager.endTransitions(fl)
    TransitionManager.beginDelayedTransition(fl, set)
    fl.visibility = View.INVISIBLE
    return true
}

fun Fragment.removeFragmentExtension(id:Int) {
    childFragmentManager.findFragmentById(id)?:return
    val listener = object : TransitionListenerAdapter() {

        override fun onTransitionEnd(transition: Transition) {
            val frag = childFragmentManager.findFragmentById(id)
            frag?.let {
                childFragmentManager
                        .beginTransaction()
                        .remove(it)
                        .commit()
            }
        }
    }
    val set = TransitionSet()
    set.duration = resources.getInteger(R.integer.stroll_animation_duration).toLong()
    val slide = Slide(Gravity.START);
    set.addTransition(slide);
    val view = view as? ViewGroup ?: return
    val fl = view.findViewById<ViewGroup>(id)
    set.addListener(listener)
    TransitionManager.endTransitions(fl)
    TransitionManager.beginDelayedTransition(fl, set)
    fl.visibility = View.INVISIBLE
}

fun Fragment.replaceFragmentExtension(fragment: Fragment) {
    replaceFragmentExtension(R.id.fragmentContainer, fragment)
}

fun Fragment.replaceFragmentExtension(id:Int, fragment: Fragment) {
    childFragmentManager
            .beginTransaction()
            .replace(id, fragment).commit()
}
