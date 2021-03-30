package fpt.adtrue.horoscope.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import fpt.adtrue.horoscope.databinding.HomeLayoutBinding

class HomeFragment:Fragment() {

    private lateinit var binding : HomeLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return binding.root
    }



}