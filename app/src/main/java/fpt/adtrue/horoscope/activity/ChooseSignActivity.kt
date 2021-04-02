package fpt.adtrue.horoscope.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import fpt.adtrue.horoscope.R
import fpt.adtrue.horoscope.adapter.SignAdapter
import fpt.adtrue.horoscope.adapter.SignPagerAdapter
import fpt.adtrue.horoscope.api.Utils
import fpt.adtrue.horoscope.api.Utils.setDataLocal
import fpt.adtrue.horoscope.application.App
import fpt.adtrue.horoscope.databinding.ActivitySelectSignBinding

class ChooseSignActivity :AppCompatActivity(){

    private lateinit var binding: ActivitySelectSignBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_sign)
        binding.gridSign.adapter = SignAdapter(context = applicationContext)
        binding.gridSign.setOnItemClickListener { _, _, _, id ->
            App.SIGN = id.toInt()
            setDataLocal( App.SIGN,applicationContext)
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivities(arrayOf(intent))
        }
//        choice_sign_go_wims
        binding.choiceSignGoWims.setOnClickListener {
            setDataLocal( App.SIGN,applicationContext)
            val intent = Intent(applicationContext, WhatIsMySignActivity::class.java)
            startActivities(arrayOf(intent))
        }

        binding.choiceSignBack.setOnClickListener {
            onBackPressed()
        }
    }

}