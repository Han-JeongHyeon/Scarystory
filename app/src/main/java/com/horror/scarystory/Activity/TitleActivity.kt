package com.horror.scarystory.Activity

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.google.android.gms.ads.*
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.horror.scarystory.*
import com.horror.scarystory.Adapter.Adapter
import com.horror.scarystory.Adapter.AdapterData
import com.horror.scarystory.R
import com.horror.scarystory.databinding.ActivityTitleBinding
import kotlinx.android.synthetic.main.activity_title.*
import kotlinx.android.synthetic.main.tiele_bar.*
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

    var titleTop: TextView? = null

    //뒤로가기 시간체크
    var time = 0L

    //광고
    private var mRewardedAd: RewardedAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        shewAd()
        setOnClickListener()
        reviewDialog()

        adapter = Adapter(this).apply {
            setOnItemClickListener(object : Adapter.OnItemClickListener { // 이벤트 리스너
                override fun onItemClick(v: View) {
                    finish()
                }
            })
        }

        binding.RecycleView.adapter = adapter

        btnAll.setTextColor(ContextCompat.getColor(this, R.color.textColor))

        //툴바
        val toolbar = findViewById<Toolbar>(R.id.Title_bar)
        toolbar.title = ""
        setSupportActionBar(toolbar)

        titleTop = findViewById(R.id.title)

        amount.text = PrefKey(this).getInt("inter",10).toString()

        ViewUpdate(Type.ALL.code)

        binding.fullScreen.isChecked = PrefKey(this).getBoolean("fullScreen", false)
        fullScreenMode(binding.fullScreen.isChecked)

        //검색
        binding.editTitle.setOnEditorActionListener { textView, i, keyEvent ->
            if (i == EditorInfo.IME_ACTION_DONE) {
                if (binding.editTitle.text.isEmpty()) {
                    ViewUpdate(Type.ALL.code)
                } else {
                    PrefKey(this).putString("Search_Word", "${binding.editTitle.text}")

                    ViewUpdate(Type.SEARCH.code)
                    binding.editTitle.setText("")
                }
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.editTitle.windowToken, 0)

                fullScreenMode(PrefKey(this).getBoolean("fullScreen", false))
            }

            true
        }
    }

    fun reviewDialog(){
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

    fun setOnClickListener() {
        binding.apply {
            btnAll.setOnClickListener {
                ViewUpdate(Type.ALL.code)
            }
            btnUnread.setOnClickListener {
                ViewUpdate(Type.UNREAD.code)
            }

            btnBookmark.setOnClickListener {
                ViewUpdate(Type.BOOKMARK.code)
            }

            btnSetting.setOnClickListener {
                ViewUpdate(Type.SETTING.code)
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

            ll_cpn.setOnClickListener {
                val dialog = Dialog(this@TitleActivity)

                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setContentView(R.layout.popup)

                val btnYes = dialog.findViewById<Button>(R.id.btnYes)
                val btnNo = dialog.findViewById<Button>(R.id.btnNo)
                val title = dialog.findViewById<TextView>(R.id.title)

                if(PrefKey(this@TitleActivity).getBoolean("popupShow", true)) {
                    PrefKey(this@TitleActivity).putBoolean("popupShow", false)
                    title.text = resources.getText(R.string.해석권_설명)
                    btnNo.text = resources.getText(R.string.확인)
                    btnYes.visibility = View.GONE
                }

                btnNo.setOnClickListener {
                    dialog.dismiss()
                }

                btnYes.setOnClickListener {
                    showRewardedAd()
                    dialog.dismiss()
                }

                dialog.show()

            }
        }
    }

    //바텀 버튼 색상 초기화
    fun resetBottomSeat() {
        binding.apply {
            val bottomSeat = ContextCompat.getColor(this@TitleActivity, R.color.bottom_seat)
            btnAll.setTextColor(bottomSeat)
            btnUnread.setTextColor(bottomSeat)
            btnBookmark.setTextColor(bottomSeat)
            btnSetting.setTextColor(bottomSeat)
        }
    }

    //뷰에 텍스트 넣기
    fun ViewUpdate(tag: String): Boolean {
        if (System.currentTimeMillis() - time < 1000L) {
            return true
        }

        time = System.currentTimeMillis()

        resetBottomSeat()

        val viewTag = PrefKey(this).putString("Tag", tag)

        if (viewTag ==  Type.SETTING.code) {
            binding.btnSetting.setTextColor(ContextCompat.getColor(this, R.color.textColor))
            binding.RecycleView.visibility = View.GONE
            binding.llSetting.visibility = View.VISIBLE
        } else {
            binding.RecycleView.visibility = View.VISIBLE
            binding.llSetting.visibility = View.GONE
        }

        setViewSetting()

        return true
    }

    fun setViewSetting() {
        val title = resources.getStringArray(R.array.name)
        val viewTag = PrefKey(this).getString("Tag", "All")
        val numvalue = title.size

        CoroutineScope(Dispatchers.IO).launch {
            datas.apply {
                datas.clear()
                for (i in 0 until numvalue) {
                    when (viewTag) {
                        Type.ALL.code -> {
                            add(
                                AdapterData(
                                    title[i],
                                    PrefKey(this@TitleActivity).getBoolean(
                                        "li_boolean_${i}",
                                        false
                                    ),
                                    viewTag,
                                    i
                                )
                            )
                        }
                        Type.UNREAD.code -> {
                            if (!PrefKey(this@TitleActivity).getBoolean("li_boolean_${i}", false)) {
                                add(AdapterData(title[i], false, viewTag, i))
                            }
                        }
                        Type.BOOKMARK.code -> {
                            if (PrefKey(this@TitleActivity).getBoolean(
                                    "favor_boolean_${i}",
                                    false
                                )
                            ) {
                                add(AdapterData(title[i], false, viewTag, i))
                            }
                        }
                        Type.SEARCH.code -> {
                            if (title[i].contains(
                                    "${
                                        PrefKey(this@TitleActivity).getString(
                                            "Search_Word",
                                            ""
                                        )
                                    }"
                                )
                            ) {
                                add(AdapterData(title[i], false, viewTag, i))
                            }
                        }
                    }
                }
            }

            withContext(Dispatchers.Main) {
                when(viewTag) {
                    Type.ALL.code -> {
                        binding.btnAll.setTextColor(ContextCompat.getColor(this@TitleActivity, R.color.textColor))
                    }
                    Type.UNREAD.code -> {
                        binding.btnUnread.setTextColor(ContextCompat.getColor(this@TitleActivity, R.color.textColor))
                    }
                    Type.BOOKMARK.code -> {
                        binding.btnBookmark.setTextColor(ContextCompat.getColor(this@TitleActivity, R.color.textColor))
                    }
                    Type.SEARCH.code -> {
                        binding.btnAll.setTextColor(ContextCompat.getColor(this@TitleActivity, R.color.textColor))
                        titleTop?.text = PrefKey(this@TitleActivity).getString("Search_Word", "")
                    }
                }

                adapter.datas = datas
                adapter.notifyDataSetChanged()

                if(viewTag != Type.SETTING.code) {
                    //리사이클뷰 애니메이션 (페이드 인)
                    val recycleAnim =
                        AnimationUtils.loadAnimation(this@TitleActivity, R.anim.recycle_anim)
                    binding.RecycleView.visibility = View.INVISIBLE

                    Handler().postDelayed(Runnable {
                        binding.RecycleView.startAnimation(recycleAnim)
                        binding.RecycleView.visibility = View.VISIBLE
                    }, 200)

                }

                //리사이클뷰 포지션 변경
                if (viewTag == Type.ALL.code) {
                    binding.RecycleView.scrollToPosition(PrefKey(this@TitleActivity).getInt("number", 0))
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

    fun shewAd() {
        loadAd()
        loadRewardedAd()
    }

    //전체화면 광고
    fun loadRewardedAd(){
        val adRequest = AdRequest.Builder().build()
        RewardedAd.load(
            this, "ca-app-pub-8461307543970328/9609175369", adRequest, object : RewardedAdLoadCallback(){
                override fun onAdFailedToLoad(p0: LoadAdError) {
                    mRewardedAd = null
                }
                override fun onAdLoaded(p0: RewardedAd) {
                    mRewardedAd = p0
                }
            }
        )
    }

    fun showRewardedAd(){
        if (mRewardedAd != null){
            try {
                mRewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback(){
                    override fun onAdDismissedFullScreenContent() {
                        Log.d("TAG", "Ad was dismissed")
                        mRewardedAd = null
                        loadRewardedAd()
                    }

                    override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                        Log.d("TAG", "Ad failed to show.")
                    }

                    override fun onAdShowedFullScreenContent() {
                        Log.d("TAG", "Ad showed fullscreen content.")
                        mRewardedAd = null
                    }
                }

                mRewardedAd?.show(this, OnUserEarnedRewardListener() { rewardItem ->
                    val rewardAmount = rewardItem.amount + PrefKey(this).getInt("inter", 10)

                    PrefKey(this).putInt("inter", rewardAmount)

                    amount.text = PrefKey(this).getInt("inter", 10).toString()

                })
            } catch (e: Exception) {
                Toast.makeText(this, "광고를 불러오는 중에 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        } else {
            Log.d("TAG", "The rewarded ad was not loaded yet")
            Toast.makeText(this, "현재 광고가 준비되지 않았습니다.\n나중에 다시 해주세요.", Toast.LENGTH_SHORT).show()
            loadRewardedAd()
        }
    }

    //배너 광고
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

    //뒤로가기 버튼
    override fun onBackPressed() {
        if(System.currentTimeMillis() - time <= 2500) {
            finish()
        } else {
            Toast.makeText(this, "앱을 종료하기 위해 '뒤로' 버튼을 한번 더 눌러주세요.", Toast.LENGTH_SHORT).show()
            time = System.currentTimeMillis()
        }
    }


    override fun onRestart() {
        super.onRestart()

        val application = application as? MyApplication

        application?.showAdIfAvailable(
            this@TitleActivity,
            object : MyApplication.OnShowAdCompleteListener {
                override fun onShowAdComplete() {

                }
            })
    }

}