package com.example.tescapstone

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // Menunggu selama 3 detik dan kemudian melanjutkan ke MainActivity
        Handler().postDelayed({
            // Memulai MainActivity setelah delay
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Menutup SplashScreenActivity
        }, 3000) // 3000ms = 3 detik
    }
}
