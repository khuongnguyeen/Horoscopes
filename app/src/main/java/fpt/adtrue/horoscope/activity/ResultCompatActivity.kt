package fpt.adtrue.horoscope.activity

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import fpt.adtrue.horoscope.R
import fpt.adtrue.horoscope.api.Utils
import fpt.adtrue.horoscope.application.App
import fpt.adtrue.horoscope.databinding.ActivityCompatResultsBinding
import java.util.*
import kotlin.system.exitProcess

class ResultCompatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCompatResultsBinding
    private var mCountDownTimer: CountDownTimer? = null
    private var runnable: Runnable? = null

    @SuppressLint("SetTextI18n", "NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_compat_results)

        Utils.sttBar(this)
         runnable = Runnable {
            binding.compatBaro.animate()
                .setDuration(30000)
                .rotationBy(360F)
                .setInterpolator(LinearInterpolator())
                .withEndAction(runnable)
                .start()
        }
        runnable!!.run()

        mCountDownTimer = object : CountDownTimer(7000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                binding.rlBackground.visibility = View.GONE
            }
        }.start()
        binding.compatResultsSignLeftImg.setImageResource(App.getZodiac()[App.SIGN].image)
        binding.compatResultsSignLeftText.text = App.getZodiac()[App.SIGN].name
        binding.compatResultsSignRightImg.setImageResource(App.getZodiac()[App.HER].image)
        binding.compatResultsSignRightText.text = App.getZodiac()[App.HER].name

        binding.ringProgress.max = 100


        App.getCom().forEach {
            if ("${App.getZodiac()[App.SIGN].name}:${App.getZodiac()[App.HER].name}".toLowerCase(
                    Locale.ROOT
                ).equals(it.key, ignoreCase = false)
            ) {
                binding.compatResultsPercent.text = "${it.compatibility.percent}%"
                binding.ringProgress.progress = it.compatibility.percent
                binding.tvLove.text = "${it.compatibility.categories[0].percent}%"
                binding.tvSex.text = "${it.compatibility.categories[1].percent}%"
                binding.tvFamily.text = "${it.compatibility.categories[2].percent}%"
                binding.tvFriendship.text = "${it.compatibility.categories[3].percent}%"
                binding.tvBusiness.text = "${it.compatibility.categories[4].percent}%"
                binding.pbLove.progress = it.compatibility.categories[0].percent
                binding.pbSex.progress = it.compatibility.categories[1].percent
                binding.pbFamily.progress = it.compatibility.categories[2].percent
                binding.pbFriendship.progress = it.compatibility.categories[3].percent
                binding.pbBusiness.progress = it.compatibility.categories[4].percent
                binding.overall.text = it.compatibility.overall_description
                binding.tvValues.text = it.compatibility.values_description
                binding.tvLoveDetail.text = it.compatibility.love_description
                binding.de.text = it.compatibility.description
            }
        }

//        compat_results_again
        binding.compatResultsAgain.setOnClickListener {
            onBackPressed()
        }



//        compat_results_close
        binding.compatResultsClose.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }

        binding.compatResultsRedirChatButton.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", "walkinsvicky@gmail.com", null
                )
            )
            intent.putExtra(Intent.EXTRA_SUBJECT, "subject")
            intent.putExtra(Intent.EXTRA_TEXT, "Horoscope")
            startActivity(Intent.createChooser(intent, "Choose apps to connect with us :"))
            binding.compatResultsRedirChatButton.isEnabled = false
            val enableButton = Runnable { binding.compatResultsRedirChatButton.isEnabled = true }
            Handler().postDelayed(enableButton, 1000)
        }


    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

}