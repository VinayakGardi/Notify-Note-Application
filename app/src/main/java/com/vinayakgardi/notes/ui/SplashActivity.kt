package com.vinayakgardi.notes.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.VideoView
import com.vinayakgardi.notes.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val videoView = findViewById<VideoView>(R.id.video_view)
        val videoPath = "android.resource://"+packageName +"/"+R.raw.notebook
        val videoUri = Uri.parse(videoPath)

        videoView.setVideoURI(videoUri)
        videoView.start()

        videoView.setOnCompletionListener {
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }
}