package com.horror.scarystory.Activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import com.google.android.gms.ads.*
import com.google.android.gms.ads.initialization.InitializationStatus
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.horror.scarystory.Adapter.AdapterData
import com.horror.scarystory.R
import com.horror.scarystory.Story.*
import com.horror.scarystory.databinding.ActivityStoryBinding
import kotlinx.android.synthetic.main.activity_story.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.DataInputStream
import java.lang.Exception

class StoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStoryBinding

    var title = ""
    var Position = 0
    var Tag = ""

    var Tool_bar: Toolbar? = null

    var seek: SharedPreferences? = null
    var font: SharedPreferences? = null

    private var mInterstitialAd: InterstitialAd? = null

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val TitleBar = findViewById<TextView>(R.id.top_title)
        val Back_btn = findViewById<Button>(R.id.beck_btn)
        val Bookmark = findViewById<Button>(R.id.bookmark)

        val Delete = findViewById<Button>(R.id.delete)
        val Edit = findViewById<Button>(R.id.edit)
        val Fake = findViewById<Button>(R.id.fake)

        //리스트 이름 배열
        val Title = resources.getStringArray(R.array.name)

        //전체화면 호출
        val fullScreen = getSharedPreferences("fullScreen", Context.MODE_PRIVATE)
        fullScreenMode(fullScreen.getBoolean("fullScreen", false))

        MobileAds.initialize(this) {}

        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)

        //배너 광고 이벤트
        binding.adView.adListener = object : AdListener() {
            override fun onAdClicked() {}

            override fun onAdClosed() {}

            override fun onAdFailedToLoad(adError: LoadAdError) {}

            override fun onAdImpression() {}

            override fun onAdLoaded() {}
        }

        setupInterstitialAd()

        //전면 광고
        val Advertising_count = application.getSharedPreferences("count", 0)
        var Advertising = Advertising_count.getInt("count", 0)

        val count = Advertising_count.edit()
        count.putInt("count", Advertising + 1)
        count.apply()

        if(Advertising >= 5){
            //광고 단가 높음
            InterstitialAd.load(this@StoryActivity,
                "ca-app-pub-8461307543970328/8595808456",
                adRequest,
                object : InterstitialAdLoadCallback() {
                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        Log.d("광고", "중간 시작")
                        //광고 단가 높음 끝
                        //광고 단가 중간
                        InterstitialAd.load(this@StoryActivity,
                            "ca-app-pub-8461307543970328/2685006225",
                            adRequest, object : InterstitialAdLoadCallback() {
                                override fun onAdFailedToLoad(adError: LoadAdError) {
                                    Log.d("광고", "낮음 단가")
                                    //광고 단가 중간 끝
                                    InterstitialAd.load(this@StoryActivity,
                                        "ca-app-pub-8461307543970328/9771239466",
                                        adRequest, object : InterstitialAdLoadCallback() {
                                            override fun onAdFailedToLoad(adError: LoadAdError) {
                                                Log.d("광고", "낮음 못받음")
                                                mInterstitialAd = null
                                            }

                                            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                                                Log.d("광고", "낮은 단가")
                                                mInterstitialAd = interstitialAd
                                            }
                                        })
                                }

                                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                                    Log.d("광고", "중간 단가")
                                    mInterstitialAd = interstitialAd
                                }
                            })
                    }

                    override fun onAdLoaded(interstitialAd: InterstitialAd) {
                        Log.d("광고", "높은 단가")
                        mInterstitialAd = interstitialAd

                    }
                })
            count.putInt("count", 0)
            count.apply()
        }

        //이야기 내용
        var value = ""

        //이야기 데이터 받기
        val data = intent.getParcelableExtra<AdapterData>("All")

        if (data != null) {
            title = data!!.Title
            Position = data!!.position
            Tag = data!!.tag

            val da = getSharedPreferences("data", 0)
            val d_a = da.edit()
            d_a.putString("data", "true")
            d_a.apply()

        } else {
            Position = intent.getIntExtra("position", -1)
            Tag = intent.getStringExtra("tag").toString()
//            if(Tag == "Watch"){
//                var input = openFileInput("$Position.txt")
//                var dis = DataInputStream(input)
//
//                var valueUTF = dis.readUTF()     //문자형 type
//                dis.close() //종료
//
//                title = valueUTF.substring(0, valueUTF.indexOf("/"))
//            }
//            else{
                title = Title[Position]
//            }
        }

