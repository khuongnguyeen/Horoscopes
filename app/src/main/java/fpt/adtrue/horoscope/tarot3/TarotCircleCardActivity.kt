package fpt.adtrue.horoscope.tarot3

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import fpt.adtrue.horoscope.R
import fpt.adtrue.horoscope.api.Utils
import fpt.adtrue.horoscope.databinding.ActivityTarotCircleCardBinding

class TarotCircleCardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTarotCircleCardBinding
    private var mContext: Context? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tarot_circle_card)
        mContext = this
        val enableButton = Runnable { binding.cardLayout.startExpendCard() }
        Handler(Looper.getMainLooper()).postDelayed(enableButton, 500)
        val enableButton2 = Runnable { binding.tvPick.visibility = View.VISIBLE }
        Handler(Looper.getMainLooper()).postDelayed(enableButton2, 2000)

        Utils.sttBar(this)
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, TarotCircleCardActivity::class.java))
        }
    }
}