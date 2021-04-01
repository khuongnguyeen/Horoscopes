package fpt.adtrue.horoscope.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import fpt.adtrue.horoscope.fragment.SignFragment

class SignPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {


    override fun getCount() = 12

    override fun getItem(position: Int): Fragment {
                return SignFragment(position)

    }



}