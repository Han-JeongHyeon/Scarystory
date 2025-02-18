package com.horror.scarystory.Util

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with

fun getAnimation(animationAction: String): AnimatedContentTransitionScope<Int>.() -> ContentTransform {
    return if (animationAction == "left") leftToRightTransitionSpec else rightToLeftTransitionSpec
}

// 왼쪽에서 오른쪽으로 이동하는 애니메이션
@OptIn(ExperimentalAnimationApi::class)
val leftToRightTransitionSpec: AnimatedContentTransitionScope<Int>.() -> ContentTransform = {
    (slideInHorizontally(
        initialOffsetX = { -it }, // 왼쪽에서 슬라이드 인
        animationSpec = tween(500)
    ) + fadeIn(
        animationSpec = tween(500)
    ) with slideOutHorizontally(
        targetOffsetX = { it }, // 오른쪽으로 슬라이드 아웃
        animationSpec = tween(500)
    ) + fadeOut(
        animationSpec = tween(500)
    )).using(
        SizeTransform(clip = false)
    )
}

// 오른쪽에서 왼쪽으로 이동하는 애니메이션
@OptIn(ExperimentalAnimationApi::class)
val rightToLeftTransitionSpec: AnimatedContentTransitionScope<Int>.() -> ContentTransform = {
    (slideInHorizontally(
        initialOffsetX = { it }, // 오른쪽에서 슬라이드 인
        animationSpec = tween(500)
    ) + fadeIn(
        animationSpec = tween(500)
    ) with slideOutHorizontally(
        targetOffsetX = { -it }, // 왼쪽으로 슬라이드 아웃
        animationSpec = tween(500)
    ) + fadeOut(
        animationSpec = tween(500)
    )).using(
        SizeTransform(clip = false)
    )
}