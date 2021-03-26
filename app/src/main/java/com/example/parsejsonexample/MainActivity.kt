package com.example.parsejsonexample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.parsejsonexample.arrayJSON.ArrayJSONActivity
import com.example.parsejsonexample.databinding.ActivityMainBinding
import com.example.parsejsonexample.nestedJSON.NestedJSONActivity
import com.example.parsejsonexample.simpleJSON.SimpleJSONActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.simpleJsonButton.setOnClickListener {
            val intent = Intent(this@MainActivity, SimpleJSONActivity::class.java)
            this@MainActivity.startActivity(intent)
        }

        binding.arrayJsonButton.setOnClickListener {
            val intent = Intent(this@MainActivity, ArrayJSONActivity::class.java)
            this@MainActivity.startActivity(intent)
        }

        binding.nestedJsonButton.setOnClickListener {
            val intent = Intent(this@MainActivity, NestedJSONActivity::class.java)
            this@MainActivity.startActivity(intent)
        }
    }
}