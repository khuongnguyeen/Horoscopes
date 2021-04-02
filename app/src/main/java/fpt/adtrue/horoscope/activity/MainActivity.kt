package fpt.adtrue.horoscope.activity

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
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

        binding.toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.menu2->{
                        rateApp()
                        true
                }
                else -> false
            }
        }
        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.person_horo -> {
                    val intent = Intent(this, ProfileAstroActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.my_as_profile -> {
                    val intent = Intent(this, ChoiceCompatActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.action_aries -> {
                    App.SIGN = 0
                    val intent = Intent(this, ProfileAstroActivity::class.java)
                    intent.putExtra("horoscope", "Zodiac")
                    startActivity(intent)

                    true
                }
                R.id.action_taurus -> {
                    App.SIGN = 1
                    val intent = Intent(this, ProfileAstroActivity::class.java)
                    intent.putExtra("horoscope", "Zodiac")
                    startActivity(intent)
                    true
                }
                R.id.action_gemini -> {
                    App.SIGN = 2
                    val intent = Intent(this, ProfileAstroActivity::class.java)
                    intent.putExtra("horoscope", "Zodiac")
                    startActivity(intent)
                    true
                }
                R.id.action_cancer -> {
                    App.SIGN = 3
                    val intent = Intent(this, ProfileAstroActivity::class.java)
                    intent.putExtra("horoscope", "Zodiac")
                    startActivity(intent)
                    true
                }
                R.id.action_leo -> {
                    App.SIGN = 4
                    val intent = Intent(this, ProfileAstroActivity::class.java)
                    intent.putExtra("horoscope", "Zodiac")
                    startActivity(intent)
                    true
                }
                R.id.action_virgo -> {
                    App.SIGN = 5
                    val intent = Intent(this, ProfileAstroActivity::class.java)
                    intent.putExtra("horoscope", "Zodiac")
                    startActivity(intent)
                    true
                }
                R.id.action_libra -> {
                    App.SIGN = 6
                    val intent = Intent(this, ProfileAstroActivity::class.java)
                    intent.putExtra("horoscope", "Zodiac")
                    startActivity(intent)
                    true
                }
                R.id.action_scorpio -> {
                    App.SIGN = 7
                    val intent = Intent(this, ProfileAstroActivity::class.java)
                    intent.putExtra("horoscope", "Zodiac")
                    startActivity(intent)
                    true
                }
                R.id.action_sagittarius -> {
                    App.SIGN = 8
                    val intent = Intent(this, ProfileAstroActivity::class.java)
                    intent.putExtra("horoscope", "Zodiac")
                    startActivity(intent)
                    true
                }
                R.id.action_capricorn -> {
                    App.SIGN = 9
                    val intent = Intent(this, ProfileAstroActivity::class.java)
                    intent.putExtra("horoscope", "Zodiac")
                    startActivity(intent)
                    true
                }
                R.id.action_aquarius -> {
                    App.SIGN = 10
                    val intent = Intent(this, ProfileAstroActivity::class.java)
                    intent.putExtra("horoscope", "Zodiac")
                    startActivity(intent)
                    true
                }
                R.id.action_pisces -> {
                    App.SIGN = 11
                    val intent = Intent(this, ProfileAstroActivity::class.java)
                    intent.putExtra("horoscope", "Zodiac")
                    startActivity(intent)
                    true
                }


                else -> false
            }
        }


//        val runTab = Runnable {
//            if (binding.slidingTabs.width < this.resources.displayMetrics.widthPixels) {
//                binding.slidingTabs.tabMode = TabLayout.MODE_FIXED
//                val mParams: ViewGroup.LayoutParams = binding.slidingTabs.layoutParams
//                mParams.width = ViewGroup.LayoutParams.MATCH_PARENT
//                binding.slidingTabs.layoutParams = mParams
//            } else {
//                binding.slidingTabs.tabMode = TabLayout.MODE_SCROLLABLE
//            }
//        }
//
//        binding.slidingTabs.post(runTab)
        binding.slidingTabs.setupWithViewPager(binding.viewpager)
        binding.viewpager.adapter = HomePagerAdapter(supportFragmentManager)
        binding.slidingTabs.getTabAt(1)?.select()

        binding.logoSign.setOnClickListener {
            val intent = Intent(applicationContext, ChooseSignActivity::class.java)
            startActivities(arrayOf(intent))
            binding.logoSign.isEnabled = false
            val enableButton = Runnable { binding.logoSign.isEnabled = true }
            Handler().postDelayed(enableButton, 1000)
        }


    }

    private fun rateApp() {
        try {
            val rateIntent = rateIntentForUrl("market://details")
            startActivity(rateIntent)
        } catch (e: ActivityNotFoundException) {
            val rateIntent = rateIntentForUrl("https://play.google.com/store/apps/details")
            startActivity(rateIntent)
        }
    }

    private fun rateIntentForUrl(url: String): Intent {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(String.format("%s?id=%s", url, applicationContext.packageName))
        )
        var flags = Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_MULTIPLE_TASK
        flags = if (Build.VERSION.SDK_INT >= 21) {
            flags or Intent.FLAG_ACTIVITY_NEW_DOCUMENT
        } else {
            flags or Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET
        }
        intent.addFlags(flags)
        return intent
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
            Dialog.BUTTON_POSITIVE, "Yes"
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