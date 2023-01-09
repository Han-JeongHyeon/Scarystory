package com.horror.scarystory

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.horror.scarystory.Activity.MainActivity

class MessagingService : FirebaseMessagingService() {
    private val TAG = "FirebaseService"

    // 토큰 생성
    override fun onNewToken(token: String) {

        // 토큰 값 따로 저장
        PrefKey(this).putString("token", token)
    }

    // 메시지 수신
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.from)

        if (remoteMessage.data.isNotEmpty()) {
            Log.i("바디", remoteMessage.data["body"].toString())
            Log.i("타이틀", remoteMessage.data["title"].toString())
            sendNotification(remoteMessage)
        } else {
            Log.i("수신에러 : ", "data가 비어있습니다. 메시지를 수신하지 못했습니다.")
            Log.i("data값 :", remoteMessage.data.toString())
        }
    }


    // 알림 생성 (아이콘, 알림 소리 등)
    private fun sendNotification(remoteMessage: RemoteMessage) {
        // RemoteCode, ID를 고유값으로 지정하여 알림이 개별 표시 되도록 함
        val uniId: Int = (System.currentTimeMillis() / 7).toInt()

        // 일회용 PendingIntent
        // PendingIntent : Intent 의 실행 권한을 외부의 어플리케이션에게 위임
        var intent: Intent? = null

        val content = remoteMessage.data["content"].toString()

//        if (remoteMessage.data["noti"].toString() == "UPDATE_ON" || content.substring(0, content.indexOf("/")) == "UPDATE_ON") {
//            intent = Intent(Intent.ACTION_VIEW)
//            intent.addCategory(Intent.CATEGORY_DEFAULT)
//            intent.data = Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
//        } else {
            intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) // Activity Stack을 경로만 남김, A-B-C-D-B => A-B
//        }

        val pendingIntent =
            PendingIntent.getActivity(this, uniId, intent, PendingIntent.FLAG_ONE_SHOT)

//        val notificationNoti = when (remoteMessage.data["noti"].toString()) {
//            "AM_ON" -> PrefKey(this).getBoolean("AM_ON", true)
//            "PM_ON" -> PrefKey(this).getBoolean("PM_ON", true)
//            "UPDATE_ON" -> PrefKey(this).getBoolean("UPDATE_ON", true)
//            else -> false
//        }
//
//        val notificationBody = when (remoteMessage.data["content"].toString()) {
//            "AM_ON" -> PrefKey(this).getBoolean("AM_ON", true)
//            "PM_ON" -> PrefKey(this).getBoolean("PM_ON", true)
//            "UPDATE_ON" -> PrefKey(this).getBoolean("UPDATE_ON", true)
//            else -> false
//        }

//        if (notificationNoti || notificationBody) {
            // 알림 채널 이름
            val channelId = "channel"

            // 알림에 대한 UI 정보와 작업을 지정
            val notificationBuilder =
                notiSetting(channelId, pendingIntent, remoteMessage.data["content"].toString().substring(remoteMessage.data["content"].toString().indexOf("/")))

            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            // 오레오 버전 이후에는 채널이 필요
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel =
                    NotificationChannel(channelId, "Notice", NotificationManager.IMPORTANCE_DEFAULT)
                notificationManager.createNotificationChannel(channel)
            }

            // 알림 생성
            notificationManager.notify(uniId, notificationBuilder.build())
//        }

    }

    fun notiSetting(
        channelId: String,
        pendingIntent: PendingIntent,
        content: String
    ): NotificationCompat.Builder {
        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        // 알림에 대한 UI 정보와 작업을 지정
        return NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)     // 아이콘 설정
            .setContentText(content)     // 메시지 내용
            .setAutoCancel(true)
            .setSound(soundUri)     // 알림 소리
            .setContentIntent(pendingIntent)       // 알림 실행 시 Intent
    }

}