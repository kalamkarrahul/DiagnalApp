package com.rahul.diagnalapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rahul.diagnalapp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLocal.setOnClickListener {
            // load data from assests
            // json file
        }

        binding.buttonNetwork.setOnClickListener {
            // load data from web service
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}