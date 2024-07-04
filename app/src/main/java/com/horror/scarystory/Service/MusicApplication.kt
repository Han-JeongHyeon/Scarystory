package com.horror.scarystory.service

import android.app.Application
import android.media.MediaPlayer
import android.util.Log
import com.horror.scarystory.R

//class MusicApplication: Application() {
//
//    companion object {
//        var mediaPlayer: MediaPlayer? = null
//    }
//
//    override fun onCreate() {
//        super.onCreate()
//
//        mediaPlayer = MediaPlayer.create(this, R.raw.fuscia)
//
//        Log.d("TAG", "as $mediaPlayer")
//
//    }
//
//    fun start() {
//        mediaPlayer?.isLooping = true
//        mediaPlayer?.start()
//    }
//
//    fun stop() {
//        mediaPlayer?.stop()
//    }
//
//    fun release() {
//        mediaPlayer?.release()
//    }
//
//}

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
                Log.d("TAG", "MediaPlayer completed")
                releaseMediaPlayer()
            }
            setOnErrorListener { _, what, extra ->
                Log.e("TAG", "MediaPlayer error: what=$what, extra=$extra")
                releaseMediaPlayer()
                true
            }
        }
        Log.d("TAG", "MediaPlayer initialized: $mediaPlayer")
    }

    fun start() {
        mediaPlayer?.let {
            if (!it.isPlaying) {
                it.isLooping = true
                it.start()
                Log.d("TAG", "MediaPlayer started")
            }
        }
    }

    fun stop() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.stop()
                Log.d("TAG", "MediaPlayer stopped")
                // Prepare the MediaPlayer for the next start
                it.prepare()
            }
        }
    }

    fun releaseMediaPlayer() {
        mediaPlayer?.release()
        mediaPlayer = null
        Log.d("TAG", "MediaPlayer released")
    }

    override fun onTerminate() {
        super.onTerminate()
        releaseMediaPlayer()
    }
}