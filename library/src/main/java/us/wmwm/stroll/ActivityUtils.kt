package us.wmwm.stroll

import android.support.transition.*
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import us.wmwm.stroll.back.BackFragment

fun FragmentActivity.showFragment(fragment: Fragment) {
    val view = findViewById(R.id.activityContainer) as? ViewGroup ?: return
    var vg = view.findViewById<ViewGroup>(R.id.activityFragmentContainer)
    vg = when (vg) {
        null -> {
            val fl = FrameLayout(view.context).apply {
            }
            view.addView(fl, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
            fl
        }
        else -> vg
    }
    val set = TransitionSet()
    set.duration = resources.getInteger(R.integer.stroll_animation_duration).toLong()
    val slide = Slide(Gravity.END);
    set.addTransition(slide);
    TransitionManager.endTransitions(vg)
    TransitionManager.beginDelayedTransition(vg, set)
    vg.visibility = View.VISIBLE
    supportFragmentManager.beginTransaction()
            .replace(R.id.activityFragmentContainer, fragment).commit()
}

fun FragmentActivity.onBackExtension(predicate: (Fragment) -> Boolean = { true }): Boolean {
    val fragment = supportFragmentManager.findFragmentById(R.id.activityFragmentContainer)
            ?: return false
    val sufficesPredicate = predicate(fragment)
    if(sufficesPredicate) {
        return false
    }
    when (fragment) {
        is BackFragment -> {
            when (fragment.onBack()) {
                true -> return true
            }
        }
    }

    val listener = object : TransitionListenerAdapter() {
        override fun onTransitionEnd(transition: Transition) {
            fragment.let {
                supportFragmentManager
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
    val view = findViewById(R.id.activityContainer) as? ViewGroup ?: return false
    val fl = view.findViewById<ViewGroup>(R.id.activityFragmentContainer)
    set.addListener(listener)
    TransitionManager.beginDelayedTransition(fl, set)
    fl.visibility = View.INVISIBLE
    return true
}