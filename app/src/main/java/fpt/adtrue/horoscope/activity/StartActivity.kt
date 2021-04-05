package fpt.adtrue.horoscope.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.allyants.notifyme.NotifyMe
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import fpt.adtrue.horoscope.R
import fpt.adtrue.horoscope.application.App
import fpt.adtrue.horoscope.databinding.ActivityStartBinding
import fpt.adtrue.horoscope.fragment.ChooseSign
import fpt.adtrue.horoscope.model.DataCompatibilityItem
import fpt.adtrue.horoscope.model.DataSignItem
import fpt.adtrue.horoscope.model.dataZodiac
import fpt.adtrue.horoscope.tarot2.TarotAnimSwitchActivity
import java.io.*
import java.lang.reflect.Type
import java.util.*


class StartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_start)
//        getSign()
//        getCompatibility()
//        getDataLocal()
//        getDaSi()
//        val enableButton = Runnable {
//            if (App.SIGN == 100) {
//
//                binding.logo.visibility = View.GONE
//                val fr1 = ChooseSign()
//                val active: Fragment = fr1
//                val manager = supportFragmentManager
//                manager.beginTransaction().hide(active).show(fr1).commit()
////            active = fr1
//                manager.beginTransaction().add(R.id.content, fr1, "1").commit()
//            } else {
//                val intent = Intent(applicationContext, MainActivity::class.java)
//                startActivities(arrayOf(intent))
//
//            }
//        }
//        Handler().postDelayed(enableButton, 2000)

        TarotAnimSwitchActivity.start(this)


//        val notifyMe = NotifyMe.Builder(applicationContext)
//        notifyMe.title("Horoscope")
//        notifyMe.content(String content)
//        notifyMe.color(Int red,Int green,Int blue,Int alpha)
//        notifyMe.led_color(Int red,Int green,Int blue,Int alpha)
//        notification pops up
//        notifyMe.time(Calendar time)
//        notifyMe.delay(Int delay)
//        notifyMe.large_icon(Int resource)
//        notifyMe.rrule("FREQ=MINUTELY;INTERVAL=5;COUNT=2")
//        notifyMe.addAction(Intent intent,String text)
    }

    private fun getCompatibility() {
        val iS: InputStream = resources.openRawResource(R.raw.zodiac_compatibility)
        val writer: Writer = StringWriter()
        val buffer = CharArray(1024)
        iS.use { iS ->
            val reader: Reader = BufferedReader(InputStreamReader(iS, "UTF-8"))
            var n: Int
            while (reader.read(buffer).also { n = it } != -1) {
                writer.write(buffer, 0, n)
            }
        }
        val jsonString: String = writer.toString()
        if (jsonString != "") {
            val gSon = Gson()
            val type: Type = object : TypeToken<MutableList<DataCompatibilityItem?>?>() {}.type
            App.getCom().clear()
            App.getCom().addAll(gSon.fromJson<MutableList<DataCompatibilityItem>>(jsonString, type))
        }
        Log.e("Start Activity", "Thanhf coong compability")
    }

    private fun getSign() {
        val iS: InputStream = resources.openRawResource(R.raw.zodiac_signs)
        val writer: Writer = StringWriter()
        val buffer = CharArray(1024)
        iS.use { iS ->
            val reader: Reader = BufferedReader(InputStreamReader(iS, "UTF-8"))
            var n: Int
            while (reader.read(buffer).also { n = it } != -1) {
                writer.write(buffer, 0, n)
            }
        }
        val jsonString: String = writer.toString()
        if (jsonString != "") {
            val gSon = Gson()
            val type: Type = object : TypeToken<MutableList<DataSignItem?>?>() {}.type
            App.getSign().clear()
            App.getSign().addAll(gSon.fromJson<MutableList<DataSignItem>>(jsonString, type))
        }
        Log.e("Start Activity", "tc sign")
    }

    private fun getDaSi() {
        val v1 = dataZodiac("Aries", R.drawable.s1_wh, R.drawable.s1_hp, R.drawable.s1_or)
        val v2 = dataZodiac("Taurus", R.drawable.s2_wh, R.drawable.s2_hp, R.drawable.s2_or)
        val v3 = dataZodiac("Gemini", R.drawable.s3_wh, R.drawable.s3_hp, R.drawable.s3_or)
        val v4 = dataZodiac("Cancer", R.drawable.s4_wh, R.drawable.s4_hp, R.drawable.s4_or)
        val v5 = dataZodiac("Leo", R.drawable.s5_wh, R.drawable.s5_hp, R.drawable.s5_or)
        val v6 = dataZodiac("Virgo", R.drawable.s6_wh, R.drawable.s6_hp, R.drawable.s6_or)
        val v7 = dataZodiac("Libra", R.drawable.s7_wh, R.drawable.s7_hp, R.drawable.s7_or)
        val v8 = dataZodiac("Scorpio", R.drawable.s8_wh, R.drawable.s8_hp, R.drawable.s8_or)
        val v9 = dataZodiac("Sagittarius", R.drawable.s9_wh, R.drawable.s9_hp, R.drawable.s9_or)
        val v10 = dataZodiac("Capricorn", R.drawable.s10_wh, R.drawable.s10_hp, R.drawable.s10_or)
        val v11 = dataZodiac("Aquarius", R.drawable.s11_wh, R.drawable.s11_hp, R.drawable.s11_or)
        val v12 = dataZodiac("Pisces", R.drawable.s12_wh, R.drawable.s12_hp, R.drawable.s12_or)
        App.getZodiac().add(v1)
        App.getZodiac().add(v2)
        App.getZodiac().add(v3)
        App.getZodiac().add(v4)
        App.getZodiac().add(v5)
        App.getZodiac().add(v6)
        App.getZodiac().add(v7)
        App.getZodiac().add(v8)
        App.getZodiac().add(v9)
        App.getZodiac().add(v10)
        App.getZodiac().add(v11)
        App.getZodiac().add(v12)
    }

    private fun getDataLocal() {
        val sharedPreferences: SharedPreferences = applicationContext.getSharedPreferences("sign",
            Context.MODE_PRIVATE)
        val string = sharedPreferences.getInt("sign", 100)
        App.SIGN = string
    }

    override fun onBackPressed() {
    }

}