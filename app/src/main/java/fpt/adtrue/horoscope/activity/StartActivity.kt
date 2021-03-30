package fpt.adtrue.horoscope.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import fpt.adtrue.horoscope.R
import fpt.adtrue.horoscope.application.App
import fpt.adtrue.horoscope.databinding.ActivityStartBinding
import fpt.adtrue.horoscope.fragment.ChooseSign
import fpt.adtrue.horoscope.model.dataZodiac

class StartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_start)
        getDaSi()
        val enableButton = Runnable {
            if (App.SIGN == 0) {

                binding.logo.visibility = View.GONE
                val fr1 = ChooseSign()
                val active: Fragment = fr1
                val manager = supportFragmentManager
                manager.beginTransaction().hide(active).show(fr1).commit()
//            active = fr1
                manager.beginTransaction().add(R.id.content, fr1, "1").commit()
            } else {
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivities(arrayOf(intent))
            }
        }
        Handler().postDelayed(enableButton, 2000)

    }

    private fun getDaSi() {
        val v1 = dataZodiac("Aries", R.drawable.s1_wh)
        val v2 = dataZodiac("Aries", R.drawable.s2_wh)
        val v3 = dataZodiac("Aries", R.drawable.s3_wh)
        val v4 = dataZodiac("Aries", R.drawable.s4_wh)
        val v5 = dataZodiac("Aries", R.drawable.s5_wh)
        val v6 = dataZodiac("Aries", R.drawable.s6_wh)
        val v7 = dataZodiac("Aries", R.drawable.s7_wh)
        val v8 = dataZodiac("Aries", R.drawable.s8_wh)
        val v9 = dataZodiac("Aries", R.drawable.s9_wh)
        val v10 = dataZodiac("Aries", R.drawable.s10_wh)
        val v11 = dataZodiac("Aries", R.drawable.s11_wh)
        val v12 = dataZodiac("Aries", R.drawable.s12_wh)
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
}