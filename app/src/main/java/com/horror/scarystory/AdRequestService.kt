package com.horror.scarystory

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.*
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ticker

private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

class AdRequestService private constructor(private val applicationContext: Context) {
    private val coroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    companion object {
        @Volatile
        private var INSTANCE: AdRequestService? = null
        private var showActivity: ComponentActivity? = null
        var mRewardedAd: RewardedAd? = null
        var mInterstitialAd: InterstitialAd? = null

        fun initialize(activity: ComponentActivity) {
            INSTANCE = AdRequestService(activity.applicationContext)
            showActivity = activity
        }

        fun getInstance(): AdRequestService {
            return INSTANCE ?: throw IllegalStateException("AdRequestService must be initialized")
        }
    }

    fun startAdLoading() {
        coroutineScope.launch {
            while (true) {
                if (mInterstitialAd == null) {
                    loadInterstitialAd()
                }
                delay(1000 * 90)
                if (mRewardedAd == null) {
                    loadRewardedAd()
                }
                delay(1000 * 90)
            }
        }
    }

    private fun loadRewardedAd() {
        val adRequest = AdRequest.Builder().build()
        RewardedAd.load(
            applicationContext, BuildConfig.AD_REWORD_ID, adRequest, object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(p0: LoadAdError) {
                    mRewardedAd = null
//                    Logger.warning("FAILED", "$p0")
                }

                override fun onAdLoaded(p0: RewardedAd) {
                    mRewardedAd = p0
                    Logger.debug("SUCCESS", "$p0")
                }
            }
        )
    }

    fun showRewardedAd() {
        if (mRewardedAd != null) {
            try {
                mRewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        Logger.debug("Dismissed", "Ad was dismissed")
                        mRewardedAd = null
                    }

                    override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                        Logger.debug("Failed", "Ad failed to show.")
                    }

                    override fun onAdShowedFullScreenContent() {
                        Logger.debug("Showed", "Ad showed fullscreen content.")
                        mRewardedAd = null
                    }
                }

                //@TODO DB 추가 후 수정
//                mRewardedAd?.show(activity, OnUserEarnedRewardListener { rewardItem ->
//                    PrefKey(activity).putInt("inter", rewardItem.amount + PrefKey(activity).getInt("inter", 10))
//                })

            } catch (e: Exception) {
                Logger.debug("LOG", "The rewarded ad was not loaded yet")
                Toast.makeText(showActivity, "광고를 불러오는 중에 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(showActivity, "광고가 준비되지 않았습니다.\n나중에 다시 시도 해주세요.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadInterstitialAd() {
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(applicationContext,
            BuildConfig.AD_FULL_HIGH_ID,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Logger.debug("광고", "광고 준비 X")
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    Logger.debug("광고", "광고 준비 O")
                    mInterstitialAd = interstitialAd
                }
            })
    }

    fun showInterstitialAd() {
        if (mInterstitialAd != null && PrefKey(applicationContext).getInt("count", 0) >= 5 && showActivity != null) {
            mInterstitialAd!!.show(showActivity!!)
            mInterstitialAd = null
            PrefKey(applicationContext).putInt("count", 0)
        }
    }

}

fun main() {
    val tickerChannel = ticker(delayMillis = 1000, initialDelayMillis = 0)
    coroutineScope.launch {
        for (event in tickerChannel) {
            println("AAAA")
        }
    }
}