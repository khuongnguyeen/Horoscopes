package fpt.adtrue.horoscope.tarot2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import fpt.adtrue.horoscope.R

class TarotAnimSwitchActivity :AppCompatActivity(){
    var mBtnStart: Button? = null
    var mTarotShuffleView: TarotShuffleView? = null
    var mTarotSelectionView: TarotSelectionView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tarot_switch)
        initView()
        mBtnStart!!.setOnClickListener { mTarotShuffleView!!.anim() }
        mTarotShuffleView!!.setOnShuffleListener(object : OnShuffleViewListener {
            override fun onShuffleAnimDone() {
                mTarotShuffleView!!.visibility = View.GONE
                mTarotSelectionView!!.showTarotSelectionView()
                mBtnStart!!.visibility = View.GONE
            }
        })
    }

    private fun initView() {
        mTarotShuffleView = findViewById(R.id.tarot_shuffle_view)
        mBtnStart = findViewById(R.id.btn_start)
        mTarotSelectionView = findViewById(R.id.tarot_selection_view)
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, TarotAnimSwitchActivity::class.java))
        }
    }
}