//        Log.d("TAG", "$Tag")

        //툴바
        Tool_bar = findViewById<Toolbar>(R.id.Story_bar)
        setSupportActionBar(Tool_bar)

//        //타이틀 변경
//        if(Tag.equals("Watch")){
//            Delete.visibility = View.VISIBLE
//            Edit.visibility = View.VISIBLE
//            Fake.visibility = View.VISIBLE
//
//            Bookmark.visibility = View.GONE
//
//            binding.title.text = "$title"
//            TitleBar.text = "$title"
//
//            var input = openFileInput("$Position.txt")
//            var dis = DataInputStream(input)
//
//            var valueUTF = dis.readUTF()     //문자형 type
//            dis.close() //종료
//
//            value = valueUTF.substring(valueUTF.indexOf("/")+1)
//        }
//        else{
//            Delete.visibility = View.GONE
//            Edit.visibility = View.GONE
//            Fake.visibility = View.GONE
//
//            Bookmark.visibility = View.VISIBLE

            //포지션 값 저장
            val list_num = getSharedPreferences("number",0)
            val list_num_ = list_num.edit()
            list_num_.putInt("number", Position)
            list_num_.apply()

            //쉐어드로 값 변경
            val liboolean = getSharedPreferences("li_boolean_$Position", 0)
            val li_boolean = liboolean.edit()
            li_boolean.putBoolean("li_boolean_$Position", true)
            li_boolean.apply()

            binding.title.text = title
            TitleBar.text = "${Position + 1}. $title"

            //이야기 가져오기
            when (Position / 100) {

                0 -> {
                    value = story0.getStory(Position)
                }
                1 -> {
                    value = story1.getStory(Position % 100)
                }
                2 -> {
                    value = story2.getStory(Position % 200)
                }
                3 -> {
                    value = story3.getStory(Position % 300)
                }
                4 -> {
                    value = story4.getStory(Position % 400)
                }
                5 -> {
                    value = story5.getStory(Position % 500)
                }
            }

