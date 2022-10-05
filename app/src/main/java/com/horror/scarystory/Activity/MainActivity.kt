package com.horror.scarystory.Activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.horror.scarystory.R
import com.horror.scarystory.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //전체화면 호출
        val fullScreen = getSharedPreferences("fullScreen", Context.MODE_PRIVATE)
        fullScreenMode(fullScreen.getBoolean("fullScreen", false))

        //메인에서 타이틀로 화면 변경
        binding.btn.setOnClickListener{
            val intent = Intent(this@MainActivity, TitleActivity::class.java)
            intent.putExtra("name","All")
            startActivity(intent)
            finish()

            overridePendingTransition(R.anim.activity_up, R.anim.activity_up_sub)
        }

    }

    //전체화면
    private fun fullScreenMode(switch : Boolean){
        var uiOption = window.decorView.systemUiVisibility
        if(switch){
            uiOption =  View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY

        }else {
            uiOption =  View.SYSTEM_UI_LAYOUT_FLAGS
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        }
        window.decorView.setSystemUiVisibility(uiOption)
    }

    override fun onBackPressed() {
        finish()
    }
}
