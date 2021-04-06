package fpt.adtrue.horoscope.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import fpt.adtrue.horoscope.R
import fpt.adtrue.horoscope.application.App
import fpt.adtrue.horoscope.databinding.ActivityTarotResultsBinding

class ResultsTarotActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTarotResultsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tarot_results)
        var loveUpright = ""
        var loveReversed = ""
        var careerUpright = ""
        var futureUpright = ""
        var careerReversed = ""
        var futureReversed = ""
        App.getTarot().forEach {
            if (it.name == App.POSITION_LOVE) {
                binding.ivLove.setImageResource(it.img!!)
                loveUpright = it.keywords.love.upright
                loveReversed = it.keywords.love.reversed
            }
            if (it.name == App.POSITION_CAREER) {
                binding.ivCareer.setImageResource(it.img!!)
                careerUpright = it.keywords.career.upright
                careerReversed = it.keywords.career.reversed
            }
            if (it.name == App.POSITION_FUTURE) {
                binding.ivFuture.setImageResource(it.img!!)
                futureUpright = it.keywords.future.upright
                futureReversed = it.keywords.future.reversed
            }
        }
        binding.txtLove.visibility = View.VISIBLE
        binding.tvLoveDetail.text = loveUpright
        binding.tvReversed.text = loveReversed

        binding.ivLove.setOnClickListener {
            binding.txtLove.visibility = View.VISIBLE
            binding.txtCareer.visibility = View.INVISIBLE
            binding.txtFuture.visibility = View.INVISIBLE
            binding.tvLoveDetail.text = loveUpright
            binding.tvReversed.text = loveReversed

        }
        binding.ivCareer.setOnClickListener {
            binding.txtLove.visibility = View.INVISIBLE
            binding.txtCareer.visibility = View.VISIBLE
            binding.txtFuture.visibility = View.INVISIBLE
            binding.tvLoveDetail.text = careerUpright
            binding.tvReversed.text = careerReversed


        }
        binding.ivFuture.setOnClickListener {
            binding.txtLove.visibility = View.INVISIBLE
            binding.txtCareer.visibility = View.INVISIBLE
            binding.txtFuture.visibility = View.VISIBLE
            binding.tvLoveDetail.text = futureUpright
            binding.tvReversed.text = futureReversed

        }


    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, ResultsTarotActivity::class.java))
        }
    }
}