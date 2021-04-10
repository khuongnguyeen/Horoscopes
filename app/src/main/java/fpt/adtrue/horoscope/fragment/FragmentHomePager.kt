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
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import fpt.adtrue.horoscope.activity.*
import fpt.adtrue.horoscope.api.HoroscopeApi
import fpt.adtrue.horoscope.api.Utils
import fpt.adtrue.horoscope.api.Utils.getAmazon
import fpt.adtrue.horoscope.application.App
import fpt.adtrue.horoscope.databinding.PagerItemRecyclerviewBinding
import fpt.adtrue.horoscope.model.DataAmazonaws
import fpt.adtrue.horoscope.model.DataHoroscope
import fpt.adtrue.horoscope.tarot3.TarotCircleCardActivity
import java.util.*

class FragmentHomePager(private val position:Int):Fragment() {

    private lateinit var binding: PagerItemRecyclerviewBinding
    private var runnable: Runnable? = null

    val dataAmazon = MutableLiveData<DataAmazonaws>()
    val dataAmazon2 = MutableLiveData<DataAmazonaws>()
    val dataAmazon3 = MutableLiveData<DataAmazonaws>()
    private val horoscopeApi2: HoroscopeApi = Utils.createRetrofit2()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =  PagerItemRecyclerviewBinding.inflate(inflater,container,false)
        val calendar = Calendar.getInstance(TimeZone.getDefault()) as Calendar
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


        var mon = "${calendar.get(Calendar.MONTH) + 1}"
        var today ="${calendar.get(Calendar.DAY_OF_MONTH) + 1}"
        var tomorrow ="${calendar.get(Calendar.DAY_OF_MONTH) }"
        var yesterday ="${calendar.get(Calendar.DAY_OF_MONTH) - 1}"

        if (calendar.get(Calendar.MONTH) + 1 < 10){
            mon= "0${calendar.get(Calendar.MONTH) + 1}"
        }
        if (calendar.get(Calendar.DAY_OF_MONTH) <10){
            tomorrow = "0${calendar.get(Calendar.DAY_OF_MONTH)}"
            today = "0${calendar.get(Calendar.DAY_OF_MONTH)}"
            yesterday = "0${calendar.get(Calendar.DAY_OF_MONTH)}"
        }
        getAmazon(tomorrow,mon,calendar.get(Calendar.YEAR),horoscopeApi2,dataAmazon3)
        getAmazon(today,mon,calendar.get(Calendar.YEAR),horoscopeApi2,dataAmazon2)
        getAmazon(yesterday,mon,calendar.get(Calendar.YEAR),horoscopeApi2,dataAmazon)


        if (position == 0){
            dataAmazon.observe(this,  Observer{ ok ->
                ok.daily_horoscope.forEach {
                    if (App.getSign()[App.SIGN].name.equals(it.name,true)){
                        binding.tvDescription.text = it.sign.general
                        binding.tvLove2.text = it.sign.love
                        binding.tvWork2.text = it.sign.work
                        binding.tvMoney2.text = it.sign.money

                    }
                }
            })

            print("ok")
            App.getViewModel().getHoroscope(App.getZodiac()[App.SIGN].name,"yesterday")
            App.getViewModel().data2.observe(this,  Observer{
                updateData(it)
            })
        }
        if (position == 1){
            dataAmazon2.observe(this,  Observer{ ok ->
                ok.daily_horoscope.forEach {
                    if (App.getSign()[App.SIGN].name.equals(it.name,true)){
                        binding.tvDescription.text = it.sign.general
                        binding.tvLove2.text = it.sign.love
                        binding.tvWork2.text = it.sign.work
                        binding.tvMoney2.text = it.sign.money

                    }
                }
            })
        }
        if (position == 2){
            dataAmazon3.observe(this,  Observer{ ok ->
                ok.daily_horoscope.forEach {
                    if (App.getSign()[App.SIGN].name.equals(it.name,true)){
                        binding.tvDescription.text = it.sign.general
                        binding.tvLove2.text = it.sign.love
                        binding.tvWork2.text = it.sign.work
                        binding.tvMoney2.text = it.sign.money

                    }
                }
            })


            App.getViewModel().getHoroscope(App.getZodiac()[App.SIGN].name,"tomorrow")
            App.getViewModel().data1.observe(this,  Observer{
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
        binding.homepageRecap.text = "${App.getZodiac()[App.SIGN].name} - ${it.currentDate}"
        binding.tvDescriptionCompatibility.text = it.compatibility
        binding.tvDescriptionMood.text = it.mood
        binding.tvDescriptionColor.text = it.color
        binding.tvDescriptionLuckyNumber.text = it.luckyNumber
        binding.tvDescriptionLuckyTime.text = it.luckyTime
    }

//    private fun updateDataAmazon(it:DataAmazonaws){
//
//        binding.tvDescription.text = it.daily_horoscope
//    }

}