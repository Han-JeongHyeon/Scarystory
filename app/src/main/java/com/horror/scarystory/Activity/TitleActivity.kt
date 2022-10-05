package com.horror.scarystory.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.View.OnTouchListener
import android.view.WindowInsets.Side.all
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.DrawerListener
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.material.navigation.NavigationView
import com.horror.scarystory.Adapter.Adapter
import com.horror.scarystory.Adapter.AdapterData
import com.horror.scarystory.R
import com.horror.scarystory.databinding.ActivityTitleBinding
import kotlinx.android.synthetic.main.activity_title.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.DataInputStream
import java.lang.Exception

class TitleActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    //xml 연결
    private lateinit var binding: ActivityTitleBinding

    lateinit var Adapter: Adapter
    val datas = mutableListOf<AdapterData>()

    var type : TextView? = null

    var all : Button? = null
    var unread : Button? = null
    var bookmark : Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTitleBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //상단 타이틀
        type = findViewById<TextView>(R.id.type)

        //백 버튼
        val Back_btn = findViewById<Button>(R.id.beck_btn)

        //상단바 버튼
        all = findViewById<Button>(R.id.all)
        unread = findViewById<Button>(R.id.unread)
        bookmark = findViewById<Button>(R.id.bookmark)

        //배너 광고
        MobileAds.initialize(this) {}

        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)

        //배너 광고 이벤트
        binding.adView.adListener = object : AdListener() {
            override fun onAdClicked() {

            }

            override fun onAdClosed() {

            }

            override fun onAdFailedToLoad(adError: LoadAdError) {

            }

            override fun onAdImpression() {

            }

            override fun onAdLoaded() {

            }
        }

//        val name = intent.getStringExtra("name")

        //리사이클 뷰 가져오기
        ViewUpdate("${intent.getStringExtra("name")}")

