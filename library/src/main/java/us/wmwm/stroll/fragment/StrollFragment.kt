package us.wmwm.stroll.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import us.wmwm.stroll.*
import us.wmwm.stroll.back.BackFragment

open class StrollFragment : Fragment(), BackFragment {

    lateinit var fragmentContainer: FrameLayout

    override fun onBack(): Boolean {
        return onBackExtension()
    }

    open fun showFragment(fragment: Fragment) {
        showFragmentExtension(R.id.fragmentContainer, fragment)
    }

    fun removeFragment(id:Int) {
        removeFragmentExtension(id)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val vg = view as? ViewGroup ?: return
        val none = (0 until vg.childCount).none {
            val vv = vg.getChildAt(it)
            vv.id == R.id.fragmentContainer
        }
        val context = context ?: return
        when (none) {
            true -> {
                fragmentContainer = FrameLayout(context).apply {
                    id = R.id.fragmentContainer
                    elevation = vg.highestElevation
                }
                vg.addView(fragmentContainer, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            }
            false -> {
                fragmentContainer = vg.findViewById(R.id.fragmentContainer)
            }
        }
        fragmentContainer.visibility = when(childFragmentManager.findFragmentById(R.id.fragmentContainer)) {
            null->View.INVISIBLE
            else->View.VISIBLE
        }
    }
}