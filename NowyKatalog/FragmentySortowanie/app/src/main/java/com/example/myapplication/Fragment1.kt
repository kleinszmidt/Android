package com.example.myapplication

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.databinding.Fragment1Binding

class Fragment1 : Fragment() {

    private var _binding: Fragment1Binding? = null
    private val binding get() = _binding!!

    private var fragmentCallback: Fragment1Listener? = null

    interface Fragment1Listener {
        fun onFragment1Result(result: String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            fragmentCallback = context as Fragment1Listener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context musi zaimplementować Fragment1Listener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = Fragment1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener { processInput() }
    }

    private fun processInput() {
        val selectedSorts = listOf(
            binding.bubbleBox.isChecked,
            binding.insertBox.isChecked,
            binding.selectBox.isChecked,
            binding.mergeBox.isChecked,
            binding.quickBox.isChecked
        )
        val inputData = binding.dataInput.text.toString()

        val result = performSorting(inputData, selectedSorts)
        fragmentCallback?.onFragment1Result(result)
    }

    private fun performSorting(dataInput: String?, selectedSorts: List<Boolean>): String {
        val strArray = dataInput?.split(',')?.mapNotNull { it.trim().toIntOrNull() }?.toIntArray() ?: intArrayOf(9, 0, -5, 23, 7, 3)
        var intArray = strArray

        if (intArray.isEmpty()) {
            intArray = intArrayOf(9, 0, -5, 23, 7, 3)
        }

        if (selectedSorts.none { it }) {
            return "Proszę wybrać przynajmniej jeden algorytm sortowania"
        }

        return buildString {
            if (selectedSorts[0]) append("\nBubble Sort: ${bubbleSort(intArray.copyOf()).contentToString()}")
            if (selectedSorts[1]) append("\nInsertion Sort: ${insertionSort(intArray.copyOf()).contentToString()}")
            if (selectedSorts[2]) append("\nSelection Sort: ${selectionSort(intArray.copyOf()).contentToString()}")
            if (selectedSorts[3]) append("\nMerge Sort: ${mergeSort(intArray.copyOf()).contentToString()}")
            if (selectedSorts[4]) append("\nQuick Sort: ${quickSort(intArray.copyOf()).contentToString()}")
        }
    }

    private fun bubbleSort(arr: IntArray): IntArray {
        val n = arr.size
        for (i in 0 until n - 1) {
            for (j in 0 until n - i - 1) {
                if (arr[j] > arr[j + 1]) {
                    val temp = arr[j]
                    arr[j] = arr[j + 1]
                    arr[j + 1] = temp
                }
            }
        }
        return arr
    }

    private fun insertionSort(arr: IntArray): IntArray {
        for (i in 1 until arr.size) {
            val key = arr[i]
            var j = i - 1
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j]
                j--
            }
            arr[j + 1] = key
        }
        return arr
    }

    private fun mergeSort(arr: IntArray): IntArray {
        if (arr.size <= 1) return arr

        val middle = arr.size / 2
        val left = arr.sliceArray(0 until middle)
        val right = arr.sliceArray(middle until arr.size)

        return merge(mergeSort(left), mergeSort(right))
    }

    private fun merge(left: IntArray, right: IntArray): IntArray {
        var i = 0
        var j = 0
        val merged = IntArray(left.size + right.size)

        for (k in merged.indices) {
            when {
                i >= left.size -> merged[k] = right[j++]
                j >= right.size -> merged[k] = left[i++]
                left[i] <= right[j] -> merged[k] = left[i++]
                else -> merged[k] = right[j++]
            }
        }
        return merged
    }

    private fun selectionSort(arr: IntArray): IntArray {
        for (i in arr.indices) {
            var minIndex = i
            for (j in i + 1 until arr.size) {
                if (arr[j] < arr[minIndex]) minIndex = j
            }
            val temp = arr[minIndex]
            arr[minIndex] = arr[i]
            arr[i] = temp
        }
        return arr
    }

    private fun quickSort(arr: IntArray, low: Int = 0, high: Int = arr.size - 1): IntArray {
        if (low < high) {
            val pi = partition(arr, low, high)
            quickSort(arr, low, pi - 1)
            quickSort(arr, pi + 1, high)
        }
        return arr
    }

    private fun partition(arr: IntArray, low: Int, high: Int): Int {
        val pivot = arr[high]
        var i = low - 1
        for (j in low until high) {
            if (arr[j] <= pivot) {
                i++
                val temp = arr[i]
                arr[i] = arr[j]
                arr[j] = temp
            }
        }
        val temp = arr[i + 1]
        arr[i + 1] = arr[high]
        arr[high] = temp
        return i + 1
    }
}