//        }

        binding.story.text = value.substring(0, value.indexOf("@"));
        binding.interText.text = value.substring(value.indexOf("@")+1)

        //백 버튼
        Back_btn.setOnClickListener {
            val intent = Intent(this, TitleActivity::class.java)
            intent.putExtra("name", Tag)
            startActivity(intent)

            finish()

            if (mInterstitialAd != null) {
                mInterstitialAd?.show(this@StoryActivity)
            }

            overridePendingTransition(R.anim.activity_down_sub, R.anim.activity_down)
        }

        //들어올때 표시
        val favorboolean = getSharedPreferences("favor_boolean_$Position", Context.MODE_PRIVATE)
        when (favorboolean.getBoolean("favor_boolean_$Position", false)) {
            true -> {
                Bookmark.setBackgroundResource(R.drawable.bookmark_btn)
            }
            false -> {
                Bookmark.setBackgroundResource(R.drawable.bookmark_border_btn)
            }
        }

        //북마크 표시
        Bookmark.setOnClickListener {
            when (favorboolean.getBoolean("favor_boolean_$Position", false)) {
                true -> {
                    Bookmark.setBackgroundResource(R.drawable.bookmark_border_btn)
                    val favor_boolean = favorboolean.edit()
                    favor_boolean.putBoolean("favor_boolean_$Position", false)
                    favor_boolean.apply()
                }
                false -> {
                    Bookmark.setBackgroundResource(R.drawable.bookmark_btn)
                    val favor_boolean = favorboolean.edit()
                    favor_boolean.putBoolean("favor_boolean_$Position", true)
                    favor_boolean.apply()
                }
            }
        }

        //다음 이야기로 이동
        binding.leftBtn.setOnClickListener {
            getBtn("Left", Tag)
        }

        binding.rightBtn.setOnClickListener {
            getBtn("Right", Tag)
        }

        getBtn("Home", Tag)

        //리스트 화면을 터치하면
        binding.Layout.setOnClickListener {
            getBtn("Option",Tag)
        }

        //스크롤 화면을 터치하면
        binding.scroll.setOnClickListener {
            getBtn("Option",Tag)
        }

        //스크롤을 내리거나 올리면
        binding.scroll.setOnScrollChangeListener { view, i, i2, i3, i4 ->
            if (Tool_bar?.visibility == View.VISIBLE){
                getBtn("Option",Tag)
            }
            if (!view.canScrollVertically(1) || !view.canScrollVertically(-1)){
                getBtn("Option",Tag)
            }
        }

        //폰트 사이즈 변경
        seek = getSharedPreferences("seek", Context.MODE_PRIVATE)
        getSeek(seek?.getInt("seek",22))

        binding.seekbar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                getSeek(seekBar.progress)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        //에딧으로 폰트 사이즈 변경
        binding.checkSeek.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (checkSeek.text.toString().toInt() in 1..40){
                getSeek(checkSeek.text.toString().toInt())
            }
            else{
                Toast.makeText(this@StoryActivity, "입력할 수 있는 값은 10부터 40까지 입니다.", Toast.LENGTH_SHORT).show()
                checkSeek.setText(seek?.getInt("seek", 22).toString() + "")
            }
            true
        })

        //폰트 가져오기
        font = getSharedPreferences("font", Context.MODE_PRIVATE)
        getFont(font?.getInt("font",1))

        binding.font1.setOnClickListener {
            getFont(1)
        }
        binding.font2.setOnClickListener {
            getFont(2)
        }
        binding.font3.setOnClickListener {
            getFont(3)
        }
        binding.font4.setOnClickListener {
            getFont(4)
        }

        binding.InterBtn.setOnClickListener {
            if (binding.interLayout.visibility == View.VISIBLE){
                binding.interLayout.visibility = View.INVISIBLE
                //binding.interLayout.startAnimation(AnimationUtils.loadAnimation(applicationContext,R.anim.interbtn_up))
                binding.interLayout.startAnimation(AnimationUtils.loadAnimation(applicationContext,R.anim.inter_down))
            }
            else{
                binding.interLayout.visibility = View.VISIBLE
                //binding.interLayout.startAnimation(AnimationUtils.loadAnimation(applicationContext,R.anim.interbtn_up))
                binding.interLayout.startAnimation(AnimationUtils.loadAnimation(applicationContext,R.anim.inter_up))
            }
        }

        //해석 버튼
        binding.interBtn.setOnClickListener {
            if (binding.interLayout.visibility == View.VISIBLE){
                binding.interLayout.visibility = View.INVISIBLE
                //binding.interLayout.startAnimation(AnimationUtils.loadAnimation(applicationContext,R.anim.interbtn_up))
                binding.interLayout.startAnimation(AnimationUtils.loadAnimation(applicationContext,R.anim.inter_down))
            }
            else{
                binding.interLayout.visibility = View.VISIBLE
                //binding.interLayout.startAnimation(AnimationUtils.loadAnimation(applicationContext,R.anim.interbtn_up))
                binding.interLayout.startAnimation(AnimationUtils.loadAnimation(applicationContext,R.anim.inter_up))
            }
        }

//        Edit.setOnClickListener {
//            val intent = Intent(this, CreativeActivity::class.java)
//            intent.putExtra("name", Tag)
//            intent.putExtra("position", Position)
//            startActivity(intent)
//
//            overridePendingTransition(R.anim.activity_up, R.anim.activity_up_sub)
//        }

