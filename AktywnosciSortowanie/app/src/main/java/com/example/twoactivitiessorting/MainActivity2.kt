package com.example.twoactivitiessorting

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.twoactivitiessorting.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding
    private lateinit var sortedResults: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val intentData = intent.extras ?: return
        val dataInput = intentData.getString("inputText")
        val sortsActive = intentData.getString("selectedSorts")

        performSort(dataInput, sortsActive)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.secondaryLayout)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun bubbleSort(array: IntArray): IntArray {
        for (i in array.indices) {
            for (j in 0 until array.size - i - 1) {
                if (array[j] > array[j + 1]) {
                    val temp = array[j]
                    array[j] = array[j + 1]
                    array[j + 1] = temp
                }
            }
        }
        return array
    }

    private fun insertionSort(array: IntArray): IntArray {
        for (i in 1 until array.size) {
            val key = array[i]
            var j = i - 1
            while (j >= 0 && array[j] > key) {
                array[j + 1] = array[j]
                j--
            }
            array[j + 1] = key
        }
        return array
    }

    private fun mergeSort(array: IntArray): IntArray {
        if (array.size <= 1) return array
        val middle = array.size / 2
        val left = array.sliceArray(0 until middle)
        val right = array.sliceArray(middle until array.size)
        return merge(mergeSort(left), mergeSort(right))
    }

    private fun merge(left: IntArray, right: IntArray): IntArray {
        var i = 0
        var j = 0
        val mergedArray = IntArray(left.size + right.size)
        for (k in mergedArray.indices) {
            when {
                i >= left.size -> mergedArray[k] = right[j++]
                j >= right.size -> mergedArray[k] = left[i++]
                left[i] <= right[j] -> mergedArray[k] = left[i++]
                else -> mergedArray[k] = right[j++]
            }
        }
        return mergedArray
    }

    private fun selectionSort(array: IntArray): IntArray {
        for (i in array.indices) {
            var minIndex = i
            for (j in i + 1 until array.size) {
                if (array[j] < array[minIndex]) {
                    minIndex = j
                }
            }
            val temp = array[minIndex]
            array[minIndex] = array[i]
            array[i] = temp
        }
        return array
    }

    private fun quickSort(array: IntArray, low: Int = 0, high: Int = array.size - 1): IntArray {
        if (low < high) {
            val pi = partition(array, low, high)
            quickSort(array, low, pi - 1)
            quickSort(array, pi + 1, high)
        }
        return array
    }

    private fun partition(array: IntArray, low: Int, high: Int): Int {
        val pivot = array[high]
        var i = low - 1
        for (j in low until high) {
            if (array[j] < pivot) {
                i++
                val temp = array[i]
                array[i] = array[j]
                array[j] = temp
            }
        }
        val temp = array[i + 1]
        array[i + 1] = array[high]
        array[high] = temp
        return i + 1
    }

    private fun performSort(dataInput: String?, sortsActive: String?) {
        val inputString = dataInput?.replace(" ", "") ?: ""
        val inputArray = inputString.split(",").mapNotNull { it.toIntOrNull() }.toIntArray()

        if (inputArray.isEmpty()) {
            sortedResults = "Input data is empty or invalid"
        } else {
            val activeSorts = sortsActive?.split(",")?.map { it.toBooleanStrictOrNull() ?: false } ?: listOf()
            val results = mutableListOf<String>()

            if (activeSorts[0]) {
                results.add("Bubble Sort: ${bubbleSort(inputArray.clone()).contentToString()}")
            }
            if (activeSorts[1]) {
                results.add("Insertion Sort: ${insertionSort(inputArray.clone()).contentToString()}")
            }
            if (activeSorts[2]) {
                results.add("Selection Sort: ${selectionSort(inputArray.clone()).contentToString()}")
            }
            if (activeSorts[3]) {
                results.add("Merge Sort: ${mergeSort(inputArray.clone()).contentToString()}")
            }
            if (activeSorts[4]) {
                results.add("Quick Sort: ${quickSort(inputArray.clone()).contentToString()}")
            }

            if (results.isEmpty()) {
                sortedResults = "No sorting algorithms selected"
            } else {
                sortedResults = results.joinToString("\n")
            }
        }

        finishWithResult()
    }

    private fun finishWithResult() {
        val resultIntent = Intent()
        resultIntent.putExtra("sortedResults", sortedResults)
        setResult(RESULT_OK, resultIntent)
        finish()
    }
}
