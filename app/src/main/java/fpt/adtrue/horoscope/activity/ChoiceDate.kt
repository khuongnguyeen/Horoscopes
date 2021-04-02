package fpt.adtrue.horoscope.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import fpt.adtrue.horoscope.R
import fpt.adtrue.horoscope.api.Utils
import fpt.adtrue.horoscope.application.App
import fpt.adtrue.horoscope.databinding.ActivityWhatIsMySignBinding

class ChoiceDate: AppCompatActivity(){

    private lateinit var binding: ActivityWhatIsMySignBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_what_is_my_sign)

        binding.wimsValidate.setOnClickListener {
            val day = binding.dp.dayOfMonth
            val mon = binding.dp.month + 1
            App.SIGN = Utils.checkDate(mon, day)
            val intent = Intent(applicationContext, ProfileAstroActivity::class.java)
            startActivities(arrayOf(intent))
        }
        binding.wimsBack.setOnClickListener {
            onBackPressed()
        }
    }
}