package com.horror.scarystory.componenet

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBox(
    textState: String = "",
    onTextChanged: (String) -> Unit,
    placeholder: @Composable() () -> Unit,
) {
    var isFocused by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val defaultModifier = Modifier
        .fillMaxWidth()
        .height(10.dp)
        .border(
            1.dp,
            if (isFocused) MaterialTheme.colorScheme.primary else Color(0xFF919EAB),
            RoundedCornerShape(28.dp)
        )
        .onFocusChanged {
            isFocused = it.isFocused
        }

    BasicTextField(
        modifier = defaultModifier,
        value = textState,
        onValueChange = {
            onTextChanged(it)
        },
    ) {
        TextFieldDefaults.DecorationBox(
            value = textState,
            innerTextField = it,
            enabled = true,
            singleLine = true,
            placeholder = placeholder,
            visualTransformation = VisualTransformation.None,
            interactionSource = interactionSource,
//            trailingIcon = {
//                ImageIcon(
//                    resourcePath = "images/icons/search.svg",
//                    contentDescription = "Search Icon",
//                    contentScale = ContentScale.Fit,
//                    size = 20.dp
//                )
//            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                focusedIndicatorColor = Color.White,
                unfocusedContainerColor = Color.White,
                unfocusedIndicatorColor = Color.White,
            ),
            contentPadding = TextFieldDefaults.contentPaddingWithoutLabel(
                start = 20.dp,
                top = 0.dp,
                end = 0.dp,
                bottom = 0.dp,
            ),
        )
    }

}