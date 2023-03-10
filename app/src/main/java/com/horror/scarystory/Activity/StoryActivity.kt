package com.horror.scarystory.Activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Typeface
import android.icu.text.CaseMap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import com.google.android.gms.ads.*
import com.google.android.gms.ads.initialization.InitializationStatus
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.horror.scarystory.Adapter.AdapterData
import com.horror.scarystory.PrefKey
import com.horror.scarystory.R
import com.horror.scarystory.Story.*
import com.horror.scarystory.databinding.ActivityStoryBinding
import kotlinx.android.synthetic.main.activity_story.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.DataInputStream
import java.lang.Exception
import java.text.Bidi

class StoryActivity : AppCompatActivity() {

    private val binding by lazy { ActivityStoryBinding.inflate(layoutInflater) }

    var Tool_bar: Toolbar? = null

    var seek: SharedPreferences? = null
    var font: SharedPreferences? = null
    var Bookmark: Button? = null

    private var mInterstitialAd: InterstitialAd? = null

    fun addAd() {
        MobileAds.initialize(this) {}

        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)

        //?????? ?????? ?????????
        binding.adView.adListener = object : AdListener() {
            override fun onAdClicked() {}

            override fun onAdClosed() {}

            override fun onAdFailedToLoad(adError: LoadAdError) {}

            override fun onAdImpression() {}

            override fun onAdLoaded() {}
        }

