package com.horror.scarystory.service

import android.app.Application
import android.media.MediaPlayer
import android.util.Log
import com.horror.scarystory.R

class MusicApplication : Application() {

    companion object {
        private var mediaPlayer: MediaPlayer? = null
    }

    override fun onCreate() {
        super.onCreate()

        initializeMediaPlayer()
    }

    private fun initializeMediaPlayer() {
        mediaPlayer = MediaPlayer.create(this, R.raw.fuscia).apply {
            setOnCompletionListener {
                releaseMediaPlayer()
            }
            setOnErrorListener { _, what, extra ->
                releaseMediaPlayer()
                true
            }
        }
    }

    fun start() {
        mediaPlayer?.let {
            if (!it.isPlaying) {
                it.isLooping = true
                it.start()
            }
        }
    }

    fun stop() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.stop()
                it.prepare()
            }
        }
    }

    private fun releaseMediaPlayer() {
        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun onTerminate() {
        super.onTerminate()
        releaseMediaPlayer()
    }
}