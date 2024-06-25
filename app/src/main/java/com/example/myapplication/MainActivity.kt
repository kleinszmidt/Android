package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), Fragment1.Fragment1Listener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Adding Fragment1 and Fragment2 to the activity
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_1, Fragment1())
            .replace(R.id.fragment_container_2, Fragment2())
            .commit()
    }

    override fun onFragment1Result(result: String) {
        val fragment2 = supportFragmentManager.findFragmentById(R.id.fragment_container_2) as Fragment2?
        fragment2?.updateText(result)
    }
}
