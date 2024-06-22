package com.horror.scarystory.activity

import android.os.Bundle
import android.util.Printer
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.ads.AdSize
import com.horror.scarystory.R
import com.horror.scarystory.Util.topBarHeight
import com.horror.scarystory.componenet.TouchScrollBox
import com.horror.scarystory.activity.ui.theme.ScarystoryTheme
import com.horror.scarystory.componenet.BannerAd
import com.horror.scarystory.componenet.CustomTopBar
import com.horror.scarystory.componenet.Word

class StoryListActivity : BaseActivity() {
    companion object {
        var storyList: Array<String> = arrayOf()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        storyList = resources.getStringArray(R.array.name)

        setContent {
            ScarystoryTheme {
                Box (
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = painterResource(id = R.drawable.main_background),
                        contentDescription = "메인 화면",
                        contentScale = ContentScale.Crop
                    )

                    TouchScrollBox {
                        Column {
                            Spacer(modifier = Modifier.height(topBarHeight))
                            
                            storyList.forEachIndexed { index, it ->
                                Column(verticalArrangement = Arrangement.Top) {
                                    ListItem(
                                        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                                        headlineContent = {
                                            Column(
                                                horizontalAlignment = Alignment.Start,
                                                modifier = Modifier.clickable {
//                                                    this@StoryListActivity.toActivity(StoreViewActivity::class.java, index.toString())
                                                    println("클릭 $index")
                                                }
                                            ) {
                                                Spacer(modifier = Modifier.height(4.dp))
                                                Word(index = index, text = it)
                                            }
                                        },
                                    )
                                    Divider(color = Color.White, startIndent = 5.dp)
                                }
                            }
                            Spacer(modifier = Modifier.height(AdSize.FULL_BANNER.height.dp))
                        }
                    }
                }

                CustomTopBar()

                BannerAd()

            }
        }

    }

}
