package fpt.adtrue.horoscope.api

import fpt.adtrue.horoscope.model.DataHoroscope
import fpt.adtrue.horoscope.model.RequestHoroscope
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

open interface HoroscopeApi {
    @POST("/")
    fun getHoroscope(
        @Query("sign") sign:String,
        @Query("day") day:String
    ): Observable<DataHoroscope>
}