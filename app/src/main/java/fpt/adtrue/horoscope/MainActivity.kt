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

        binding.data = App.getViewModel()
        binding.buttonOk.setOnClickListener {
            val test = RequestHoroscope()
            test.sign = "leo"
            test.day = "today"
            App.getViewModel().getHoroscope(test.sign,test.day)
        }
        binding.buttonYes.setOnClickListener {
            val test = RequestHoroscope()
            test.sign = "leo"
            test.day = "yesterday"
            App.getViewModel().getHoroscope(test.sign,test.day)
        }
        binding.buttonTomo.setOnClickListener {
            val test = RequestHoroscope()
            test.sign = "leo"
            test.day = "tomorrow"
            App.getViewModel().getHoroscope(test.sign,test.day)
        }

    }
}