package fpt.adtrue.horoscope.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import fpt.adtrue.horoscope.activity.ChooseSignActivity
import fpt.adtrue.horoscope.activity.MainActivity
import fpt.adtrue.horoscope.adapter.SignPagerAdapter
import fpt.adtrue.horoscope.api.Utils
import fpt.adtrue.horoscope.application.App
import fpt.adtrue.horoscope.databinding.ActivityChoiceSignBinding

class ChooseSign : Fragment() {

    private lateinit var binding: ActivityChoiceSignBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ActivityChoiceSignBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager.adapter = SignPagerAdapter(childFragmentManager)
        binding.choiceSignNext.setOnClickListener {
            if (binding.viewPager.currentItem == 11) binding.viewPager.currentItem = 0
            else binding.viewPager.currentItem = binding.viewPager.currentItem + 1
        }

        binding.choiceSignPrev.setOnClickListener {
            if (binding.viewPager.currentItem == 0) binding.viewPager.currentItem = 11
            else binding.viewPager.currentItem = binding.viewPager.currentItem -1
        }

        binding.choiceSignValidate.setOnClickListener {
            App.SIGN = binding.viewPager.currentItem
            Utils.setDataLocal( App.SIGN,context!!.applicationContext)
            val intent = Intent(context, MainActivity::class.java)
           context?.startActivities(arrayOf(intent))
        }

        binding.choiceSignGoWims.setOnClickListener {
            val intent = Intent(context, ChooseSignActivity::class.java)
            context?.startActivities(arrayOf(intent))
            binding.choiceSignGoWims.isEnabled = false
            val enableButton = Runnable { binding.choiceSignGoWims.isEnabled = true }
            Handler().postDelayed(enableButton, 1000)
        }
    }

}