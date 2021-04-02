package fpt.adtrue.horoscope.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivities
import androidx.fragment.app.Fragment
import fpt.adtrue.horoscope.activity.ChooseSignActivity
import fpt.adtrue.horoscope.activity.MainActivity
import fpt.adtrue.horoscope.activity.WhatIsMySignActivity
import fpt.adtrue.horoscope.adapter.SignAdapter
import fpt.adtrue.horoscope.adapter.SignPagerAdapter
import fpt.adtrue.horoscope.api.Utils
import fpt.adtrue.horoscope.api.Utils.setDataLocal
import fpt.adtrue.horoscope.application.App
import fpt.adtrue.horoscope.databinding.ActivitySelectSignBinding

class ChooseSign : Fragment() {

    private lateinit var binding: ActivitySelectSignBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ActivitySelectSignBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.gridSign.adapter = SignAdapter(context = context!!)
        binding.gridSign.setOnItemClickListener { _, _, _, id ->
            App.SIGN = id.toInt()
            setDataLocal(App.SIGN, context!!)
            val intent = Intent(context, MainActivity::class.java)
            context!!.startActivities(arrayOf(intent))
        }
//        choice_sign_go_wims
        binding.choiceSignGoWims.setOnClickListener {
            Utils.setDataLocal(App.SIGN, context!!)
            val intent = Intent(context, WhatIsMySignActivity::class.java)
            context!!.startActivities(arrayOf(intent))
        }
    }

}