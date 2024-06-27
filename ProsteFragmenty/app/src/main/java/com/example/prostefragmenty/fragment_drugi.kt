package com.amw.prostefragmenty
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.prostefragmenty.databinding.FragmentDrugiBinding

class DrugiFragment : Fragment() {
    private var _binding: FragmentDrugiBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDrugiBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    fun ZmienTekst(tekst: String) {
        binding.tekstF2a.text = tekst
    }
}
