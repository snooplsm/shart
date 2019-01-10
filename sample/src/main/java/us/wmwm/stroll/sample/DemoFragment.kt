package us.wmwm.stroll.sample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_demo.*
import us.wmwm.stroll.fragment.StrollFragment

const val DATA = "data"

fun newInstance(data: DataItem) = StrollFragment().apply { arguments = Bundle().apply { putParcelable(DATA, data) } }

class DemoFragment : StrollFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_demo, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val data = arguments?.getParcelable<DataItem>(DATA) ?: return

        toolbar.title = data.string

        when (data.showBackButton) {
            true -> {
                toolbar.apply {
                    setNavigationIcon(R.drawable.baseline_arrow_back_24px)
                    setOnClickListener { activity?.onBackPressed() }
                }
            }
        }
    }
}