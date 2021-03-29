package fpt.adtrue.horoscope

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import fpt.adtrue.horoscope.application.App
import fpt.adtrue.horoscope.model.RequestHoroscope
import fpt.adtrue.horoscope.databinding.ActivityMainBinding
import fpt.adtrue.horoscope.viewmodel.HoroscopeViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)



    }
}