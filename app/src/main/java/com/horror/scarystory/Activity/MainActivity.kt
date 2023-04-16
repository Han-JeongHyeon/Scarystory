package com.horror.scarystory.Activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.horror.scarystory.MyApplication
import com.horror.scarystory.PrefKey
import com.horror.scarystory.R
import com.horror.scarystory.databinding.ActivityMainBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    var timerTime = 3000L

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val application = application as? MyApplication

        val title = resources.getStringArray(R.array.name)

        if (PrefKey(this).getBoolean("firstOpen", true)){
            for (i in title.indices){
                if (PrefKey(this).getBoolean("li_boolean_${i}", false)) {
                    PrefKey(this).putBoolean("interClick_$i", true)
                }
            }
            PrefKey(this).putBoolean("firstOpen", false)
        }

//        val formatter = DateTimeFormatter.ISO_DATE
//
//        Log.d("TAG", formatter.toString())
//
//        if (PrefKey(this).getString("date", formatter.toString()) != formatter.toString()) {
//            PrefKey(this).putString("date", formatter.toString())
//
//            when (PrefKey(this).getInt("inter", 10)) {
//                in 0..9 -> {
//                    PrefKey(this).putInt("inter", 10)
//                }
//            }
//        }

        Handler().postDelayed(
            Runnable {
                application?.showAdIfAvailable(
                    this@MainActivity,
                    object : MyApplication.OnShowAdCompleteListener {
                        override fun onShowAdComplete() {
                            val intent = Intent(this@MainActivity, TitleActivity::class.java)
                            startActivity(intent)
                            finish()

                            overridePendingTransition(R.anim.activity_up, R.anim.activity_up_sub)
                        }
                    })
            }, timerTime
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
