package fpt.adtrue.horoscope.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import fpt.adtrue.horoscope.R
import fpt.adtrue.horoscope.api.Utils
import fpt.adtrue.horoscope.api.Utils.checkDate
import fpt.adtrue.horoscope.application.App
import fpt.adtrue.horoscope.databinding.ActivityWhatIsMySignBinding
import java.util.*


class WhatIsMySignActivity : AppCompatActivity(){

    private lateinit var binding: ActivityWhatIsMySignBinding

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_what_is_my_sign)

        binding.wimsValidate.setOnClickListener {
            val day = binding.dp.day
            val mon = binding.dp.month
            App.SIGN = checkDate(mon, day)
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivities(arrayOf(intent))
        }

        Utils.sttBar(this)

        binding.wimsBack.setOnClickListener {
            onBackPressed()
        }
    }
}