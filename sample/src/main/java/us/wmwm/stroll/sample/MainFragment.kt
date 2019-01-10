package us.wmwm.stroll.sample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_main.*
import us.wmwm.stroll.fragment.StrollFragment

class MainFragment : StrollFragment() {

    var count:Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        count = arguments?.getInt("count") ?: 0
        backButton.visibility = when (parentFragment) {
            null -> View.INVISIBLE
            else -> View.VISIBLE
        }
        goDeeper.text = when (count) {
            0 -> resources.getString(R.string.go_deeper)
            else -> resources.getString(R.string.go_deeper_numeric, count)
        }
        backButton.setOnClickListener {
            activity?.onBackPressed()
        }
        goDeeper.setOnClickListener {
            showFragment(newInstance(count + 1))
        }
    }
}

fun newInstance(count: Int = 0): MainFragment {
    return MainFragment().apply {
        arguments = Bundle().apply {
            putInt("count", count)
        }
    }
}