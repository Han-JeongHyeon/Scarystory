package com.horror.scarystory

import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.*
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

class AdRequestService(
    private var thisActivity: AppCompatActivity
) {
    var mRewardedAd: RewardedAd? = null

    fun loadRewardedAd(){
        val adRequest = AdRequest.Builder().build()
        RewardedAd.load(
            thisActivity, "ca-app-pub-8461307543970328/9609175369", adRequest, object : RewardedAdLoadCallback(){
                override fun onAdFailedToLoad(p0: LoadAdError) {
                    mRewardedAd = null
                    Log.d("LOG_D", "$p0")
                }
                override fun onAdLoaded(p0: RewardedAd) {
                    mRewardedAd = p0
                }
            }
        )

        Log.d("LOG_D", "$mRewardedAd")
    }

    fun showRewardedAd(): String{
        var rewardAmount = 0

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

                mRewardedAd?.show(thisActivity, OnUserEarnedRewardListener() { rewardItem ->
                    rewardAmount = rewardItem.amount + PrefKey(thisActivity).getInt("inter", 10)

                    PrefKey(thisActivity).putInt("inter", rewardAmount)

//                  amount.text = PrefKey(thisActivity).getInt("inter", 10).toString()

                })
            } catch (e: Exception) {
                Toast.makeText(thisActivity, "광고를 불러오는 중에 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        } else {
            Log.d("TAG", "The rewarded ad was not loaded yet")
            Toast.makeText(thisActivity, "현재 광고가 준비되지 않았습니다.\n나중에 다시 해주세요.", Toast.LENGTH_SHORT).show()
            loadRewardedAd()
        }

        return "$rewardAmount"
    }

    fun getReward(): RewardedAd? {
        return mRewardedAd
    }

}