//        Delete.setOnClickListener {
//            val builder = AlertDialog.Builder(this)
//            builder.setTitle("삭제")
//                .setMessage("${title}글을 삭제하겠습니까?")
//                .setPositiveButton("삭제",
//                    DialogInterface.OnClickListener { dialog, id ->
//                        val Delete = getSharedPreferences("delete_$Position",0)
//                        val delete = Delete.edit()
//                        delete.putBoolean("delete_$Position",false)
//                        delete.apply()
//
//                        deleteFile("$Position.txt")
//
//                        val intent = Intent(this, TitleActivity::class.java)
//                        intent.putExtra("name", Tag)
//                        startActivity(intent)
//
//                        overridePendingTransition(R.anim.activity_down_sub, R.anim.activity_down)
//
//                        Toast.makeText(this,"삭제가 완료되었습니다.",Toast.LENGTH_SHORT).show()
//                    })
//                .setNegativeButton("취소",
//                    DialogInterface.OnClickListener { dialog, id ->
//
//                    })
//            // 다이얼로그를 띄워주기
//            builder.show()
//        }

    }

    //폰트 설정
    @RequiresApi(Build.VERSION_CODES.O)
    fun getFont(Value: Int?){
        val On_Stroke = R.drawable.stroke_btn_w; val Off_Stroke = R.drawable.stroke_btn; val White = "#FFFFFF"; val Gray = "#7A7A7A"

        binding.font1.setBackgroundResource(if(Value == 1) On_Stroke else Off_Stroke)
        binding.font1.setTextColor(Color.parseColor(if (Value == 1) White else Gray))
        if (Value == 1){ binding.story.typeface = Typeface.createFromAsset(assets,"font/yoondokrip.ttf") }

        binding.font2.setBackgroundResource(if(Value == 2) On_Stroke else Off_Stroke)
        binding.font2.setTextColor(Color.parseColor(if (Value == 2) White else Gray))
        if (Value == 2){ binding.story.typeface = Typeface.createFromAsset(assets,"font/bm.ttf") }

        binding.font3.setBackgroundResource(if(Value == 3) On_Stroke else Off_Stroke)
        binding.font3.setTextColor(Color.parseColor(if (Value == 3) White else Gray))
        if (Value == 3){ binding.story.typeface = Typeface.createFromAsset(assets,"font/bitrofri.ttf") }

        binding.font4.setBackgroundResource(if(Value == 4) On_Stroke else Off_Stroke)
        binding.font4.setTextColor(Color.parseColor(if (Value == 4) White else Gray))
        if (Value == 4){ binding.story.typeface = Typeface.createFromAsset(assets,"font/nanum.ttf") }

        val font_num = font?.edit()
        font_num?.putInt("font", Value!!)
        font_num?.apply()

    }

    //메뉴
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.font -> {
                binding.textOption.visibility = View.VISIBLE
            }
            R.id.Sharing -> {
                val Sharing_intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(
                        Intent.EXTRA_TEXT,
                        "$title \n\n ${binding.story.text} \n\n <해석> \n ${binding.interText.text} \n\n 더 많은 이무이를 보고싶다면 play.google.com/store/apps/details?id=com.horror.scarystory"
                    )
                    type = "text/plain"
                }
                startActivity(Intent.createChooser(Sharing_intent,null))
            }
            R.id.Update -> {
                try {
                    val email = Intent(Intent.ACTION_SEND)
                    email.type = "plain/text"
                    email.setPackage("com.google.android.gm")
                    val address = arrayOf("jhsoft04@gmail.com")
                    email.putExtra(Intent.EXTRA_EMAIL, address)
                    email.putExtra(Intent.EXTRA_SUBJECT, "[이무이 업데이트 문의]")
                    email.putExtra(
                        Intent.EXTRA_TEXT,
                        "앱 버전 (AppVersion): ${applicationContext.packageManager.getPackageInfo(applicationContext.packageName,0).versionName}\n"+
                                "기기명 (Device): ${Build.MODEL}\n"+
                                "안드로이드 OS버전 (Android OS Ver): ${Build.VERSION.RELEASE}\n\n"+
                                "원하는 업데이트 : \n\n\n"
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
            R.id.Error -> {
                try {
                    val email = Intent(Intent.ACTION_SEND)
                    email.type = "plain/text"
                    email.setPackage("com.google.android.gm")
                    val address = arrayOf("jhsoft04@gmail.com")
                    email.putExtra(Intent.EXTRA_EMAIL, address)
                    email.putExtra(Intent.EXTRA_SUBJECT, "[이무이 오류 문의]")
                    email.putExtra(
                        Intent.EXTRA_TEXT,
                        "앱 버전 (AppVersion): ${applicationContext.packageManager.getPackageInfo(applicationContext.packageName,0).versionName}\n"+
                                "기기명 (Device): ${Build.MODEL}\n"+
                                "안드로이드 OS버전 (Android OS Ver): ${Build.VERSION.RELEASE}\n"+
                                "${Position}화 $title \n\n" +
                                "오류 제보 : \n\n\n"
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

        }
        return super.onOptionsItemSelected(item)
    }

    //글자 크기
    fun getSeek(Value : Int?){

        binding.seekbar.progress = Value!!
        binding.story.textSize = Value!!.toFloat()
        binding.checkSeek.setText(Value!!.toString() + "")

        val seek_pro = seek?.edit()
        seek_pro?.putInt("seek", Value)
        seek_pro?.apply()

    }

    //버튼 표시 & 화면 인텐트
    fun getBtn(Location: String, Tag: String) {
        var title = resources.getStringArray(R.array.name)

        var numvalue = title.size

        val fullScreen = getSharedPreferences("fullScreen", Context.MODE_PRIVATE)
        fullScreenMode(fullScreen.getBoolean("fullScreen", false))

        val posi = Position

        val up = AnimationUtils.loadAnimation(applicationContext,R.anim.toolbar_up)
        val down = AnimationUtils.loadAnimation(applicationContext,R.anim.toolbar_down)

        when (Tag) {
            "All" -> {
                if (Position != 0) {
                    binding.leftBtn.visibility = View.VISIBLE
                } else {
                    binding.leftBtn.visibility = View.INVISIBLE
                }
                if (Position != (numvalue - 1)) {
                    binding.rightBtn.visibility = View.VISIBLE
                } else {
                    binding.rightBtn.visibility = View.INVISIBLE
                }
                if (Location == "Left") {
                    Position -= 1
                }
                if (Location == "Right") {
                    Position += 1
                }
            }
            "Unread" -> {
                for (i in posi downTo 0) {
                    val liboolean = getSharedPreferences("li_boolean_${i}", Context.MODE_PRIVATE)
                    ////Left 다음 이야기 체크
                    if (Location == "Left") {
                        if (!liboolean.getBoolean("li_boolean_${i}", false)) {
                            Position = i
                        }
                    }
                    //버튼이 필요한지 체크
                    if (!liboolean.getBoolean("li_boolean_${i}", false)) {
                        binding.leftBtn.visibility = View.VISIBLE; break
                    }
                    if (i == 0) {
                        binding.leftBtn.visibility = View.INVISIBLE
                    }
                }
                for (i in posi until numvalue) {
                    val liboolean = getSharedPreferences("li_boolean_${i}", Context.MODE_PRIVATE)
                    //Right 다음 이야기 체크
                    if (Location == "Right") {
                        if (!liboolean.getBoolean("li_boolean_${i}", false)) {
                            Position = i
                        }
                    }
                    //버튼이 필요한지 체크
                    if (!liboolean.getBoolean("li_boolean_${i}", false)) {
                        binding.rightBtn.visibility = View.VISIBLE; break
                    }
                    if (i == (numvalue - 1)) {
                        binding.rightBtn.visibility = View.INVISIBLE
                    }
                }
            }
            "Bookmark" -> {
                for (i in (posi - 1) downTo -1) {
                    val favorboolean = getSharedPreferences("favor_boolean_${i}", Context.MODE_PRIVATE)
                    //Left 다음 이야기 체크
                    if (Location == "Left") {
                        if (favorboolean.getBoolean("favor_boolean_${i}", false)) {
                            Position = i
                        }
                    }
                    //버튼이 필요한지 체크
                    if (i == -1) {
                        binding.leftBtn.visibility = View.INVISIBLE
                    }
                    if (favorboolean.getBoolean("favor_boolean_${i}", false)) {
                        binding.leftBtn.visibility = View.VISIBLE; break
                    }
                }
                for (i in (posi + 1)..numvalue) {
                    val favorboolean =
                        getSharedPreferences("favor_boolean_${i}", Context.MODE_PRIVATE)
                    //Right 다음 이야기 체크
                    if (Location == "Right") {
                        if (favorboolean.getBoolean("favor_boolean_${i}", false)) {
                            Position = i
                        }
                    }
                    //버튼이 필요한지 체크
                    if (i == numvalue) {
                        binding.rightBtn.visibility = View.INVISIBLE
                    }
                    if (favorboolean.getBoolean("favor_boolean_${i}", false)) {
                        binding.rightBtn.visibility = View.VISIBLE; break
                    }
                }
            }
            "Search" -> {
                val Search_Word = getSharedPreferences("Search_Word",0)
                Search_Word.getString("Search_Word","")
                binding.leftBtn.visibility = View.INVISIBLE
                binding.rightBtn.visibility = View.INVISIBLE
                for (i in (posi - 1) downTo 0) {
                    //Left 다음 이야기 체크
                    if (Location == "Left") {
                        if (title[i].contains("${Search_Word.getString("Search_Word","")}")) {
                            Position = i
                        }
                    }
                    //버튼이 필요한지 체크
//                    if (i == -1) {
//                        binding.leftBtn.visibility = View.INVISIBLE
//                    }
                    if (title[i].contains("${Search_Word.getString("Search_Word","")}")) {
                        binding.leftBtn.visibility = View.VISIBLE; break
                    }
                }
                for (i in (posi + 1) until numvalue) {
                    //Right 다음 이야기 체크
                    if (Location == "Right") {
                        if (title[i].contains("${Search_Word.getString("Search_Word","")}")) {
                            Position = i
                        }
                    }
                    //버튼이 필요한지 체크
//                    if (i == numvalue) {
//                        binding.rightBtn.visibility = View.INVISIBLE
//                    }
                    if (title[i].contains("${Search_Word.getString("Search_Word","")}")) {
                        binding.rightBtn.visibility = View.VISIBLE; break
                    }
                }
            }
//            "Watch" -> {
//                val create_number = getSharedPreferences("create_number",0)
//                binding.leftBtn.visibility = View.INVISIBLE
//                binding.rightBtn.visibility = View.INVISIBLE
//                for (i in (posi - 1) downTo 0) {
//                    val Delete = getSharedPreferences("delete_$i",0)
//                    //Left 다음 이야기 체크
//                    if (Location == "Left") {
//                        if (Delete.getBoolean("delete_${i}", true)) {
//                            Position = i
//                        }
//                    }
//                    //버튼이 필요한지 체크
////                    if (i == -1) {
////                        binding.leftBtn.visibility = View.INVISIBLE
////                    }
//                    if (Delete.getBoolean("delete_${i}", true)) {
//                        binding.leftBtn.visibility = View.VISIBLE; break
//                    }
//                }
//                for (i in (posi + 1) until create_number.getInt("create_number", 0)) {
//                    val Delete = getSharedPreferences("delete_$i",0)
//                    //Right 다음 이야기 체크
//                    if (Location == "Right") {
//                        if (Delete.getBoolean("delete_${i}", true)) {
//                            Position = i
//                        }
//                    }
//                    //버튼이 필요한지 체크
////                    if (i == create_number.getInt("create_number", 0)) {
////                        binding.rightBtn.visibility = View.INVISIBLE
////                    }
//                    if (Delete.getBoolean("delete_${i}", true)) {
//                        binding.rightBtn.visibility = View.VISIBLE; break
//                    }
//                }
//            }
        }

        //버튼을 화면에 표시/표시 안함
        if (Location == "Option"){
            if (Tool_bar?.visibility == View.VISIBLE){
                binding.leftBtn.visibility = View.INVISIBLE
                binding.rightBtn.visibility = View.INVISIBLE
                binding.textOption.visibility = View.INVISIBLE
                Tool_bar?.visibility = View.INVISIBLE

                Tool_bar?.startAnimation(up)
            }
            else{
                Tool_bar?.visibility = View.VISIBLE

                Tool_bar?.startAnimation(down)
            }
        }

        if (Location == "Left" || Location == "Right") {
//            if(!Tag.equals("Watch")){
//                val liboolean = getSharedPreferences("li_boolean_${Position}", Context.MODE_PRIVATE)
//                val li_boolean = liboolean.edit()
//                li_boolean.putBoolean("li_boolean_${Position}", true)
//                li_boolean.apply()
//            }

            val intent = Intent(this, StoryActivity::class.java)
            intent.putExtra("tag", Tag)
            intent.putExtra("position", Position)
            startActivity(intent)

            finish()

            if (mInterstitialAd != null) {
                mInterstitialAd?.show(this@StoryActivity)
            }

            if (Location == "Left") {
                overridePendingTransition(R.anim.activity_left, R.anim.activity_left_sub)
            } else {
                overridePendingTransition(R.anim.activity_right, R.anim.activity_right_sub)
            }
        }

    }

    //전면 광고
    private fun setupInterstitialAd() {

    }

    //메뉴 보이게 함
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.story_menu, menu)
        return true
    }

    override fun onBackPressed() {
        val intent = Intent(this, TitleActivity::class.java)
        intent.putExtra("name", Tag)
        startActivity(intent)

        finish()

        if (mInterstitialAd != null) {
            mInterstitialAd?.show(this@StoryActivity)
        }

        overridePendingTransition(R.anim.activity_down_sub, R.anim.activity_down)
    }

    //전체화면
    private fun fullScreenMode(switch : Boolean){
        var uiOption = window.decorView.systemUiVisibility
        if(switch){
            uiOption =  View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        }else {
            uiOption =  View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        }
        window.decorView.setSystemUiVisibility(uiOption)
    }
}