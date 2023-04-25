package com.example.feedwizard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import com.example.feedwizard.databinding.ActivityMixFeedBinding

class MixFeed : AppCompatActivity() {
    private lateinit var binding: ActivityMixFeedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage)

        binding = ActivityMixFeedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.completeMix.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}