package fpt.adtrue.horoscope.api

import fpt.adtrue.horoscope.model.DataAmazonaws
import fpt.adtrue.horoscope.model.DataHoroscope
import fpt.adtrue.horoscope.model.RequestHoroscope
import io.reactivex.Observable
import retrofit2.http.*

interface HoroscopeApi {
    @POST("/")
    fun getHoroscope(
        @Query("sign") sign:String,
        @Query("day") day:String
    ): Observable<DataHoroscope>

    @GET("/idz-horoscopes/{year}/{mon}/{day}-{mon}-{year}.json")
    fun  getDataAmazon(@Path("day") day:String ,
                       @Path("mon") mon:String,
                       @Path("year") year:Int

    ): Observable<DataAmazonaws>


}
