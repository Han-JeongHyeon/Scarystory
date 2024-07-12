package com.horror.scarystory

import android.app.Application
import android.content.Context
import android.util.Log
import com.horror.scarystory.Toast.Companion.showToast
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

class AdRequestService private constructor(private val applicationContext: Context) {

    companion object {
        @Volatile
        private var INSTANCE: AdRequestService? = null
        private var showActivity: ComponentActivity? = null
        private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
        private const val defaultDelay = 1000L

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
                loadInterstitialAd()
                delay(defaultDelay * 20)
                loadRewardedAd()
                delay(defaultDelay * 20)
            }
        }
    }

    private fun loadRewardedAd() {
        if (mRewardedAd == null) return

        val adRequest = AdRequest.Builder().build()
        RewardedAd.load(
            applicationContext, BuildConfig.AD_REWORD_ID, adRequest, object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(p0: LoadAdError) {
                    mRewardedAd = null
                }

                override fun onAdLoaded(p0: RewardedAd) {
                    mRewardedAd = p0
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
                showToast("광고를 불러오는 중에 오류가 발생했습니다.", Toast.LENGTH_SHORT)
            }
        } else {
            showToast("광고가 준비되지 않았습니다.\n나중에 다시 시도 해주세요.", Toast.LENGTH_SHORT)
        }
    }

    private fun loadInterstitialAd() {
        if (mInterstitialAd == null) return

        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(applicationContext,
            BuildConfig.AD_FULL_HIGH_ID,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
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