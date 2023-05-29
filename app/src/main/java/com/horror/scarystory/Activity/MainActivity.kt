package com.horror.scarystory.Activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.horror.scarystory.*
import com.horror.scarystory.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val application = application as? MyApplication

//        Handler().postDelayed(
//            Runnable {
//                ChangeIntent(this@MainActivity, TitleActivity::class.java, "main")
//            }, 3600L
//        )

        Handler().postDelayed(
            Runnable {
                application?.showAdIfAvailable(
                    this@MainActivity,
                    object : MyApplication.OnShowAdCompleteListener {
                        override fun onShowAdComplete() {
                            ChangeIntent(this@MainActivity, TitleActivity::class.java, "main")
                        }
                    })
            }, Time.TimerTime
        )

        getFCMToken()
    }

    private fun getFCMToken(): String?{
        var token: String? = null
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("TAG", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            token = task.result

            // Log and toast
            Log.d("TAG", "FCM Token is ${token}")
        })

        return token
    }
}
