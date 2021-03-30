package fpt.adtrue.horoscope.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import bot.box.horology.annotation.DURATION
import bot.box.horology.annotation.SUNSIGN
import bot.box.horology.api.Horoscope
import bot.box.horology.delegate.Response
import bot.box.horology.hanshake.HorologyController
import bot.box.horology.pojo.Zodiac
import com.google.android.material.tabs.TabLayout
import fpt.adtrue.horoscope.R
import fpt.adtrue.horoscope.adapter.HomePagerAdapter
import fpt.adtrue.horoscope.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), TabLayout.OnTabSelectedListener {

    private lateinit var binding: ActivityMainBinding

    @SuppressLint("RtlHardcoded")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.slidingTabs.setOnTabSelectedListener(this)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
//        val actionBar = supportActionBar
        binding.toolbar.setNavigationOnClickListener {
            binding.drawerLayout.openDrawer(Gravity.LEFT)
        }
//        setNavigationDrawer()


        val runTab = Runnable {
            if (binding.slidingTabs.width < this.resources.displayMetrics.widthPixels) {
                binding.slidingTabs.tabMode = TabLayout.MODE_FIXED
                val mParams: ViewGroup.LayoutParams = binding.slidingTabs.layoutParams
                mParams.width = ViewGroup.LayoutParams.MATCH_PARENT
                binding.slidingTabs.layoutParams = mParams
            } else {
                binding.slidingTabs.tabMode = TabLayout.MODE_SCROLLABLE
            }
        }

        binding.slidingTabs.post(runTab)
        binding.slidingTabs.setupWithViewPager(binding.viewpager)
        binding.viewpager.adapter = HomePagerAdapter(supportFragmentManager)
        binding.slidingTabs.getTabAt(2)?.select()

        val hGemini = Horoscope.Zodiac(applicationContext)
            .requestSunSign(SUNSIGN.GEMINI)
            .requestDuration(DURATION.TODAY)
            .showLoader(true)
            .isDebuggable(true)
            .fetchHoroscope()


        val cGemini = HorologyController(object : Response {
            override fun onResponseObtained(zodiac: Zodiac?) {
                val horoscope = zodiac?.horoscope
                Log.e("_____________________", "$horoscope")
                val sunsign = zodiac?.sunSign
                Log.e("_____________________", "$sunsign")
                val date = zodiac?.date
                Log.e("_____________________", "$date")

            }

            override fun onErrorObtained(errormsg: String?) {

            }
        })

        cGemini.requestConstellations(hGemini)


    }

    override fun onTabSelected(tab: TabLayout.Tab?) {}

    override fun onTabUnselected(tab: TabLayout.Tab?) {}

    override fun onTabReselected(tab: TabLayout.Tab?) {}

//    private fun setNavigationDrawer() {
//        binding.navView.setNavigationItemSelectedListener {
//            true
//        }
//    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.mymenu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Toast.makeText(applicationContext, "Font Size", Toast.LENGTH_SHORT).show()

        return super.onOptionsItemSelected(item)
    }
}