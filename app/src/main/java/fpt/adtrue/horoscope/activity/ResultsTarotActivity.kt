package fpt.adtrue.horoscope.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import fpt.adtrue.horoscope.R
import fpt.adtrue.horoscope.api.Utils
import fpt.adtrue.horoscope.application.App
import fpt.adtrue.horoscope.databinding.ActivityTarotResultsBinding

class ResultsTarotActivity : Activity() {

    private lateinit var binding: ActivityTarotResultsBinding
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tarot_results)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
        binding.viewLeft.setImageResource(R.drawable.bg_view_tarot)
        binding.tvLoveDetail.text = loveUpright
        binding.tvReversed.text = loveReversed

        Utils.sttBar(this)
        binding.ivLove.setOnClickListener {
            binding.txtLove.visibility = View.VISIBLE
            binding.txtCareer.visibility = View.INVISIBLE
            binding.txtFuture.visibility = View.INVISIBLE
            binding.tvLoveDetail.text = loveUpright
            binding.tvReversed.text = loveReversed
            binding.viewLeft.setImageResource(R.drawable.bg_view_tarot)
            binding.viewCenter.setImageResource(R.drawable.bg_troan)
            binding.viewRight.setImageResource(R.drawable.bg_troan)

        }
        binding.ivCareer.setOnClickListener {
            binding.txtLove.visibility = View.INVISIBLE
            binding.txtCareer.visibility = View.VISIBLE
            binding.txtFuture.visibility = View.INVISIBLE
            binding.tvLoveDetail.text = careerUpright
            binding.tvReversed.text = careerReversed
            binding.viewCenter.setImageResource(R.drawable.bg_view_tarot)
            binding.viewRight.setImageResource(R.drawable.bg_troan)
            binding.viewLeft.setImageResource(R.drawable.bg_troan)
        }

        binding.ivFuture.setOnClickListener {
            binding.txtLove.visibility = View.INVISIBLE
            binding.txtCareer.visibility = View.INVISIBLE
            binding.txtFuture.visibility = View.VISIBLE
            binding.tvLoveDetail.text = futureUpright
            binding.tvReversed.text = futureReversed
            binding.viewRight.setImageResource(R.drawable.bg_view_tarot)
            binding.viewCenter.setImageResource(R.drawable.bg_troan)
            binding.viewLeft.setImageResource(R.drawable.bg_troan)
        }

        binding.compatChoiceBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, StartReadingTarotActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, ResultsTarotActivity::class.java))
        }
    }
}