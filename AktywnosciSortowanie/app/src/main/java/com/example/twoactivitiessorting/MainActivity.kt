package com.example.twoactivitiessorting

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.twoactivitiessorting.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the binding object
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainLayout)) { view, insets ->
            val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBarsInsets.left, systemBarsInsets.top, systemBarsInsets.right, systemBarsInsets.bottom)
            insets
        }
    }

    private val activityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.let { data ->
                if (data.hasExtra("sortedResults")) {
                    binding.resultTextView.text = data.getStringExtra("sortedResults")
                }
            }
        }
    }

    fun launchSortActivity(view: View) {
        val intent = Intent(this, MainActivity2::class.java)
        val inputText = binding.inputEditText.text.toString()
        val selectedSorts = "${binding.bubbleSortCheckBox.isChecked}," +
                "${binding.insertionSortCheckBox.isChecked}," +
                "${binding.selectionSortCheckBox.isChecked}," +
                "${binding.mergeSortCheckBox.isChecked}," +
                "${binding.quickSortCheckBox.isChecked}"

        intent.putExtra("inputText", inputText)
        intent.putExtra("selectedSorts", selectedSorts)
        activityResultLauncher.launch(intent)
    }
}