import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gdyniaapp.databinding.ActivityMainBinding
import com.example.gdyniaapp.AttractionsFragment
import com.example.gdyniaapp.HistoryFragment
import com.example.gdyniaapp.R

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicjalizacja DatabaseHelper
        databaseHelper = DatabaseHelper(this)

        binding.buttonMoreInfo.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.gdynia.pl"))
            startActivity(intent)
        }

        binding.buttonAttractions.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, AttractionsFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.buttonHistory.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HistoryFragment())
                .addToBackStack(null)
                .commit()
        }

        // Dodawanie nowej atrakcji do bazy danych
        val newAttraction = Attraction(name = "Nowa atrakcja", description = "Opis nowej atrakcji")
        val attractionId = databaseHelper.addAttraction(newAttraction)

        // Pobieranie wszystkich atrakcji z bazy danych
        val attractionsList = databaseHelper.getAllAttractions()

        // Usuwanie atrakcji z bazy danych (przykładowo pierwszej atrakcji z listy)
        if (attractionsList.isNotEmpty()) {
            val attractionToDelete = attractionsList[0]
            val deletedRows = databaseHelper.deleteAttraction(attractionToDelete.id)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        // Zamknięcie połączenia z bazą danych w onDestroy()
        databaseHelper.close()
    }
}
