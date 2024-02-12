package com.horror.scarystory

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.horror.scarystory.Activity.MainActivity
import com.horror.scarystory.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_story.*
import kotlinx.android.synthetic.main.activity_title.*
import kotlinx.android.synthetic.main.activity_title.adView

abstract class BaseActivity<T: ViewBinding>(
    val bindingFactory: (LayoutInflater) -> T
): AppCompatActivity() {

    private var _binding: T? = null
    val binding get() = _binding!!

    //뒤로가기 시간체크
    companion object {
        var backTime = 0L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingFactory(layoutInflater)
        setContentView(binding.root)

        if (!_binding.toString().contains("Main")) {
            loadAd()
        }

        if (_binding.toString().contains("Title")) {
            setOnClick()
        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()

    }

//    override fun onPause() {
//        super.onPause()
//
//    }

    override fun onStop() {
        super.onStop()
    }

    override fun onRestart() {
        super.onRestart()

        val application = application as? MyApplication

        application?.showAdIfAvailable(
            this,
            object : MyApplication.OnShowAdCompleteListener {
                override fun onShowAdComplete() {

                }
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onBackPressed() {
        if(System.currentTimeMillis() - backTime <= 2500) {
            MyApplication().release()
            finish()
        } else {
            Toast.makeText(this, "앱을 종료하기 위해 '뒤로' 버튼을 한번 더 눌러주세요.", Toast.LENGTH_SHORT).show()
            backTime = System.currentTimeMillis()
        }
    }

    fun View.show() {
        this.visibility = View.VISIBLE
    }

    fun View.hide() {
        this.visibility = View.GONE
    }

    fun View.invisible() {
        this.visibility = View.INVISIBLE
    }

    //클릭 리스터
    private fun setOnClick() {
        _binding.apply {
            //문의하기 기능
            update.setOnClickListener {
                sendMail("[이해하면 무서운 이야기 업데이트 문의]", "원하는 업데이트")
            }

            error.setOnClickListener {
                sendMail("[이해하면 무서운 이야기 오류 문의]", "오류 설명")
            }
        }
    }

    private fun sendMail(title: String, content: String) {
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

    //배너 광고
    private fun loadAd() {
        MobileAds.initialize(this) {}

        val adRequest = AdRequest.Builder().build()

        _binding.apply {
            adView.loadAd(adRequest)

            //배너 광고 이벤트
            adView.adListener = object : AdListener() {
                override fun onAdClicked() {}
                override fun onAdClosed() {}
                override fun onAdFailedToLoad(adError: LoadAdError) {}
                override fun onAdImpression() {}
                override fun onAdLoaded() {}
            }

        }
    }

    fun fromToIntent(fromActivity: AppCompatActivity, toActivity: AppCompatActivity) {
        val intent = Intent(fromActivity, toActivity::class.java)
        fromActivity.apply {
            startActivity(intent)
            finish()

            if(fromActivity == MainActivity())
            //메인 애니메이션
                overridePendingTransition(R.anim.activity_up, R.anim.activity_up_sub)
            else
            //스토리 애니메이션
                overridePendingTransition(R.anim.activity_down_sub, R.anim.activity_down)
        }
    }

}
