package fpt.adtrue.horoscope.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import fpt.adtrue.horoscope.fragment.AdFragment
import fpt.adtrue.horoscope.fragment.FragmentHomePager

class HomePagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {


    override fun getCount() = 3

    override fun getItem(position: Int): Fragment {
        when (position) {

            0 -> {
                return FragmentHomePager(0)
            }
            1 -> {
                return FragmentHomePager(1)
            }
            2 -> {
                return FragmentHomePager(2)
            }
            else -> return AdFragment()
        }
    }


    override fun getPageTitle(position: Int): CharSequence {
        when (position) {
            0 -> return "Yesterday"
            1 -> return "Today"
            else -> return "Tomorrow"
        }
    }
}