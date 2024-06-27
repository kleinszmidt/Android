package com.example.prostefragmenty
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentActivity
import com.amw.prostefragmenty.DrugiFragment
import com.example.prostefragmenty.databinding.ActivityMainBinding

class MainActivity : FragmentActivity(), PierwszyFragment.PierwszyListener {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v,
                                                                             insets ->
            val systemBars =
                insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right,
                systemBars.bottom)
            insets
        }
    }
    override fun onButtonClick(tekst: String) {
        val drugiFragment = supportFragmentManager.findFragmentById(
            R.id.drugi_fragment) as DrugiFragment
        drugiFragment.ZmienTekst (tekst)
    }
}