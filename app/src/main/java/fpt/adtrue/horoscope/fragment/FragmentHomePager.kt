package fpt.adtrue.horoscope.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import fpt.adtrue.horoscope.activity.ChoiceCompatActivity
import fpt.adtrue.horoscope.activity.ChooseSignActivity
import fpt.adtrue.horoscope.activity.ProfileAstroActivity
import fpt.adtrue.horoscope.activity.ResultCompatActivity
import fpt.adtrue.horoscope.application.App
import fpt.adtrue.horoscope.databinding.PagerItemRecyclerviewBinding
import fpt.adtrue.horoscope.model.DataHoroscope

class FragmentHomePager(val position:Int):Fragment() {

    private lateinit var binding: PagerItemRecyclerviewBinding
    var runnable: Runnable? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =  PagerItemRecyclerviewBinding.inflate(inflater,container,false)
        runnable = Runnable {
//            hp_barometre
            binding.hpBarometre.animate()
                .setDuration(30000)
                .rotationBy(360F)
                .setInterpolator(LinearInterpolator())
                .withEndAction(runnable)
                .start()
        }
        runnable!!.run()
        binding.data = App.getViewModel()
        binding.hpSign.setImageResource(App.getZodiac()[App.SIGN].image2)
        if (position == 0){
            App.getViewModel().getHoroscope(App.getZodiac()[App.SIGN].name,"yesterday")
            App.getViewModel().data.observe(this, Observer {
                updateData(it)
            })
        }
        if (position == 2){
            App.getViewModel().getHoroscope(App.getZodiac()[App.SIGN].name,"today")
            App.getViewModel().data.observe(this, Observer {
                updateData(it)
            })
        }
        if (position == 4){
            App.getViewModel().getHoroscope(App.getZodiac()[App.SIGN].name,"tomorrow")
            App.getViewModel().data.observe(this, Observer {
                updateData(it)

            })
        }

        binding.homepageRedirHoroperso.setOnClickListener {
            val intent = Intent(context, ProfileAstroActivity::class.java)
            context!!.startActivities(arrayOf(intent))
            binding.homepageRedirCompat.isEnabled = false
            val enableButton = Runnable { binding.homepageRedirCompat.isEnabled = true }
            Handler().postDelayed(enableButton, 1000)
        }


        binding.homepageRedirChatButton.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", "walkinsvicky@gmail.com", null
                )
            )
            intent.putExtra(Intent.EXTRA_SUBJECT, "subject")
            intent.putExtra(Intent.EXTRA_TEXT, "Horoscope")
            startActivity(Intent.createChooser(intent, "Choose apps to connect with us :"))
            binding.homepageRedirChatButton.isEnabled = false
            val enableButton = Runnable { binding.homepageRedirChatButton.isEnabled = true }
            Handler().postDelayed(enableButton, 1000)
        }

//        homepage_redir_compat
    binding.homepageRedirCompat.setOnClickListener {
        val intent = Intent(context, ChoiceCompatActivity::class.java)
        context!!.startActivities(arrayOf(intent))
        binding.homepageRedirCompat.isEnabled = false
        val enableButton = Runnable { binding.homepageRedirCompat.isEnabled = true }
        Handler().postDelayed(enableButton, 1000)
    }

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun updateData(it:DataHoroscope) {
        binding.tvDescription.text = it.description
        binding.homepageRecap.text = "${App.getZodiac()[App.SIGN].name} - ${it.currentDate}"
        binding.tvDescriptionCompatibility.text = it.compatibility
        binding.tvDescriptionMood.text = it.mood
        binding.tvDescriptionColor.text = it.color
        binding.tvDescriptionLuckyNumber.text = it.luckyNumber
        binding.tvDescriptionLuckyTime.text = it.luckyTime
    }

}