package us.wmwm.stroll.sample

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class DemoAdapter(fm:FragmentManager) : FragmentPagerAdapter(fm) {

    val data = ("abc").mapIndexed { index, item-> DataItem(showBackButton = index==0, string = item.toString()) }

    override fun getItem(index: Int): Fragment {
        return newInstance(data[index])
    }

    override fun getCount(): Int {
        return data.size
    }

}