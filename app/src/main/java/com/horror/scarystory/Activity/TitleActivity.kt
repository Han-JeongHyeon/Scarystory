package com.horror.scarystory.Activity

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.horror.scarystory.Adapter.Adapter
import com.horror.scarystory.Adapter.AdapterData
import com.horror.scarystory.PrefKey
import com.horror.scarystory.R
import com.horror.scarystory.databinding.ActivityTitleBinding
import kotlinx.android.synthetic.main.activity_title.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class TitleActivity : AppCompatActivity() {

    //xml 연결
    private val binding by lazy { ActivityTitleBinding.inflate(layoutInflater) }

    lateinit var adapter: Adapter
    val datas = mutableListOf<AdapterData>()

    companion object {
        var time = 0L
    }

    var titleTop: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        loadAd()
        setOnClickListener()
        setBottomSeat()
        dialog()

        adapter = Adapter(this).apply {
            setOnItemClickListener(object : Adapter.OnItemClickListener { // 이벤트 리스너
                override fun onItemClick(v: View) {
                    finish()
                }
            })
        }
        binding.RecycleView.adapter = adapter

        btnAll.setTextColor(Color.parseColor("#FFFFFF"))

        //툴바
        val toolbar = findViewById<Toolbar>(R.id.Title_bar)
        setSupportActionBar(toolbar)

        titleTop = findViewById<TextView>(R.id.title)

        ViewUpdate("All")

//        binding.swAm.isChecked = PrefKey(this).getBoolean("AM_ON", true)
//        binding.swPm.isChecked = PrefKey(this).getBoolean("PM_ON", true)
//        binding.swUpdate.isChecked = PrefKey(this).getBoolean("UPDATE_ON", true)

        binding.fullScreen.isChecked = PrefKey(this).getBoolean("fullScreen", false)
        fullScreenMode(binding.fullScreen.isChecked)

        //검색
        binding.editTitle.setOnEditorActionListener { textView, i, keyEvent ->
            if (i == EditorInfo.IME_ACTION_DONE) {
                if (binding.editTitle.text.isEmpty()) {
                    ViewUpdate("All")
                } else {
                    PrefKey(this).putString("Search_Word", "${binding.editTitle.text}")

                    ViewUpdate("Search")
                    binding.editTitle.setText("")
                }
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.editTitle.windowToken, 0)

                fullScreenMode(PrefKey(this).getBoolean("fullScreen", false))
            }

            true
        }
    }

    fun dialog(){
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.showupdate_popup)

        if (Random().nextInt(20) + 1 == 1 && !PrefKey(this).getBoolean("review", false)){
            dialog.show()
        }
        
        val btnNo = dialog.findViewById<Button>(R.id.btnNo)
        val btnYes = dialog.findViewById<Button>(R.id.btnYes)

        btnNo.setOnClickListener {
            dialog.dismiss()
        }

        btnYes.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.addCategory(Intent.CATEGORY_DEFAULT)
            intent.data = Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
            startActivity(intent)
            PrefKey(this).putBoolean("review", true)
            dialog.dismiss()
        }
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
                "앱 버전 (AppVersion): ${
                    applicationContext.packageManager.getPackageInfo(
                        applicationContext.packageName,
                        0
                    ).versionName
                }\n" +
                        "기기명 (Device): ${Build.MODEL}\n" +
                        "안드로이드 OS버전 (Android OS Ver): ${Build.VERSION.RELEASE}\n\n" +
                        "$content : \n\n\n"
            )
            startActivity(email)
        } catch (e: Exception) {
            Toast.makeText(
                applicationContext,
                "Gmail앱이 활성화 되어 있지 않아 실행할 수 없습니다.",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    fun loadAd() {
        MobileAds.initialize(this) {}

        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)

        //배너 광고 이벤트
        binding.adView.adListener = object : AdListener() {
            override fun onAdClicked() {}
            override fun onAdClosed() {}
            override fun onAdFailedToLoad(adError: LoadAdError) {}
            override fun onAdImpression() {}
            override fun onAdLoaded() {}
        }
    }

    fun setOnClickListener() {
        binding.apply {
            btnAll.setOnClickListener {
                ViewUpdate("All")
            }

            btnUnread.setOnClickListener {
                ViewUpdate("Unread")
            }

            btnBookmark.setOnClickListener {
                ViewUpdate("Bookmark")
            }

            btnSetting.setOnClickListener {
                ViewUpdate("Setting")
            }
            //문의하기 기능
            Update.setOnClickListener {
                sendMail("[이해하면 무서운 이야기 업데이트 문의]", "원하는 업데이트")
            }

            Error.setOnClickListener {
                sendMail("[이해하면 무서운 이야기 오류 문의]", "오류 설명")
            }

            fullScreen.setOnCheckedChangeListener { compoundButton, isChecked ->
                PrefKey(this@TitleActivity).putBoolean("fullScreen", isChecked)
                fullScreenMode(isChecked)
            }

//            swAm.setOnCheckedChangeListener { compoundButton, isChecked ->
//                PrefKey(this@TitleActivity).putBoolean("AM_ON", isChecked)
//            }
//
//            swPm.setOnCheckedChangeListener { compoundButton, isChecked ->
//                PrefKey(this@TitleActivity).putBoolean("PM_ON", isChecked)
//            }
//
//            swUpdate.setOnCheckedChangeListener { compoundButton, isChecked ->
//                PrefKey(this@TitleActivity).putBoolean("UPDATE_ON", isChecked)
//            }
        }
    }

    fun setBottomSeat() {
        binding.apply {
            btnAll.setTextColor(Color.parseColor("#A5A5A5"))
            btnUnread.setTextColor(Color.parseColor("#A5A5A5"))
            btnBookmark.setTextColor(Color.parseColor("#A5A5A5"))
            btnSetting.setTextColor(Color.parseColor("#A5A5A5"))
        }
    }

    var viewName = ""

    //뷰에 텍스트 넣기
    fun ViewUpdate(name: String): Boolean {
        if (System.currentTimeMillis() - time < 1000L) {
            return true
        }

        time = System.currentTimeMillis()
        setBottomSeat()

        if (name == "Setting") {
            binding.btnSetting.setTextColor(Color.parseColor("#FFFFFF"))
            viewName = name
            binding.RecycleView.visibility = View.GONE
            binding.llSetting.visibility = View.VISIBLE

            return true
        }

        viewName = name
        binding.RecycleView.visibility = View.VISIBLE
        binding.llSetting.visibility = View.GONE

        setViewSetting(name)

        return true
    }

    fun setViewSetting(name: String) {
        val title = resources.getStringArray(R.array.name)

        val numvalue = title.size

        CoroutineScope(Dispatchers.IO).launch {
            datas.apply {
                datas.clear()
                for (i in 0 until numvalue) {
                    when (name) {
                        "All" -> {
                            add(
                                AdapterData(
                                    title[i],
                                    PrefKey(this@TitleActivity).getBoolean(
                                        "li_boolean_${i}",
                                        false
                                    ),
                                    name,
                                    i
                                )
                            )
                        }
                        "Unread" -> {
                            if (!PrefKey(this@TitleActivity).getBoolean("li_boolean_${i}", false)) {
                                add(AdapterData(title[i], false, name, i))
                            }
                        }
                        "Bookmark" -> {
                            if (PrefKey(this@TitleActivity).getBoolean(
                                    "favor_boolean_${i}",
                                    false
                                )
                            ) {
                                add(AdapterData(title[i], false, name, i))
                            }
                        }
                        "Search" -> {
                            if (title[i].contains(
                                    "${
                                        PrefKey(this@TitleActivity).getString(
                                            "Search_Word",
                                            ""
                                        )
                                    }"
                                )
                            ) {
                                add(AdapterData(title[i], false, name, i))
                            }
                        }
                    }
                }
            }

            withContext(Dispatchers.Main) {
                when(name) {
                    "All" -> {
                        binding.btnAll.setTextColor(Color.parseColor("#FFFFFF"))
//                            type?.text = "전체"
                    }
                    "Unread" -> {
                        binding.btnUnread.setTextColor(Color.parseColor("#FFFFFF"))
//                            type?.text = "안본글"
                    }
                    "Bookmark" -> {
                        binding.btnBookmark.setTextColor(Color.parseColor("#FFFFFF"))
//                            type?.text = "즐겨찾기"
                    }
                    "Search" -> {
                        binding.btnAll.setTextColor(Color.parseColor("#FFFFFF"))
                        titleTop?.text = PrefKey(this@TitleActivity).getString("Search_Word", "")
                    }
                }

                adapter.datas = datas
                adapter.notifyDataSetChanged()

                //리사이클뷰 애니메이션 (페이드 인)
                val recycleAnim =
                    AnimationUtils.loadAnimation(this@TitleActivity, R.anim.recycle_anim)
                binding.RecycleView.visibility = View.INVISIBLE
                Handler().postDelayed(Runnable {
                    binding.RecycleView.startAnimation(recycleAnim)
                    binding.RecycleView.visibility = View.VISIBLE
                }, 200)

                val list_num = getSharedPreferences("number", 0)

                //리사이클뷰 포지션 변경
                if (name == "All") {
                    binding.RecycleView.scrollToPosition(list_num.getInt("number", 0))
                }
            }

        }
    }

    //전체화면
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

    var time = 0L

    //뒤로가기 버튼
    override fun onBackPressed() {
        if(System.currentTimeMillis() - time <= 2500L) {
            finish()
        }
        Toast.makeText(this, "앱을 종료하기 위해 '뒤로' 버튼을 한번 더 눌러주세요.",Toast.LENGTH_SHORT).show()
        time = System.currentTimeMillis()
    }

}