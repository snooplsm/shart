package us.wmwm.stroll

import android.view.View
import android.view.ViewGroup

val View.highestElevation: Float
    get() {
        val vg = this as? ViewGroup ?: return this.elevation
        val max = (0 until vg.childCount).maxBy {
            val view = vg.getChildAt(it)
            view.elevation
        } ?: return this.elevation
        val view = vg.getChildAt(max)
        return view.elevation
    }
