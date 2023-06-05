package com.horror.scarystory.Activity

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.nfc.Tag
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.google.android.gms.ads.*
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.horror.scarystory.*
import com.horror.scarystory.Adapter.Adapter
import com.horror.scarystory.Adapter.AdapterData
import com.horror.scarystory.R
import com.horror.scarystory.databinding.ActivityMainBinding
import com.horror.scarystory.databinding.ActivityTitleBinding
import kotlinx.android.synthetic.main.activity_title.*
import kotlinx.android.synthetic.main.tiele_bar.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class TitleActivity : BaseActivity<ActivityTitleBinding>({ ActivityTitleBinding.inflate(it) }) {

    lateinit var adapter: Adapter
    val datas = mutableListOf<AdapterData>()

    var time = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setOnClickListener()
        reviewDialog()
//        adapter()

        adapter = Adapter(this).apply {
            setOnItemClickListener(object : Adapter.OnItemClickListener { // 이벤트 리스너
                override fun onItemClick(v: View) {
                    finish()
                }
            })
        }

        binding.RecycleView.adapter = adapter

        //Title 상단 바 표시
        setSupportActionBar(findViewById(R.id.title_bar))

        ViewUpdate(Type.ALL.code)

        //전체 화면 작업
        binding.fullScreen.isChecked = PrefKey(this).getBoolean("fullScreen", false)
        fullScreenMode(binding.fullScreen.isChecked)

        //검색
        binding.editTitle.setOnEditorActionListener { textView, i, keyEvent ->
            if (i == EditorInfo.IME_ACTION_DONE) {
                if (binding.editTitle.text.isEmpty()) {
                    ViewUpdate(Type.ALL.code)
                } else {
                    PrefKey(this).putString("Search_Word", "${binding.editTitle.text}")

                    ViewUpdate(Type.SEARCH.code)
                    binding.editTitle.setText("")
                }
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.editTitle.windowToken, 0)

                fullScreenMode(PrefKey(this).getBoolean("fullScreen", false))
            }

            true
        }
    }

    fun reviewDialog(){
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.showupdate_popup)

        if (Random().nextInt(20) + 1 == 1 && !PrefKey(this).getBoolean("review", false)){
            dialog.show()
        }

        val btnNo = dialog.findViewById<Button>(R.id.btnNo)
        val btnYes = dialog.findViewById<Button>(R.id.btnYes)

        btnNo.setOnClickListener {
            dialog.dismiss()
        }

        btnYes.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.addCategory(Intent.CATEGORY_DEFAULT)
            intent.data = Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
            startActivity(intent)
            PrefKey(this).putBoolean("review", true)
            dialog.dismiss()
        }
    }

    fun setOnClickListener() {
        binding.apply {
            btnAll.setOnClickListener {
                ViewUpdate(Type.ALL.code)
            }

            btnUnread.setOnClickListener {
                ViewUpdate(Type.UNREAD.code)
            }

            btnBookmark.setOnClickListener {
                ViewUpdate(Type.BOOKMARK.code)
            }

            btnSetting.setOnClickListener {
                ViewUpdate(Type.SETTING.code)
            }

            fullScreen.setOnCheckedChangeListener { compoundButton, isChecked ->
                PrefKey(this@TitleActivity).putBoolean("fullScreen", isChecked)
                fullScreenMode(isChecked)
            }

            ll_cpn.setOnClickListener {
                val dialog = Dialog(this@TitleActivity)

                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setContentView(R.layout.popup)

                val btnYes = dialog.findViewById<Button>(R.id.btnYes)
                val btnNo = dialog.findViewById<Button>(R.id.btnNo)
                val title = dialog.findViewById<TextView>(R.id.title)

                if(PrefKey(this@TitleActivity).getBoolean("popupShow", true)) {
                    PrefKey(this@TitleActivity).putBoolean("popupShow", false)
                    title.text = resources.getText(R.string.해석권_설명)
                    btnNo.text = resources.getText(R.string.확인)
                    btnYes.hide()
                }

                btnNo.setOnClickListener {
                    dialog.dismiss()
                }

                btnYes.setOnClickListener {
                    AdRequestService(this@TitleActivity).showRewardedAd()
                    dialog.dismiss()
                }

                dialog.show()

            }
        }
    }

    //바텀 버튼 색상 초기화
    fun resetBottomSeat() {
        binding.apply {
            val bottomSeat = ContextCompat.getColor(this@TitleActivity, R.color.bottom_seat)
            btnAll.setTextColor(bottomSeat)
            btnUnread.setTextColor(bottomSeat)
            btnBookmark.setTextColor(bottomSeat)
            btnSetting.setTextColor(bottomSeat)
        }
    }

    //뷰에 텍스트 넣기
    fun ViewUpdate(tag: String): Boolean {
        if (System.currentTimeMillis() - time < 1000L) {
            return true
        }

        time = System.currentTimeMillis()

        resetBottomSeat()

        val viewTag = PrefKey(this).putString("Tag", tag)

        if (viewTag ==  Type.SETTING.code) {
            binding.btnSetting.setTextColor(ContextCompat.getColor(this, R.color.textColor))
            binding.RecycleView.hide()
            binding.llSetting.show()
        } else {
            binding.RecycleView.show()
            binding.llSetting.hide()
        }

        setViewSetting()

        return true
    }

    fun setViewSetting() {
        val title = resources.getStringArray(R.array.name)
        val viewTag = PrefKey(this).getString("Tag", "All")
        val numvalue = title.size

        CoroutineScope(Dispatchers.IO).launch {
            datas.apply {
                datas.clear()
                for (i in 0 until numvalue) {
                    when (viewTag) {
                        Type.ALL.code -> {
                            add(
                                AdapterData(
                                    title[i],
                                    PrefKey(this@TitleActivity).getBoolean(
                                        "li_boolean_${i}",
                                        false
                                    ),
                                    viewTag,
                                    i
                                )
                            )
                        }
                        Type.UNREAD.code -> {
                            if (!PrefKey(this@TitleActivity).getBoolean("li_boolean_${i}", false)) {
                                add(AdapterData(title[i], false, viewTag, i))
                            }
                        }
                        Type.BOOKMARK.code -> {
                            if (PrefKey(this@TitleActivity).getBoolean(
                                    "favor_boolean_${i}",
                                    false
                                )
                            ) {
                                add(AdapterData(title[i], false, viewTag, i))
                            }
                        }
                        Type.SEARCH.code -> {
                            if (title[i].contains(
                                    "${
                                        PrefKey(this@TitleActivity).getString(
                                            "Search_Word",
                                            ""
                                        )
                                    }"
                                )
                            ) {
                                add(AdapterData(title[i], false, viewTag, i))
                            }
                        }
                    }
                }
            }

            withContext(Dispatchers.Main) {
                when(viewTag) {
                    Type.ALL.code -> {
                        binding.btnAll.setTextColor(ContextCompat.getColor(this@TitleActivity, R.color.textColor))
                    }
                    Type.UNREAD.code -> {
                        binding.btnUnread.setTextColor(ContextCompat.getColor(this@TitleActivity, R.color.textColor))
                    }
                    Type.BOOKMARK.code -> {
                        binding.btnBookmark.setTextColor(ContextCompat.getColor(this@TitleActivity, R.color.textColor))
                    }
                    Type.SEARCH.code -> {
                        binding.btnAll.setTextColor(ContextCompat.getColor(this@TitleActivity, R.color.textColor))
//                        tv_top_title.text = PrefKey(this@TitleActivity).getString("Search_Word", "")
                    }
                }

                adapter.datas = datas
                adapter.notifyDataSetChanged()

                if(viewTag != Type.SETTING.code) {
                    //리사이클뷰 애니메이션 (페이드 인)
                    val recycleAnim =
                        AnimationUtils.loadAnimation(this@TitleActivity, R.anim.recycle_anim)
                    binding.RecycleView.visibility = View.INVISIBLE

                    Handler().postDelayed(Runnable {
                        binding.RecycleView.startAnimation(recycleAnim)
                        binding.RecycleView.show()
                    }, 200)

                }

                //리사이클뷰 포지션 변경
                binding.RecycleView.scrollToPosition(if (viewTag == Type.ALL.code) PrefKey(this@TitleActivity).getInt("number", 0) else 0)
            }

        }
    }

    //전체화면
    private fun fullScreenMode(switch: Boolean) {
        var uiOption = window.decorView.systemUiVisibility
        if (switch) {
            uiOption = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        } else {
            uiOption = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        }
        window.decorView.setSystemUiVisibility(uiOption)
    }

    override fun onResume() {
        super.onResume()
        amount.text = PrefKey(this).getInt("inter",10).toString()
    }

//    override fun onRestart() {
//        super.onRestart()
//
//        val application = application as? MyApplication
//
//        application?.showAdIfAvailable(
//            this@TitleActivity,
//            object : MyApplication.OnShowAdCompleteListener {
//                override fun onShowAdComplete() {
//
//                }
//            })
//    }

}