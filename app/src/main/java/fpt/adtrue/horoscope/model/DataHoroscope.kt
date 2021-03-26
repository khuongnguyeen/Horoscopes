package fpt.adtrue.horoscope.model

import com.google.gson.annotations.SerializedName

data class DataHoroscope (
    val color: String = "",
    val compatibility: String = "",
    @SerializedName("current_date")
    val currentDate: String = "",
    @SerializedName("date_range")
    val dateRange: String = "",
    val description: String = "",
    @SerializedName("lucky_number")
    val luckyNumber: String = "",
    @SerializedName("lucky_time")
    val luckyTime: String = "",
    val mood: String = ""
)