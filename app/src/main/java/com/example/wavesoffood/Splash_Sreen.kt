package com.example.wavesoffood
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.wavesoffood.databinding.ActivitySplashSreenBinding
import com.example.wavesoffood.StartActivity



class Splash_Screen : AppCompatActivity() {
    private lateinit var binding: ActivitySplashSreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashSreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, ChooseLocationActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000) // 5 seconds delay
    }
}
