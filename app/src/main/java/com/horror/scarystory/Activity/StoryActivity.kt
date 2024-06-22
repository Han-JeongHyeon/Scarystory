//package com.horror.scarystory.activity
//
//import android.annotation.SuppressLint
//import android.content.Intent
//import android.content.SharedPreferences
//import android.graphics.Color
//import android.graphics.Typeface
//import android.os.Build
//import android.os.Bundle
//import android.os.Handler
//import android.view.Menu
//import android.view.MenuItem
//import android.view.View
//import android.view.animation.AnimationUtils
//import android.widget.Button
//import android.widget.SeekBar
//import android.widget.SeekBar.OnSeekBarChangeListener
//import android.widget.TextView
//import android.widget.Toast
//import androidx.appcompat.widget.Toolbar
//import com.google.android.gms.ads.*
//import com.horror.scarystory.*
//import com.horror.scarystory.R
//import com.horror.scarystory.Story.*
//import com.horror.scarystory.databinding.ActivityStoryBinding
//import java.lang.Exception
//
//class StoryActivity : BaseActivity<ActivityStoryBinding>({ ActivityStoryBinding.inflate(it) }) {
//
//    var Tool_bar: Toolbar? = null
//
//    var seek: SharedPreferences? = null
//    var font: SharedPreferences? = null
//    var Bookmark: Button? = null
//
//    var Title: Array<String> = listOf<String>().toTypedArray()
//
//    companion object {
//        var position = 0
//        var tag = ""
//        var title = ""
//    }
//
//    fun setStory() {
//        val TitleBar = findViewById<TextView>(R.id.top_title)
//
//        val value: String = when (position / 100) {
//            0 -> story0.getStory(position)
//            1 -> story1.getStory(position % 100)
//            2 -> story2.getStory(position % 200)
//            else -> {
//                "null"
//            }
//        }
//
//        binding.story.text = value.substring(0, value.indexOf("@"))
//        val text = if(value.substring(value.indexOf("@") + 1) == "") {
//            "이 이야기는 해석이 없습니다."
//        } else {
//            value.substring(value.indexOf("@") + 1)
//        }
//
//        binding.interText.text = text
//        title = Title[position]
//
//        binding.title.text = title
//        TitleBar.text = "${position + 1}. $title"
//
//        //포지션 값 저장
//        PrefKey(this).putInt("number", position)
//
//        //쉐어드로 값 변경
//        PrefKey(this).putBoolean("li_boolean_$position", true)
//
//        //들어올때 표시
//        when (PrefKey(this).getBoolean("favor_boolean_$position", false)) {
//            true -> {
//                Bookmark?.setBackgroundResource(R.drawable.bookmark_btn)
//            }
//            false -> {
//                Bookmark?.setBackgroundResource(R.drawable.bookmark_border_btn)
//            }
//        }
//
////        getBtn(Type.HOME.code)
//
//        //폰트 사이즈 변경
//        getSeek(PrefKey(this).getInt("seek", 22))
//
//        //폰트 가져오기
//        getFont(PrefKey(this).getInt("font", 1))
//    }
//
//
//    @SuppressLint("SetTextI18n")
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        Bookmark = findViewById(R.id.bookmark)
//        Title = resources.getStringArray(R.array.name)
//        tag = PrefKey(this).getString("Tag", "All")!!
//        val backBtn = findViewById<Button>(R.id.beck_btn)
//
//        //전체화면 호출
//        fullScreenMode(PrefKey(this).getBoolean("fullScreen", false))
//
//        position = intent.getIntExtra("position", -1)
//        title = Title[position]
//
//        setStory()
//
//        //툴바
//        Tool_bar = findViewById(R.id.Story_bar)
//        setSupportActionBar(Tool_bar)
//
//        //백 버튼
//        backBtn.setOnClickListener {
//            fromToIntent(this@StoryActivity, TitleActivity())
////            showFullScreenAd()
//        }
//
//        //북마크 표시
//        Bookmark!!.setOnClickListener {
//            when (PrefKey(this).getBoolean("favor_boolean_$position", false)) {
//                true -> {
//                    Bookmark?.setBackgroundResource(R.drawable.bookmark_border_btn)
//                    PrefKey(this).putBoolean("favor_boolean_$position", false)
//                }
//                false -> {
//                    Bookmark?.setBackgroundResource(R.drawable.bookmark_btn)
//                    PrefKey(this).putBoolean("favor_boolean_$position", true)
//                }
//            }
//        }
//
//        //리스트 화면을 터치하면
//        binding.Layout.setOnClickListener {
////            getBtn(Type.OPTION.code)
//        }
//
//        //스크롤 화면을 터치하면
//        binding.scroll.setOnClickListener {
////            getBtn(Type.OPTION.code)
//        }
//
//        //스크롤을 내리거나 올리면
//        binding.scroll.setOnScrollChangeListener { view, i, i2, i3, i4 ->
////            if (Tool_bar?.visibility == View.VISIBLE) {
////                getBtn(Type.OPTION.code)
////            }
////            if (!view.canScrollVertically(1) || !view.canScrollVertically(-1)) {
////                getBtn(Type.OPTION.code)
////            }
//        }
//
//        binding.seekbar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
//            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
//                getSeek(seekBar.progress)
//            }
//
//            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
//            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
//        })
//
//        //에딧으로 폰트 사이즈 변경
////        binding.checkSeek.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
////            if (checkSeek.text.toString().toInt() in 1..40) {
////                getSeek(checkSeek.text.toString().toInt())
////            } else {
////                Toast.makeText(this@StoryActivity, "입력할 수 있는 값은 10부터 40까지 입니다.", Toast.LENGTH_SHORT)
////                    .show()
////                checkSeek.setText(seek?.getInt("seek", 22).toString() + "")
////            }
////            true
////        })
//
//        binding.font1.setOnClickListener {
//            getFont(1)
//        }
//        binding.font2.setOnClickListener {
//            getFont(2)
//        }
//        binding.font3.setOnClickListener {
//            getFont(3)
//        }
//        binding.font4.setOnClickListener {
//            getFont(4)
//        }
//
//        binding.InterBtn.setOnClickListener {
//            var interCount = PrefKey(this).getInt("inter", 10)
//            if (PrefKey(this).getBoolean("interClick_$position", false)
//                || interCount > 0) {
//
//                if (!PrefKey(this).getBoolean("interClick_$position", false) && interCount > 0) {
//                    Toast.makeText(this, "해석권 하나를 사용했습니다.", Toast.LENGTH_SHORT).show()
//                    PrefKey(this).putInt("inter", interCount - 1)
//                    PrefKey(this).putBoolean("interClick_$position", true)
//                }
//
//                if (binding.interLayout.visibility == View.VISIBLE) {
//                    binding.interLayout.invisible()
//                    Handler().postDelayed(Runnable {
//                        binding.InterBtn.show()
//                    }, 700)
//                    binding.interLayout.startAnimation(
//                        AnimationUtils.loadAnimation(
//                            applicationContext,
//                            R.anim.inter_down
//                        )
//                    )
//                } else {
//                    binding.interLayout.show()
//                    binding.InterBtn.invisible()
//                    binding.interLayout.startAnimation(
//                        AnimationUtils.loadAnimation(
//                            applicationContext,
//                            R.anim.inter_up
//                        )
//                    )
//                }
//            } else {
//                Toast.makeText(this, "해석권이 부족합니다.", Toast.LENGTH_SHORT).show()
//            }
//        }
//
//        //해석 버튼
//        binding.interBtn.setOnClickListener {
//            if (binding.interLayout.visibility == View.VISIBLE) {
//                binding.interLayout.invisible()
//                Handler().postDelayed(Runnable {
//                    binding.InterBtn.show()
//                }, 700)
//                binding.interLayout.startAnimation(
//                    AnimationUtils.loadAnimation(
//                        applicationContext,
//                        R.anim.inter_down
//                    )
//                )
//            } else {
//                binding.interLayout.show()
//                binding.InterBtn.invisible()
//                binding.interLayout.startAnimation(
//                    AnimationUtils.loadAnimation(
//                        applicationContext,
//                        R.anim.inter_up
//                    )
//                )
//            }
//        }
//
//    }
//
//    //폰트 설정
//    fun getFont(Value: Int) {
//        val On_Stroke = R.drawable.stroke_btn_w
//        val Off_Stroke = R.drawable.stroke_btn
//        val White = "#FFFFFF"
//        val Gray = "#7A7A7A"
//
//        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O) {
////            if (type == "btn") {
//                Toast.makeText(this, "현재 기기는 폰트 변경이 불가능합니다.", Toast.LENGTH_SHORT).show()
////            } else {
//                binding.story.typeface = Typeface.createFromAsset(assets, "font/yoondokrip.ttf")
////            }
//            return
//        }
//
//        binding.font1.setBackgroundResource(if (Value == 1) On_Stroke else Off_Stroke)
//        binding.font1.setTextColor(Color.parseColor(if (Value == 1) White else Gray))
//        if (Value == 1) {
//            binding.story.typeface = Typeface.createFromAsset(assets, "font/yoondokrip.ttf")
//        }
//
//        binding.font2.setBackgroundResource(if (Value == 2) On_Stroke else Off_Stroke)
//        binding.font2.setTextColor(Color.parseColor(if (Value == 2) White else Gray))
//        if (Value == 2) {
//            binding.story.typeface = Typeface.createFromAsset(assets, "font/bm.ttf")
//        }
//
//        binding.font3.setBackgroundResource(if (Value == 3) On_Stroke else Off_Stroke)
//        binding.font3.setTextColor(Color.parseColor(if (Value == 3) White else Gray))
//        if (Value == 3) {
//            binding.story.typeface = Typeface.createFromAsset(assets, "font/bitrofri.ttf")
//        }
//
//        binding.font4.setBackgroundResource(if (Value == 4) On_Stroke else Off_Stroke)
//        binding.font4.setTextColor(Color.parseColor(if (Value == 4) White else Gray))
//        if (Value == 4) {
//            binding.story.typeface = Typeface.createFromAsset(assets, "font/nanum.ttf")
//        }
//
//        PrefKey(this).putInt("font", Value)
//    }
//
//    //메뉴
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.font -> {
//                binding.textOption.show()
//                binding.InterBtn.invisible()
//                binding.InterBtn.invisible()
//                binding.interLayout.invisible()
//            }
//            R.id.Sharing -> {
//                val Sharing_intent = Intent().apply {
//                    action = Intent.ACTION_SEND
//                    putExtra(
//                        Intent.EXTRA_TEXT,
//                        "$title \n\n ${binding.story.text} \n\n <해석> \n ${binding.interText.text} \n\n 더 많은 이무이를 보고싶다면 play.google.com/store/apps/details?id=com.horror.scarystory"
//                    )
//                    type = "text/plain"
//                }
//                startActivity(Intent.createChooser(Sharing_intent, null))
//            }
//            R.id.Update -> {
//                sendMail("[무서운 이야기 업데이트 문의]", "원하는 업데이트")
//            }
//            R.id.Error -> {
//                sendMail("[무서운 이야기 오류 문의]", "오류 설명")
//            }
//        }
//        return super.onOptionsItemSelected(item)
//    }
//
//    fun sendMail(title: String, content: String) {
//        try {
//            val email = Intent(Intent.ACTION_SEND)
//            email.type = "plain/text"
//            email.setPackage("com.google.android.gm")
//            val address = arrayOf("jhsoft04@gmail.com")
//            email.putExtra(Intent.EXTRA_EMAIL, address)
//            email.putExtra(Intent.EXTRA_SUBJECT, title)
//            email.putExtra(
//                Intent.EXTRA_TEXT,
//                "앱 버전 (AppVersion): ${
//                    applicationContext.packageManager.getPackageInfo(
//                        applicationContext.packageName,
//                        0
//                    ).versionName
//                }\n" +
//                        "기기명 (Device): ${Build.MODEL}\n" +
//                        "안드로이드 OS버전 (Android OS Ver): ${Build.VERSION.RELEASE}\n\n" +
//                        "$content : \n\n\n"
//            )
//            startActivity(email)
//        } catch (e: Exception) {
//            Toast.makeText(
//                applicationContext,
//                "Gmail앱이 활성화 되어 있지 않아 실행할 수 없습니다.",
//                Toast.LENGTH_LONG
//            ).show()
//        }
//    }
//
//
//    //글자 크기
//    fun getSeek(Value: Int?) {
//
//        binding.seekbar.progress = Value!!
//        binding.story.textSize = Value!!.toFloat()
//        binding.checkSeek.setText(Value!!.toString() + "")
//
//        PrefKey(this).putInt("seek", Value)
//
//    }
//
//    //광고
////    private fun showFullScreenAd(){
////        AdRequestService(this@StoryActivity).showInterstitialAd()
////    }
//
//    //메뉴 보이게 함
//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.story_menu, menu)
//        return true
//    }
//
//    override fun onBackPressed() {
//        super.onBackPressed()
//        fromToIntent(this@StoryActivity, TitleActivity())
////        showFullScreenAd()
//    }
//
//    //전체화면
//    private fun fullScreenMode(switch: Boolean) {
//        var uiOption = window.decorView.systemUiVisibility
//        if (switch) {
//            uiOption = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
//                    View.SYSTEM_UI_FLAG_FULLSCREEN or
//                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//        } else {
//            uiOption = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//        }
//        window.decorView.setSystemUiVisibility(uiOption)
//    }
//
////    override fun onRestart() {
////        super.onRestart()
////
////        Log.d("TAG", "onRestart")
////        val application = application as? MyApplication
////
////        application?.showAdIfAvailable(
////            this@StoryActivity,
////            object : MyApplication.OnShowAdCompleteListener {
////                override fun onShowAdComplete() {
////                }
////            })
////    }
//}