package com.horror.scarystory

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.*
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AdRequestService(
    private var fromActivity: AppCompatActivity
): Application() {

    companion object {
        var mRewardedAd: RewardedAd? = null
        var mInterstitialAd: InterstitialAd? = null
    }

    fun getRewardAd() {
        CoroutineScope(Dispatchers.Main).launch {
            while (true) {
                mInterstitialAd ?: interstitialAd()
                delay(1000 * 90)
                mRewardedAd ?: loadRewardedAd()
                delay(1000 * 90)
            }
        }
    }

    private fun loadRewardedAd(){
        val adRequest = AdRequest.Builder().build()
        RewardedAd.load(
            fromActivity, "ca-app-pub-8461307543970328/9609175369", adRequest, object : RewardedAdLoadCallback(){
                override fun onAdFailedToLoad(p0: LoadAdError) {
                    mRewardedAd = null
                    Logger.warring("FAILED", "$p0")
                }
                override fun onAdLoaded(p0: RewardedAd) {
                    mRewardedAd = p0
                    Logger.debug("SUCCESS", "$p0")
                }
            }
        )
    }

    fun showRewardedAd(){
        if (mRewardedAd != null){
            try {
                mRewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback(){
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

                mRewardedAd?.show(fromActivity, OnUserEarnedRewardListener() { rewardItem ->
                    PrefKey(fromActivity).putInt("inter", rewardItem.amount + PrefKey(fromActivity).getInt("inter", 10))
                })

            } catch (e: Exception) {
                Logger.debug("LOG", "The rewarded ad was not loaded yet")
                Toast.makeText(fromActivity, "광고를 불러오는 중에 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(fromActivity, "광고가 준비되지 않았습니다.\n나중에 다시 시도 해주세요.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun interstitialAd() {
        val adRequest = AdRequest.Builder().build()

        //광고 단가 높음
        InterstitialAd.load(fromActivity,
            "ca-app-pub-8461307543970328/8595808456",
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

//        //광고 단가 중간
//        InterstitialAd.load(fromActivity,
//            "ca-app-pub-8461307543970328/2685006225",
//            adRequest, object : InterstitialAdLoadCallback() {
//                override fun onAdFailedToLoad(adError: LoadAdError) {
//                    Log.d("광고", "낮음 단가")
//                    mInterstitialAd = null
//                }
//
//                override fun onAdLoaded(interstitialAd: InterstitialAd) {
//                    Log.d("광고", "중간 단가")
//                    mInterstitialAd = interstitialAd
//                }
//            })

//        //광고 단가 중간 끝
//        InterstitialAd.load(fromActivity,
//            "ca-app-pub-8461307543970328/9771239466",
//            adRequest, object : InterstitialAdLoadCallback() {
//                override fun onAdFailedToLoad(adError: LoadAdError) {
//                    Log.d("광고", "낮음 못받음")
//                    mInterstitialAd = null
//                }
//
//                override fun onAdLoaded(interstitialAd: InterstitialAd) {
//                    Log.d("광고", "낮은 단가")
//                    mInterstitialAd = interstitialAd
//                }
//            })

    }

    fun showInterstitialAd() {
        mInterstitialAd ?: return

        if (PrefKey(fromActivity).getInt("count", 0) >= 5) {
            mInterstitialAd!!.show(fromActivity)
            mInterstitialAd = null
            PrefKey(fromActivity).putInt("count", 0)
        }
    }
    
}