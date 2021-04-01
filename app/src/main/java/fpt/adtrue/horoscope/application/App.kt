package fpt.adtrue.horoscope.application

import android.app.Application
import fpt.adtrue.horoscope.model.DataCompatibilityItem
import fpt.adtrue.horoscope.model.DataSignItem
import fpt.adtrue.horoscope.model.dataZodiac
import fpt.adtrue.horoscope.viewmodel.HoroscopeViewModel

class App:Application() {

    companion object{
        private  val horoscopeViewModel = HoroscopeViewModel()
        fun getViewModel() = horoscopeViewModel
        private val zodiac = mutableListOf<dataZodiac>()
        fun getZodiac() = zodiac
        private val dataCom = mutableListOf<DataCompatibilityItem>()
        fun getCom() = dataCom
        private val dataSign = mutableListOf<DataSignItem>()
        fun getSign() = dataSign
        var SIGN = 100
        var HER = 100

    }

}