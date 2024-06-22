package com.horror.scarystory.componenet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.horror.scarystory.R
import com.horror.scarystory.Util.colorWhite

@Composable
fun SettingText(
    text: String,
    fontSize: Int = 12,
    color: Color = colorWhite,
) {
    Text(
        textAlign = TextAlign.Left,
        text = buildAnnotatedString {
        withStyle(style = SpanStyle(fontSize = fontSize.sp, fontFamily = FontFamily(Font(R.font.bitro)))) {
            append(text)
        }
    }, color = color)
}