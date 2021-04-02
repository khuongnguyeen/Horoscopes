package fpt.adtrue.horoscope.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import fpt.adtrue.horoscope.api.HoroscopeApi
import fpt.adtrue.horoscope.api.Utils
import fpt.adtrue.horoscope.model.DataHoroscope
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HoroscopeViewModel {
    private val horoscopeApi: HoroscopeApi = Utils.createRetrofit()
    val data = MutableLiveData<DataHoroscope>()
    val data1 = MutableLiveData<DataHoroscope>()
    val data2 = MutableLiveData<DataHoroscope>()
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
                    if (day == "today"){
                        data.value = it
                    }
                    if (day == "tomorrow"){
                        data1.value = it
                    }
                    if (day == "yesterday"){
                        data2.value = it
                    }
                    isLoading.set(false)
                },
                {
                    Log.e("HoroscopeViewModel","Errrrrrrrrrrrrrrrrrrrrrrrrrr")
                    isLoading.set(false)
                })
    }
}