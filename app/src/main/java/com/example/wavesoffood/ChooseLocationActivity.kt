package com.example.wavesoffood

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import com.example.wavesoffood.databinding.ActivityChooseLocationBinding
import java.util.*

class ChooseLocationActivity : AppCompatActivity() {
    private val binding: ActivityChooseLocationBinding by lazy {
        ActivityChooseLocationBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val locationList = arrayOf("English", "Vietnamese", "Chinese")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, locationList)
        val autoCompleteTextView = binding.ListOfLocation as AutoCompleteTextView
        autoCompleteTextView.setAdapter(adapter)

        autoCompleteTextView.setOnItemClickListener { _, _, position, _ ->
            when (position) {
                0 -> setLocale("en") // Set language to English
                1 -> setLocale("vi") // Set language to Vietnamese
                2 -> setLocale("zh") // Set language to Chinese
            }
            navigateToLoginActivity()
        }
    }

    private fun setLocale(language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    private fun navigateToLoginActivity() {
        val intent = Intent(this, StartActivity::class.java)
        startActivity(intent)
        finish()
    }
}
