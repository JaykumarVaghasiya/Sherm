package com.jay.shermassignment.ui.splashScreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowInsets
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.jay.shermassignment.R
import com.jay.shermassignment.ui.login.LoginActivity

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContentView(R.layout.activity_splash_screen)

        window.setFlags(
            WindowInsets.Type.statusBars(),
            WindowInsets.Type.statusBars()
        )


        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            overridePendingTransition(
                com.google.android.material.R.anim.abc_grow_fade_in_from_bottom,
                R.anim.slide_out_to_left
            )
            finish()
        }, 3000)
    }
}
