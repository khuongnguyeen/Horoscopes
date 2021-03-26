package fpt.adtrue.horoscope.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.databinding.ObservableBoolean
import com.google.gson.Gson
import fpt.adtrue.horoscope.api.HoroscopeApi
import fpt.adtrue.horoscope.api.RetrofitUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HoroscopeViewModel {
    private val horoscopeApi: HoroscopeApi = RetrofitUtils.createRetrofit()
    val isLoading= ObservableBoolean(false)
    @SuppressLint("CheckResult")
    fun getHoroscope(sign:String,day:String) {
        isLoading.set(true)
        horoscopeApi.getHoroscope(sign, day)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Log.e("HoroscopeViewModel", Gson().toJson(it))
                    isLoading.set(false)
                },
                {
                    Log.e("HoroscopeViewModel","Errrrrrrrrrrrrrrrrrrrrrrrrrr")
                    isLoading.set(false)
                })
    }
}