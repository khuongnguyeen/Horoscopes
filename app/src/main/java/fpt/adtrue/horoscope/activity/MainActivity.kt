package fpt.adtrue.horoscope.activity

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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
import fpt.adtrue.horoscope.application.App
import fpt.adtrue.horoscope.databinding.ActivityMainBinding
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity(), TabLayout.OnTabSelectedListener {

    private lateinit var binding: ActivityMainBinding

    @SuppressLint("RtlHardcoded", "NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.logoSign.setImageResource(App.getZodiac()[App.SIGN].image)
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
        binding.viewpager.adapter = HomePagerAdapter(supportFragmentManager,binding.viewpager.currentItem)
        binding.slidingTabs.getTabAt(2)?.select()

        binding.logoSign.setOnClickListener {
            val intent = Intent(applicationContext, ChooseSignActivity::class.java)
            startActivities(arrayOf(intent))
            binding.logoSign.isEnabled = false
            val enableButton = Runnable { binding.logoSign.isEnabled = true }
            Handler().postDelayed(enableButton, 1000)
        }


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
    override fun onBackPressed() {
        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle(R.string.do_you_really_want_to_exit)
        alertDialog.setButton(
            Dialog.BUTTON_POSITIVE,"Yes"
        ) { _, _ ->
            finishAffinity()
            exitProcess(0)
        }
        alertDialog.setButton(
            Dialog.BUTTON_NEGATIVE, "No"
        ) { dialog, _ -> dialog!!.dismiss() }
        alertDialog.show()
    }
}