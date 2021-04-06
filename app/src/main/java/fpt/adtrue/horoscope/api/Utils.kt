package fpt.adtrue.horoscope.api

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.util.Log
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import fpt.adtrue.horoscope.application.App
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit


object Utils {
    @JvmStatic
    fun createRetrofit(): HoroscopeApi {
        val http = OkHttpClient.Builder()
            .callTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .addInterceptor(
                LoggingInterceptor.Builder()
                    .setLevel(Level.BASIC)
                    .log(Log.VERBOSE)
                    .addHeader("cityCode", "53")
                    .addQueryParam("moonStatus", "crescent")
                    .build()
            )
            .build()
        return Retrofit.Builder()
            .baseUrl("https://aztro.sameerkumar.website")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(http)
            .build()
            .create(HoroscopeApi::class.java)
    }

    fun setDataLocal(sign: Int, context: Context) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(
                "sign",
                Context.MODE_PRIVATE
            )
        val editor = sharedPreferences.edit()
        editor.putInt("sign", sign)
        editor.apply()
    }

    fun capitalizeString(string: String): String {
        val chars = string.toLowerCase(Locale.ROOT).toCharArray()
        var found = false
        for (i in chars.indices) {
            if (!found && Character.isLetter(chars[i])) {
                chars[i] = Character.toUpperCase(chars[i])
                found = true
            } else if (Character.isWhitespace(chars[i]) || chars[i] == '.' || chars[i] == '\'') {
                found = false
            }
        }
        return String(chars)
    }


    fun checkDate(mon: Int, day: Int): Int {
        when (mon) {
            1 -> {
                return if (day < 20) {
                    9
                } else {
                    10
                }
            }
            2 -> {
                return if (day < 18) {
                    10
                } else {
                    11
                }
            }
            3 -> {
                return if (day < 21) {
                    11
                } else {
                    0
                }
            }
            4 -> {
                return if (day < 20) {
                    0
                } else {
                    1
                }
            }
            5 -> {
                return if (day < 21) {
                    1
                } else {
                    2
                }
            }
            6 -> {
                return if (day < 21) {
                    2
                } else {
                    3
                }
            }
            7 -> {
                return if (day < 23) {
                    3
                } else {
                    4
                }
            }
            8 -> {
                return if (day < 23) {
                    4
                } else {
                    5
                }
            }
            9 -> {
                return if (day < 23) {
                    5
                } else {
                    6
                }
            }
            10 -> {
                return if (day < 23) {
                    6
                } else {
                    7
                }
            }
            11 -> {
                return if (day < 22) {
                    7
                } else {
                    8
                }
            }
            else -> {
                return if (day < 22) {
                    8
                } else {
                    9
                }
            }
        }
    }

}