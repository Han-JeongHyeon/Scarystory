package com.horror.scarystory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.horror.scarystory.Activity.TitleActivity

class ChangeIntent (
    thisActivity: AppCompatActivity,
    intentActivity: Class<TitleActivity>,
    animation: String?
): AppCompatActivity() {

    init {
        val intent = Intent(thisActivity, intentActivity)
        thisActivity.startActivity(intent)
        finish()

        if(animation == "main")
        //메인 애니메이션
            overridePendingTransition(R.anim.activity_up, R.anim.activity_up_sub)
        else
        //스토리 애니메이션
            overridePendingTransition(R.anim.activity_down_sub, R.anim.activity_down)

    }

}