package fpt.adtrue.horoscope.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import fpt.adtrue.horoscope.R
import fpt.adtrue.horoscope.api.Utils
import fpt.adtrue.horoscope.api.Utils.capitalizeString
import fpt.adtrue.horoscope.application.App
import fpt.adtrue.horoscope.databinding.ActivityAstroProfileBinding

class ProfileAstroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAstroProfileBinding
    var runnable: Runnable? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_astro_profile)
        val s = intent.getStringExtra("horoscope")
        if (s != null) binding.textView6.text = s
        binding.tvSignNameAstro.text = App.getZodiac()[App.SIGN].name
        runnable = Runnable {
            binding.hpBarometre.animate()
                .setDuration(30000)
                .rotationBy(360F)
                .setInterpolator(LinearInterpolator())
                .withEndAction(runnable)
                .start()
        }
        runnable!!.run()

        binding.hpSign.setOnClickListener {
            val intent = Intent(applicationContext, ChoiceSignProfileAstroActivity::class.java)
            startActivities(arrayOf(intent))
            binding.hpSign.isEnabled = false
            val enableButton = Runnable { binding.hpSign.isEnabled = true }
            Handler().postDelayed(enableButton, 1000)
        }

        binding.hpSign.setImageResource(App.getZodiac()[App.SIGN].image3)
        App.getSign().forEach {
            if (App.getZodiac()[App.SIGN].name.equals(it.name, ignoreCase = true)) {
                binding.astroProfileTextSign.text = "${it.description[0]} \n\n ${it.description[1]}"
                binding.tvSignColor.text = capitalizeString(it.color)
                binding.tvSignElement.text =  capitalizeString(it.element)
                var s = ""
                it.planets.forEach {
                    s += capitalizeString(it )+ "\n"
                }


                binding.tvSignPlanet.text = s
                binding.tvSignSymbol.text =  capitalizeString(it.symbol)
                binding.tvSignPhrase.text =  capitalizeString(it.phrase)
                s = ""
                it.days.forEach {
                    s +=  capitalizeString(it) + "\n"
                }
                binding.tvSignLuckyDay.text = s
                binding.tvSignPolarity.text =  capitalizeString(it.polarity)
                binding.tvSignModality.text =  capitalizeString(it.modality)
                binding.tvSignDates.text = it.dates
            }
        }

        binding.astroProfileRedirChatButton.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", "walkinsvicky@gmail.com", null
                )
            )
            intent.putExtra(Intent.EXTRA_SUBJECT, "subject")
            intent.putExtra(Intent.EXTRA_TEXT, "Horoscope")
            startActivity(Intent.createChooser(intent, "Choose apps to connect with us :"))
            binding.astroProfileRedirChatButton.isEnabled = false
            val enableButton = Runnable { binding.astroProfileRedirChatButton.isEnabled = true }
            Handler().postDelayed(enableButton, 1000)
        }

        binding.astroProfileBack.setOnClickListener {
            onBackPressed()
        }


    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }


}