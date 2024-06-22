package com.horror.scarystory.Util

import android.view.View

fun toggleFullScreen(context: android.content.Context) {
    val window = (context as? android.app.Activity)?.window

    // window가 null이 아니고, 전체 화면 상태를 설정
    window?.let {
        val isFullScreen = (window.attributes.flags and android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN) != 0

        if (isFullScreen) {
            it.clearFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN)
        } else {
            it.addFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
    }
}