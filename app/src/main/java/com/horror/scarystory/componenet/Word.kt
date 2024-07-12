package com.horror.scarystory.componenet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.horror.scarystory.R

@Composable
fun Word(index: Int, text: String) {
    val index = index + 1

    Column {
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Left,
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontSize = defaultSize.sp, fontFamily = FontFamily(Font(R.font.jsarirang)))) {
                    append("$index | $text")
                }
            },
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}

const val defaultSize = 32