package com.example.feedwizard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import com.example.feedwizard.databinding.ActivityManageBinding

class Manage : AppCompatActivity() {
    private lateinit var binding: ActivityManageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage)

        binding = ActivityManageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startMixButton.setOnClickListener {
            val intent = Intent(this, MixFeed::class.java)
            startActivity(intent)
        }
    }
}