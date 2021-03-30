package fpt.adtrue.horoscope.application

import android.app.Application
import fpt.adtrue.horoscope.model.dataZodiac
import fpt.adtrue.horoscope.viewmodel.HoroscopeViewModel

class App:Application() {

    companion object{
        private  val horoscopeViewModel = HoroscopeViewModel()
        fun getViewModel() = horoscopeViewModel
        private val zodiac = mutableListOf<dataZodiac>()
        fun getZodiac() = zodiac
        var SIGN = 0
    }

}