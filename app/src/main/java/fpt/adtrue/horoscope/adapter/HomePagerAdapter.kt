package fpt.adtrue.horoscope.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import fpt.adtrue.horoscope.fragment.AdFragment
import fpt.adtrue.horoscope.fragment.FragmentHomePager

class HomePagerAdapter(fm: FragmentManager): FragmentStatePagerAdapter(fm) {


    override fun getCount() = 11

    override fun getItem(position: Int): Fragment {
        when(position){
//            1,3,5,7,9 ->{
//
//            }
            0,2,4,6,8,10->{
                return FragmentHomePager()
            }
            else -> return AdFragment()
        }
    }



    override fun getPageTitle(position: Int): CharSequence {
        when(position){
            0-> return "Yesterday"
            2-> return "Today"
            4-> return "Tomorrow"
            6-> return "Week"
            8-> return "Month"
            10-> return "Year"
            else -> return "Ad"
        }
    }
}