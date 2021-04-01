package fpt.adtrue.horoscope.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import fpt.adtrue.horoscope.fragment.AdFragment
import fpt.adtrue.horoscope.fragment.FragmentHomePager

class HomePagerAdapter(fm: FragmentManager,val position: Int) : FragmentStatePagerAdapter(fm) {


    override fun getCount() = 5

    override fun getItem(position: Int): Fragment {
        when (position) {

            0 -> {
                return FragmentHomePager(position)
            }
            2 -> {
                return FragmentHomePager(position)
            }
            4 -> {
                return FragmentHomePager(position)
            }
            else -> return AdFragment()
        }
    }


    override fun getPageTitle(position: Int): CharSequence {
        when (position) {
            0 -> return "Yesterday"
            2 -> return "Today"
            4 -> return "Tomorrow"
            else -> return "Ad"
        }
    }
}