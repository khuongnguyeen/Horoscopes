package fpt.adtrue.horoscope.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import fpt.adtrue.horoscope.R
import fpt.adtrue.horoscope.application.App
import fpt.adtrue.horoscope.databinding.ActivityAstroProfileBinding
import java.util.*

class ProfileAstroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAstroProfileBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_astro_profile)
        binding.hpSign.setImageResource(App.getZodiac()[App.SIGN].image2)
        App.getSign().forEach {
            if (App.getZodiac()[App.SIGN].name.toLowerCase(Locale.ROOT) == it.name.toLowerCase(
                    Locale.ROOT
                )
            ){
                binding.astroProfileTextSign.text = "${it.description[0]} \n\n ${it.description[1]}"
            }
        }


    }




}