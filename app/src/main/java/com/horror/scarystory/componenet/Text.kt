package com.horror.scarystory.componenet

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.horror.scarystory.R
import com.horror.scarystory.Store.toFontFamily
import com.horror.scarystory.Util.colorWhite

@Composable
fun Text(
    text: String,
    fontSize: Float = 12f,
    color: Color = colorWhite,
    font: Int = R.font.bitro,
    textAlign: TextAlign = TextAlign.Left
) {
    Text(
        textAlign = textAlign,
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(fontSize = fontSize.sp, fontFamily = font.toFontFamily())) {
                append(text)
            }
        }, color = color)
}