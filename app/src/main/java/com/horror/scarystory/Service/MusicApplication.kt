package com.horror.scarystory.Service

import android.app.Activity
import android.app.Application
import android.app.Service
import android.media.MediaPlayer
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.horror.scarystory.Activity.MainActivity
import com.horror.scarystory.R

class MusicApplication: Application() {

    companion object {
        var mediaPlayer: MediaPlayer? = null
    }

    override fun onCreate() {
        super.onCreate()

        mediaPlayer = MediaPlayer.create(this, R.raw.fuscia)

        Log.d("TAG", "as $mediaPlayer")

    }

    fun start() {
        mediaPlayer?.isLooping = true
        mediaPlayer?.start()
    }

    fun stop() {
        mediaPlayer?.stop()
    }

    fun release() {
        mediaPlayer?.release()
    }

}