        //?????? ?????? ??????
        InterstitialAd.load(this@StoryActivity,
            "ca-app-pub-8461307543970328/8595808456",
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d("??????", "?????? ??????")
                    //?????? ?????? ?????? ???
                    //?????? ?????? ??????
                    InterstitialAd.load(this@StoryActivity,
                        "ca-app-pub-8461307543970328/2685006225",
                        adRequest, object : InterstitialAdLoadCallback() {
                            override fun onAdFailedToLoad(adError: LoadAdError) {
                                Log.d("??????", "?????? ??????")
                                //?????? ?????? ?????? ???
                                InterstitialAd.load(this@StoryActivity,
                                    "ca-app-pub-8461307543970328/9771239466",
                                    adRequest, object : InterstitialAdLoadCallback() {
                                        override fun onAdFailedToLoad(adError: LoadAdError) {
                                            Log.d("??????", "?????? ?????????")
                                            mInterstitialAd = null
                                        }

                                        override fun onAdLoaded(interstitialAd: InterstitialAd) {
                                            Log.d("??????", "?????? ??????")
                                            mInterstitialAd = interstitialAd
                                        }
                                    })
                            }

                            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                                Log.d("??????", "?????? ??????")
                                mInterstitialAd = interstitialAd
                            }
                        })
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    Log.d("??????", "?????? ??????")
                    mInterstitialAd = interstitialAd

                }
            })

    }

    companion object {
        var position = 0
        var tag = ""
        var title = ""
    }

    fun setStory() {
        val TitleBar = findViewById<TextView>(R.id.top_title)

        val value: String = when (position / 100) {
            0 -> story0.getStory(position)
            1 -> story1.getStory(position % 100)
            else -> {
                "null"
            }
        }

        binding.story.text = value.substring(0, value.indexOf("@"))
        val text = if(value.substring(value.indexOf("@") + 1) == "") {
            "??? ???????????? ????????? ????????????."
        } else {
            value.substring(value.indexOf("@") + 1)
        }
        binding.interText.text = text
        title = Title[position]

        binding.title.text = title
        TitleBar.text = "${position + 1}. $title"

        //????????? ??? ??????
        PrefKey(this).putInt("number", position)

        //???????????? ??? ??????
        PrefKey(this).putBoolean("li_boolean_$position", true)

        //???????????? ??????
        when (PrefKey(this).getBoolean("favor_boolean_$position", false)) {
            true -> {
                Bookmark?.setBackgroundResource(R.drawable.bookmark_btn)
            }
            false -> {
                Bookmark?.setBackgroundResource(R.drawable.bookmark_border_btn)
            }
        }

        getBtn("Home", tag)

        //?????? ????????? ??????
        getSeek(PrefKey(this).getInt("seek", 22))

        //?????? ????????????
        getFont(PrefKey(this).getInt("font", 1), "")
    }

    var Title: Array<String> = listOf<String>().toTypedArray()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Bookmark = findViewById<Button>(R.id.bookmark)
        Title = resources.getStringArray(R.array.name)
        val backBtn = findViewById<Button>(R.id.beck_btn)

        //???????????? ??????
        fullScreenMode(PrefKey(this).getBoolean("fullScreen", false))

        position = intent.getIntExtra("position", -1)
        tag = intent.getStringExtra("tag").toString()
        title = Title[position]

        setupInterstitialAd()
        addAd()
        setStory()

        //??????
        Tool_bar = findViewById(R.id.Story_bar)
        setSupportActionBar(Tool_bar)

        //??? ??????
        backBtn.setOnClickListener {
            val intent = Intent(this, TitleActivity::class.java)
            intent.putExtra("name", tag)
            startActivity(intent)

            finish()

            if (mInterstitialAd != null && PrefKey(this).getInt("count", 0) >= 5) {
                mInterstitialAd?.show(this@StoryActivity)
                PrefKey(this).putInt("count", 0)
            }

            overridePendingTransition(R.anim.activity_down_sub, R.anim.activity_down)
        }

        //????????? ??????
        Bookmark!!.setOnClickListener {
            when (PrefKey(this).getBoolean("favor_boolean_$position", false)) {
                true -> {
                    Bookmark?.setBackgroundResource(R.drawable.bookmark_border_btn)
                    PrefKey(this).putBoolean("favor_boolean_$position", false)
                }
                false -> {
                    Bookmark?.setBackgroundResource(R.drawable.bookmark_btn)
                    PrefKey(this).putBoolean("favor_boolean_$position", true)
                }
            }
        }

        //?????? ???????????? ??????
        binding.leftBtn.setOnClickListener {
            getBtn("Left", tag)
        }

        binding.rightBtn.setOnClickListener {
            getBtn("Right", tag)
        }

        //????????? ????????? ????????????
        binding.Layout.setOnClickListener {
            getBtn("Option", tag)
        }

        //????????? ????????? ????????????
        binding.scroll.setOnClickListener {
            getBtn("Option", tag)
        }

        //???????????? ???????????? ?????????
        binding.scroll.setOnScrollChangeListener { view, i, i2, i3, i4 ->
            if (Tool_bar?.visibility == View.VISIBLE) {
                getBtn("Option", tag)
            }
            if (!view.canScrollVertically(1) || !view.canScrollVertically(-1)) {
                getBtn("Option", tag)
            }
        }

        binding.seekbar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                getSeek(seekBar.progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        //???????????? ?????? ????????? ??????
        binding.checkSeek.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (checkSeek.text.toString().toInt() in 1..40) {
                getSeek(checkSeek.text.toString().toInt())
            } else {
                Toast.makeText(this@StoryActivity, "????????? ??? ?????? ?????? 10?????? 40?????? ?????????.", Toast.LENGTH_SHORT)
                    .show()
                checkSeek.setText(seek?.getInt("seek", 22).toString() + "")
            }
            true
        })

        binding.font1.setOnClickListener {
            getFont(1, "btn")
        }
        binding.font2.setOnClickListener {
            getFont(2, "btn")
        }
        binding.font3.setOnClickListener {
            getFont(3, "btn")
        }
        binding.font4.setOnClickListener {
            getFont(4, "btn")
        }

        binding.InterBtn.setOnClickListener {
            if (binding.interLayout.visibility == View.VISIBLE) {
                binding.interLayout.visibility = View.INVISIBLE
                Handler().postDelayed(Runnable {
                    binding.InterBtn.visibility = View.VISIBLE
                }, 700)
                binding.interLayout.startAnimation(
                    AnimationUtils.loadAnimation(
                        applicationContext,
                        R.anim.inter_down
                    )
                )
            } else {
                binding.interLayout.visibility = View.VISIBLE
                binding.InterBtn.visibility = View.INVISIBLE
                binding.interLayout.startAnimation(
                    AnimationUtils.loadAnimation(
                        applicationContext,
                        R.anim.inter_up
                    )
                )
            }
        }

        //?????? ??????
        binding.interBtn.setOnClickListener {
            if (binding.interLayout.visibility == View.VISIBLE) {
                binding.interLayout.visibility = View.INVISIBLE
                Handler().postDelayed(Runnable {
                    binding.InterBtn.visibility = View.VISIBLE
                }, 700)
                binding.interLayout.startAnimation(
                    AnimationUtils.loadAnimation(
                        applicationContext,
                        R.anim.inter_down
                    )
                )
            } else {
                binding.interLayout.visibility = View.VISIBLE
                binding.InterBtn.visibility = View.INVISIBLE
                binding.interLayout.startAnimation(
                    AnimationUtils.loadAnimation(
                        applicationContext,
                        R.anim.inter_up
                    )
                )
            }
        }

    }

    //?????? ??????
    fun getFont(Value: Int?, type: String) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O) {
            if (type == "btn") {
                Toast.makeText(this, "?????? ????????? ?????? ????????? ??????????????????.", Toast.LENGTH_SHORT).show()
            } else {
                binding.story.typeface = Typeface.createFromAsset(assets, "font/yoondokrip.ttf")
            }
            return
        } else {
            val On_Stroke = R.drawable.stroke_btn_w
            val Off_Stroke = R.drawable.stroke_btn
            val White = "#FFFFFF"
            val Gray = "#7A7A7A"

            binding.font1.setBackgroundResource(if (Value == 1) On_Stroke else Off_Stroke)
            binding.font1.setTextColor(Color.parseColor(if (Value == 1) White else Gray))
            if (Value == 1) {
                binding.story.typeface = Typeface.createFromAsset(assets, "font/yoondokrip.ttf")
            }

            binding.font2.setBackgroundResource(if (Value == 2) On_Stroke else Off_Stroke)
            binding.font2.setTextColor(Color.parseColor(if (Value == 2) White else Gray))
            if (Value == 2) {
                binding.story.typeface = Typeface.createFromAsset(assets, "font/bm.ttf")
            }

            binding.font3.setBackgroundResource(if (Value == 3) On_Stroke else Off_Stroke)
            binding.font3.setTextColor(Color.parseColor(if (Value == 3) White else Gray))
            if (Value == 3) {
                binding.story.typeface = Typeface.createFromAsset(assets, "font/bitrofri.ttf")
            }

            binding.font4.setBackgroundResource(if (Value == 4) On_Stroke else Off_Stroke)
            binding.font4.setTextColor(Color.parseColor(if (Value == 4) White else Gray))
            if (Value == 4) {
                binding.story.typeface = Typeface.createFromAsset(assets, "font/nanum.ttf")
            }

            PrefKey(this).putInt("font", Value!!)
        }
    }

    //??????
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.font -> {
                binding.textOption.visibility = View.VISIBLE
                binding.InterBtn.visibility = View.INVISIBLE
                binding.interLayout.visibility = View.INVISIBLE
            }
            R.id.Sharing -> {
                val Sharing_intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(
                        Intent.EXTRA_TEXT,
                        "$title \n\n ${binding.story.text} \n\n <??????> \n ${binding.interText.text} \n\n ??? ?????? ???????????? ??????????????? play.google.com/store/apps/details?id=com.horror.scarystory"
                    )
                    type = "text/plain"
                }
                startActivity(Intent.createChooser(Sharing_intent, null))
            }
            R.id.Update -> {
                sendMail("[????????? ????????? ???????????? ??????]", "????????? ????????????")
            }
            R.id.Error -> {
                sendMail("[????????? ????????? ?????? ??????]", "?????? ??????")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun sendMail(title: String, content: String) {
        try {
            val email = Intent(Intent.ACTION_SEND)
            email.type = "plain/text"
            email.setPackage("com.google.android.gm")
            val address = arrayOf("jhsoft04@gmail.com")
            email.putExtra(Intent.EXTRA_EMAIL, address)
            email.putExtra(Intent.EXTRA_SUBJECT, title)
            email.putExtra(
                Intent.EXTRA_TEXT,
                "??? ?????? (AppVersion): ${
                    applicationContext.packageManager.getPackageInfo(
                        applicationContext.packageName,
                        0
                    ).versionName
                }\n" +
                        "????????? (Device): ${Build.MODEL}\n" +
                        "??????????????? OS?????? (Android OS Ver): ${Build.VERSION.RELEASE}\n\n" +
                        "$content : \n\n\n"
            )
            startActivity(email)
        } catch (e: Exception) {
            Toast.makeText(
                applicationContext,
                "Gmail?????? ????????? ?????? ?????? ?????? ????????? ??? ????????????.",
                Toast.LENGTH_LONG
            ).show()
        }
    }


    //?????? ??????
    fun getSeek(Value: Int?) {

        binding.seekbar.progress = Value!!
        binding.story.textSize = Value!!.toFloat()
        binding.checkSeek.setText(Value!!.toString() + "")

        PrefKey(this).putInt("seek", Value)

    }

    //?????? ?????? & ?????? ?????????
    fun getBtn(Location: String, Tag: String) {
        var title = resources.getStringArray(R.array.name)

        var numvalue = title.size

        fullScreenMode(PrefKey(this).getBoolean("fullScreen", false))

        val posi = position

        val up = AnimationUtils.loadAnimation(applicationContext, R.anim.toolbar_up)
        val down = AnimationUtils.loadAnimation(applicationContext, R.anim.toolbar_down)

        when (Tag) {
            "All" -> {
                if (position != 0) {
                    binding.leftBtn.visibility = View.VISIBLE
                } else {
                    binding.leftBtn.visibility = View.INVISIBLE
                }
                if (position != (numvalue - 1)) {
                    binding.rightBtn.visibility = View.VISIBLE
                } else {
                    binding.rightBtn.visibility = View.INVISIBLE
                }
                if (Location == "Left") {
                    position -= 1
                }
                if (Location == "Right") {
                    position += 1
                }
            }
            "Unread" -> {
                for (i in posi downTo 0) {
                    val liboolean = getSharedPreferences("li_boolean_${i}", Context.MODE_PRIVATE)
                    ////Left ?????? ????????? ??????
                    if (Location == "Left") {
                        if (!liboolean.getBoolean("li_boolean_${i}", false)) {
                            position = i
                        }
                    }
                    //????????? ???????????? ??????
                    if (!liboolean.getBoolean("li_boolean_${i}", false)) {
                        binding.leftBtn.visibility = View.VISIBLE; break
                    }
                    if (i == 0) {
                        binding.leftBtn.visibility = View.INVISIBLE
                    }
                }
                for (i in posi until numvalue) {
                    val liboolean = getSharedPreferences("li_boolean_${i}", Context.MODE_PRIVATE)
                    //Right ?????? ????????? ??????
                    if (Location == "Right") {
                        if (!liboolean.getBoolean("li_boolean_${i}", false)) {
                            position = i
                        }
                    }
                    //????????? ???????????? ??????
                    if (!liboolean.getBoolean("li_boolean_${i}", false)) {
                        binding.rightBtn.visibility = View.VISIBLE; break
                    }
                    if (i == (numvalue - 1)) {
                        binding.rightBtn.visibility = View.INVISIBLE
                    }
                }
            }
            "Bookmark" -> {
                for (i in (posi - 1) downTo -1) {
                    val favorboolean =
                        getSharedPreferences("favor_boolean_${i}", Context.MODE_PRIVATE)
                    //Left ?????? ????????? ??????
                    if (Location == "Left") {
                        if (favorboolean.getBoolean("favor_boolean_${i}", false)) {
                            position = i
                        }
                    }
                    //????????? ???????????? ??????
                    if (i == -1) {
                        binding.leftBtn.visibility = View.INVISIBLE
                    }
                    if (favorboolean.getBoolean("favor_boolean_${i}", false)) {
                        binding.leftBtn.visibility = View.VISIBLE; break
                    }
                }
                for (i in (posi + 1)..numvalue) {
                    val favorboolean =
                        getSharedPreferences("favor_boolean_${i}", Context.MODE_PRIVATE)
                    //Right ?????? ????????? ??????
                    if (Location == "Right") {
                        if (favorboolean.getBoolean("favor_boolean_${i}", false)) {
                            position = i
                        }
                    }
                    //????????? ???????????? ??????
                    if (i == numvalue) {
                        binding.rightBtn.visibility = View.INVISIBLE
                    }
                    if (favorboolean.getBoolean("favor_boolean_${i}", false)) {
                        binding.rightBtn.visibility = View.VISIBLE; break
                    }
                }
            }
            "Search" -> {
                val Search_Word = getSharedPreferences("Search_Word", 0)
                Search_Word.getString("Search_Word", "")
                binding.leftBtn.visibility = View.INVISIBLE
                binding.rightBtn.visibility = View.INVISIBLE
                for (i in (posi - 1) downTo 0) {
                    //Left ?????? ????????? ??????
                    if (Location == "Left") {
                        if (title[i].contains("${Search_Word.getString("Search_Word", "")}")) {
                            position = i
                        }
                    }
                    //????????? ???????????? ??????
//                    if (i == -1) {
//                        binding.leftBtn.visibility = View.INVISIBLE
//                    }
                    if (title[i].contains("${Search_Word.getString("Search_Word", "")}")) {
                        binding.leftBtn.visibility = View.VISIBLE; break
                    }
                }
                for (i in (posi + 1) until numvalue) {
                    //Right ?????? ????????? ??????
                    if (Location == "Right") {
                        if (title[i].contains("${Search_Word.getString("Search_Word", "")}")) {
                            position = i
                        }
                    }
                    if (title[i].contains("${Search_Word.getString("Search_Word", "")}")) {
                        binding.rightBtn.visibility = View.VISIBLE; break
                    }
                }
            }
        }

        //????????? ????????? ??????/?????? ??????
        if (Location == "Option") {
            if (Tool_bar?.visibility == View.VISIBLE) {
                binding.leftBtn.visibility = View.INVISIBLE
                binding.rightBtn.visibility = View.INVISIBLE
                binding.textOption.visibility = View.INVISIBLE
                binding.InterBtn.visibility = View.VISIBLE
                Tool_bar?.visibility = View.INVISIBLE

                Tool_bar?.startAnimation(up)
            } else {
                Tool_bar?.visibility = View.VISIBLE

                Tool_bar?.startAnimation(down)
            }

            if (binding.interLayout.visibility == View.VISIBLE) {
                binding.interLayout.visibility = View.INVISIBLE
                Handler().postDelayed(Runnable {
                    binding.InterBtn.visibility = View.VISIBLE
                }, 700)
                binding.interLayout.startAnimation(
                    AnimationUtils.loadAnimation(
                        applicationContext,
                        R.anim.inter_down
                    )
                )
            }
        }

        if (Location == "Left" || Location == "Right") {
            binding.scroll.scrollTo(0,0)
            setStory()

            var Advertising = PrefKey(this).getInt("count", 0)

            PrefKey(this).putInt("count", Advertising + 1)

            if (mInterstitialAd != null && PrefKey(this).getInt("count", 0) >= 5) {
                mInterstitialAd?.show(this@StoryActivity)
                addAd()
                PrefKey(this).putInt("count", 0)
            }

//            if (Location == "Left") {
//                binding.root.startAnimation(
//                    AnimationUtils.loadAnimation(
//                        application,
//                        R.anim.activity_left
//                    )
//                )
//                overridePendingTransition(R.anim.activity_left, R.anim.activity_left_sub)
//            } else {
//                overridePendingTransition(R.anim.activity_right, R.anim.activity_right_sub)
//            }
        }

    }

    //?????? ??????
    private fun setupInterstitialAd() {

    }

    //?????? ????????? ???
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.story_menu, menu)
        return true
    }

    override fun onRestart() {
        super.onRestart()
//        if (mInterstitialAd != null) {
//            mInterstitialAd?.show(this@StoryActivity)
//            PrefKey(this).putInt("count", 0)
//        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, TitleActivity::class.java)
        intent.putExtra("name", tag)
        startActivity(intent)

        finish()

        if (mInterstitialAd != null && PrefKey(this).getInt("count", 0) >= 5) {
            mInterstitialAd?.show(this@StoryActivity)
            PrefKey(this).putInt("count", 0)
        }

        overridePendingTransition(R.anim.activity_down_sub, R.anim.activity_down)
    }

    //????????????
    private fun fullScreenMode(switch: Boolean) {
        var uiOption = window.decorView.systemUiVisibility
        if (switch) {
            uiOption = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        } else {
            uiOption = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        }
        window.decorView.setSystemUiVisibility(uiOption)
    }
}