package fpt.adtrue.horoscope.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import fpt.adtrue.horoscope.R
import fpt.adtrue.horoscope.adapter.StartReadingTarotAdapter
import fpt.adtrue.horoscope.api.Utils.sttBar
import fpt.adtrue.horoscope.application.App
import fpt.adtrue.horoscope.databinding.TarotReadingActivityBinding
import fpt.adtrue.horoscope.tarot3.TarotCircleCardActivity

class StartReadingTarotActivity : AppCompatActivity(), StartReadingTarotAdapter.ITarot {

    private lateinit var binding: TarotReadingActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.tarot_reading_activity)
        App.getViewModel().data.observe(this,  {
            binding.tvToday.text = it.currentDate
        })


        binding.compatChoiceBack.setOnClickListener {
            onBackPressed()
        }
        binding.content.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        binding.content.adapter = StartReadingTarotAdapter(this)
        sttBar(this)
    }


    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, StartReadingTarotActivity::class.java))
        }
    }

    override fun onClickItem(position: Int) {
        if (position == 0) {
            TarotCircleCardActivity.start(this)
        } else Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show()
    }
}