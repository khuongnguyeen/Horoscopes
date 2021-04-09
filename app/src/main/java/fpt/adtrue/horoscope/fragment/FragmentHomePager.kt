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
import fpt.adtrue.horoscope.activity.*
import fpt.adtrue.horoscope.application.App
import fpt.adtrue.horoscope.databinding.PagerItemRecyclerviewBinding
import fpt.adtrue.horoscope.model.DataHoroscope
import fpt.adtrue.horoscope.tarot3.TarotCircleCardActivity
import java.util.*

class FragmentHomePager(private val position:Int):Fragment() {

    private lateinit var binding: PagerItemRecyclerviewBinding
    private var runnable: Runnable? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =  PagerItemRecyclerviewBinding.inflate(inflater,container,false)
        runnable = Runnable {
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

            val calendar = Calendar.getInstance(TimeZone.getDefault()) as Calendar
            var mon = "${calendar.get(Calendar.MONTH) + 1}"
            var day ="${calendar.get(Calendar.DAY_OF_MONTH)}"

            if (calendar.get(Calendar.MONTH) + 1 < 10){
                mon= "0${calendar.get(Calendar.MONTH) + 1}"
            }
            if (calendar.get(Calendar.DAY_OF_MONTH) <10){
                day = "0${calendar.get(Calendar.DAY_OF_MONTH)}"
            }


            App.getViewModel().getHoroscope(App.getZodiac()[App.SIGN].name,"yesterday")
            App.getViewModel().getAmazon(day,mon,calendar.get(Calendar.YEAR))
            print("ok")
            App.getViewModel().data2.observe(this,  {
                updateData(it)
            })
        }
        if (position == 1){
            App.getViewModel().getHoroscope(App.getZodiac()[App.SIGN].name,"today")
            App.getViewModel().data.observe(this,  {
                updateData(it)
            })
        }
        if (position == 2){
            App.getViewModel().getHoroscope(App.getZodiac()[App.SIGN].name,"tomorrow")
            App.getViewModel().data1.observe(this,  {
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

        binding.homepageTarot.setOnClickListener {
            StartReadingTarotActivity.start(context!!)
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