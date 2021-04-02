package fpt.adtrue.horoscope.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import fpt.adtrue.horoscope.R
import fpt.adtrue.horoscope.adapter.SignPagerAdapter
import fpt.adtrue.horoscope.api.Utils
import fpt.adtrue.horoscope.api.Utils.setDataLocal
import fpt.adtrue.horoscope.application.App
import fpt.adtrue.horoscope.databinding.ActivityCompatChoiceBinding

class ChoiceCompatActivity: AppCompatActivity(){

    private lateinit var binding: ActivityCompatChoiceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_compat_choice)
        binding.compatChoiceLeftViewpager.adapter = SignPagerAdapter(supportFragmentManager)

        binding.compatChoiceLeftViewpager.currentItem = App.SIGN


            binding.compatChoiceLeftNext.setOnClickListener {
            if (binding.compatChoiceLeftViewpager.currentItem == 11)  binding.compatChoiceLeftViewpager.currentItem = 0
            else binding.compatChoiceLeftViewpager.currentItem = binding.compatChoiceLeftViewpager.currentItem + 1
        }

        binding.compatChoiceLeftPrev.setOnClickListener {
            if (binding.compatChoiceLeftViewpager.currentItem == 0)  binding.compatChoiceLeftViewpager.currentItem = 11
            else binding.compatChoiceLeftViewpager.currentItem = binding.compatChoiceLeftViewpager.currentItem -1
        }

        binding.compatChoiceRightViewpager.adapter = SignPagerAdapter(supportFragmentManager)
        if (App.HER != 100) binding.compatChoiceRightViewpager.currentItem = App.HER
        binding.compatChoiceRightNext.setOnClickListener {
            if (binding.compatChoiceRightViewpager.currentItem == 11) binding.compatChoiceRightViewpager.currentItem = 0
            else   binding.compatChoiceRightViewpager.currentItem = binding.compatChoiceRightViewpager.currentItem + 1
        }

        binding.compatChoiceRightPrev.setOnClickListener {
            if (binding.compatChoiceRightViewpager.currentItem == 0) binding.compatChoiceRightViewpager.currentItem = 11
              else  binding.compatChoiceRightViewpager.currentItem = binding.compatChoiceRightViewpager.currentItem -1
            print("a")
        }

        binding.compatChoiceBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }

        binding.compatChoiceBtn.setOnClickListener {
            App.SIGN = binding.compatChoiceLeftViewpager.currentItem
            App.HER = binding.compatChoiceRightViewpager.currentItem
            setDataLocal( App.SIGN,applicationContext)
            val intent = Intent(applicationContext, ResultCompatActivity::class.java)
            startActivities(arrayOf(intent))
        }

//        compat_choice_go_wims
        binding.compatChoiceGoWims.setOnClickListener {
            setDataLocal( App.SIGN,applicationContext)
            val intent = Intent(applicationContext, ChoiceSignHerActivity::class.java)
            startActivities(arrayOf(intent))
        }

    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }

}