package fpt.adtrue.horoscope.tarot2

import android.content.Context

object ScreenUtil {
    @JvmStatic
    fun dip2px(context: Context, dip: Float): Int {
        val density = context.resources.displayMetrics.density
        return (dip * density + 0.5f * (if (dip >= 0.0f) 1 else -1).toFloat()).toInt()
    }

    @JvmStatic
    fun getScreenWidth(context: Context): Int {
        val dm = context.resources.displayMetrics
        return dm.widthPixels
    }
}