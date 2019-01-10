package us.wmwm.stroll.sample

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import us.wmwm.stroll.onBackExtension
import us.wmwm.stroll.showFragment

class MainActivity : FragmentActivity() {

    var fragment:MainFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onBackPressed() {
        when(onBackExtension { fragment->
            fragment == this.fragment
        }) {
            false-> super.onBackPressed()
        }
    }

}