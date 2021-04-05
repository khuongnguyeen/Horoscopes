package fpt.adtrue.horoscope.tarot3

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import fpt.adtrue.horoscope.R
import fpt.adtrue.horoscope.databinding.ActivityTarotCircleCardBinding

class TarotCircleCardActivity : AppCompatActivity() {

    private lateinit var binding:ActivityTarotCircleCardBinding
    private var mContext: Context? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_tarot_circle_card)
        mContext = this
        binding.btnChoose.setOnClickListener {
            binding.cardLayout.startExpendCard()
            binding.btnChoose.visibility = View.GONE
//            binding.firstImageBorder.visibility = View.VISIBLE
//            binding.secondImageBorder.visibility = View.VISIBLE
//            binding.thirdImageBorder.visibility = View.VISIBLE
        }
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, TarotCircleCardActivity::class.java))
        }
    }
}