//        Log.d("TAG", "${intent.getStringExtra("name")}")

        //버튼 체크
        Background("${intent.getStringExtra("name")}")

        //툴바
        val toolbar = findViewById<Toolbar>(R.id.Title_bar)
        setSupportActionBar(toolbar)

        //카테고리 버튼
        binding.All.setOnClickListener{
            ViewUpdate("All")
            Background("All")
        }

        all?.setOnClickListener{
            ViewUpdate("All")
            Background("All")
        }

        binding.Unread.setOnClickListener{
            ViewUpdate("Unread")
            Background("Unread")
        }

        unread?.setOnClickListener{
            ViewUpdate("Unread")
            Background("Unread")
        }

        binding.Bookmark.setOnClickListener{
            ViewUpdate("Bookmark")
            Background("Bookmark")
        }

        bookmark?.setOnClickListener{
            ViewUpdate("Bookmark")
            Background("Bookmark")
        }

        //뒤로가기 버튼
        Back_btn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

            overridePendingTransition(R.anim.activity_down_sub, R.anim.activity_down)
        }

        //코루틴으로 값이 변하면 피니쉬
        CoroutineScope(Dispatchers.Default).launch{
            while (true){
                val da = getSharedPreferences("data", Context.MODE_PRIVATE)
                if (da.getString("data","0").equals("true")){
                    val d_a = da.edit()
                    d_a.putString("data", "false")
                    d_a.apply()

                    finish()
                }
            }
        }

        //문의하기 기능
        binding.Update.setOnClickListener {
            try {
                val email = Intent(Intent.ACTION_SEND)
                email.type = "plain/text"
                email.setPackage("com.google.android.gm")
                val address = arrayOf("jhsoft04@gmail.com")
                email.putExtra(Intent.EXTRA_EMAIL, address)
                email.putExtra(Intent.EXTRA_SUBJECT, "[무서운 이야기 업데이트 문의]")
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

        binding.Error.setOnClickListener {
            try {
                val email = Intent(Intent.ACTION_SEND)
                email.type = "plain/text"
                email.setPackage("com.google.android.gm")
                val address = arrayOf("jhsoft04@gmail.com")
                email.putExtra(Intent.EXTRA_EMAIL, address)
                email.putExtra(Intent.EXTRA_SUBJECT, "[무서운 이야기 오류 문의]")
                email.putExtra(
                    Intent.EXTRA_TEXT,
                    "앱 버전 (AppVersion): ${applicationContext.packageManager.getPackageInfo(applicationContext.packageName,0).versionName}\n"+
                            "기기명 (Device): ${Build.MODEL}\n"+
                            "안드로이드 OS버전 (Android OS Ver): ${Build.VERSION.RELEASE}\n\n"+
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

        //설정 확인 (전체 화면 & 카테고리 )
        val fullScreen = getSharedPreferences("fullScreen", Context.MODE_PRIVATE)

        val category = getSharedPreferences("category", Context.MODE_PRIVATE)

        binding.fullScreen.setOnCheckedChangeListener { compoundButton, isChecked ->
            val fullScreen_ = fullScreen.edit()
            if(binding.fullScreen.isChecked){
                fullScreen_.putBoolean("fullScreen", true)
                fullScreenMode(true)
            }
            else{
                fullScreen_.putBoolean("fullScreen", false)
                fullScreenMode(false)
            }
            fullScreen_.apply()
        }
        binding.fullScreen.isChecked = fullScreen.getBoolean("fullScreen", false)

        if(binding.fullScreen.isChecked){
            fullScreenMode(true)
        }
        else{
            fullScreenMode(false)
        }

        binding.category.setOnCheckedChangeListener { compoundButton, isChecked ->
            val category_ = category.edit()
            if(binding.category.isChecked){
                category_.putBoolean("category", true)
                category_setting()
            }
            else{
                category_.putBoolean("category", false)
                category_setting()
            }
            category_.apply()
        }
        binding.category.isChecked = category.getBoolean("category", false)
        category_setting()

//        //글쓰기 (창작글 보는 방)
//        binding.watch.setOnClickListener {
//            ViewUpdate("Watch")
//            Background("Watch")
//        }
//
//        binding.creative.setOnClickListener {
//            val intent = Intent(this@TitleActivity, CreativeActivity::class.java)
//            intent.putExtra("name","$name")
//            startActivity(intent)
//            finish()
//
//            overridePendingTransition(R.anim.activity_up, R.anim.activity_up_sub)
//        }

    }

    //카테고리 세팅
    fun category_setting(){
        if (binding.category.isChecked){
            all?.visibility = View.VISIBLE
            unread?.visibility = View.VISIBLE
            bookmark?.visibility = View.VISIBLE

            type?.visibility = View.GONE
        }
        else{
            all?.visibility = View.GONE
            unread?.visibility = View.GONE
            bookmark?.visibility = View.GONE

            type?.visibility = View.VISIBLE
        }

        //검색
        val Search_Word = getSharedPreferences("Search_Word",0)

        val search_word = Search_Word.edit()
        binding.editTitle.setOnEditorActionListener { textView, i, keyEvent ->
//            var handled = false
            if (i == EditorInfo.IME_ACTION_DONE) {
                if(binding.editTitle.text.isEmpty()){
                    ViewUpdate("All")
                    Background("All")
                }
                else{
                    search_word.putString("Search_Word","${binding.editTitle.text}")
                    search_word.apply()

                    ViewUpdate("Search")
                    Background("Search")
                    binding.editTitle.setText("")
                }
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.editTitle.windowToken, 0)

                val fullScreen = getSharedPreferences("fullScreen", Context.MODE_PRIVATE)
                fullScreenMode(fullScreen.getBoolean("fullScreen", false))
            }

            true
        }

    }

    //뷰에 텍스트 넣기
    fun ViewUpdate(name: String) {
        var title = resources.getStringArray(R.array.name)

        var numvalue = title.size

        Adapter = Adapter(this)
        binding.RecycleView.adapter = Adapter

        datas.apply {
            datas.clear()
            when (name) {
                "All" -> {
                    type?.text = "전체"
                    for (i in 0 until numvalue) {
                        val liboolean = getSharedPreferences("li_boolean_${i}", Context.MODE_PRIVATE)
                        add(AdapterData("${title[i]}",liboolean.getBoolean("li_boolean_${i}", false),"$name",i)
                        )
                    }
                }
                "Unread" -> {
                    type?.text = "안본글"
                    for (i in 0 until numvalue) {
                        val liboolean = getSharedPreferences("li_boolean_${i}", Context.MODE_PRIVATE)
                        if (!liboolean.getBoolean("li_boolean_${i}", false)) {
                            add(AdapterData("${title[i]}", false, "$name", i))
                        }
                    }
                }
                "Bookmark" -> {
                    type?.text = "즐겨찾기"
                    for (i in 0 until numvalue) {
                        val favorboolean = getSharedPreferences("favor_boolean_${i}", Context.MODE_PRIVATE)
                        if (favorboolean.getBoolean("favor_boolean_${i}", false)) {
                            add(AdapterData("${title[i]}", false, "$name", i))
                        }
                    }
                }
                "Search" -> {
                    val Search_Word = getSharedPreferences("Search_Word",0)
                    type?.text = Search_Word.getString("Search_Word","")
                    for (i in 0 until numvalue) {
                        if (title[i].contains("${Search_Word.getString("Search_Word","")}")) {
                            add(AdapterData("${title[i]}", false, "$name", i))
                        }
                    }
                }
//                "Watch" -> {
//                    val create_number = getSharedPreferences("create_number",0)
//                    type?.text = "창작글"
//                    for (i in 0 until create_number.getInt("create_number",0)) {
//                        val Delete = getSharedPreferences("delete_$i",0)
//                        if (Delete.getBoolean("delete_$i", true)) {
//                            var input = openFileInput("$i.txt")
//                            var dis = DataInputStream(input)
//
//                            var valueUTF = dis.readUTF()     //문자형 type
//                            dis.close() //종료
//
//                            var title_ = valueUTF.substring(0, valueUTF.indexOf("/"))
//
//                            add(AdapterData(title_, false, "$name", i))
//                        }
//                    }
//                }
            }
        }

        Adapter.datas = datas
        Adapter.notifyDataSetChanged()

        //리사이클뷰 애니메이션 (페이드 인)
        val recycleAnim = AnimationUtils.loadAnimation(this, R.anim.recycle_anim)
        binding.RecycleView.visibility = View.INVISIBLE
        Handler().postDelayed(Runnable {
            binding.RecycleView.startAnimation(recycleAnim)
            binding.RecycleView.visibility = View.VISIBLE
        }, 200)

        val list_num = getSharedPreferences("number",0)

        //리사이클뷰 포지션 변경
        if(name == "All"){
            binding.RecycleView.scrollToPosition(list_num.getInt("number",0))
        }

        val drawer_btn = findViewById<Button>(R.id.drawer_btn)

        drawer_btn.setOnClickListener {
            drawer_layout.openDrawer(GravityCompat.END)
        }

        drawer_layout.closeDrawers()

    }

    //버튼 백그라운드 변경
    fun Background(name : String){
        binding.All.setTextColor(Color.parseColor("#7A7A7A"))
        binding.Unread.setTextColor(Color.parseColor("#7A7A7A"))
        binding.Bookmark.setTextColor(Color.parseColor("#7A7A7A"))

        all?.setBackgroundResource(R.drawable.stroke_btn)
        all?.setTextColor(Color.parseColor("#7A7A7A"))
        unread?.setBackgroundResource(R.drawable.stroke_btn)
        unread?.setTextColor(Color.parseColor("#7A7A7A"))
        bookmark?.setBackgroundResource(R.drawable.stroke_btn)
        bookmark?.setTextColor(Color.parseColor("#7A7A7A"))

        when(name){
            "All" -> {
                all?.setBackgroundResource(R.drawable.stroke_btn_w)
                all?.setTextColor(Color.parseColor("#FFFFFF"))
                binding.All.setTextColor(Color.parseColor("#FFFFFF"))
            }
            "Unread" -> {
                unread?.setBackgroundResource(R.drawable.stroke_btn_w)
                unread?.setTextColor(Color.parseColor("#FFFFFF"))
                binding.Unread.setTextColor(Color.parseColor("#FFFFFF"))
            }
            "Bookmark" -> {
                bookmark?.setBackgroundResource(R.drawable.stroke_btn_w)
                bookmark?.setTextColor(Color.parseColor("#FFFFFF"))
                binding.Bookmark.setTextColor(Color.parseColor("#FFFFFF"))
            }
        }

    }

    //뒤로가기 버튼
    override fun onBackPressed() {
        if(drawer_layout.isDrawerOpen(GravityCompat.END)){
            drawer_layout.closeDrawers()
        }
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        TODO("Not yet implemented")
    }

}