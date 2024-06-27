import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "gdynia_database"
        private const val DATABASE_VERSION = 1

        // Nazwa tabeli i kolumny
        private const val TABLE_ATTRACTIONS = "attractions"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_DESCRIPTION = "description"

        // Zapytanie do stworzenia tabeli Attractions
        private const val CREATE_TABLE_ATTRACTIONS = (
                "CREATE TABLE $TABLE_ATTRACTIONS (" +
                        "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "$COLUMN_NAME TEXT," +
                        "$COLUMN_DESCRIPTION TEXT)"
                )
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Tworzenie tabeli Attractions przy pierwszym uruchomieniu bazy danych
        db.execSQL(CREATE_TABLE_ATTRACTIONS)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Usuwanie i ponowne tworzenie tabeli Attractions w przypadku zmiany wersji bazy danych
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ATTRACTIONS")
        onCreate(db)
    }

    // Metoda dodająca nową atrakcję do bazy danych
    fun addAttraction(attraction: Attraction): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, attraction.name)
            put(COLUMN_DESCRIPTION, attraction.description)
        }
        return db.insert(TABLE_ATTRACTIONS, null, values)
    }

    // Metoda pobierająca listę wszystkich atrakcji z bazy danych
    fun getAllAttractions(): List<Attraction> {
        val attractions = mutableListOf<Attraction>()
        val query = "SELECT * FROM $TABLE_ATTRACTIONS"
        val db = readableDatabase
        val cursor = db.rawQuery(query, null)
        cursor.use {
            while (it.moveToNext()) {
                val id = it.getLong(it.getColumnIndex(COLUMN_ID))
                val name = it.getString(it.getColumnIndex(COLUMN_NAME))
                val description = it.getString(it.getColumnIndex(COLUMN_DESCRIPTION))
                attractions.add(Attraction(id, name, description))
            }
        }
        return attractions
    }

    // Metoda usuwająca atrakcję z bazy danych
    fun deleteAttraction(attractionId: Long): Int {
        val db = writableDatabase
        return db.delete(TABLE_ATTRACTIONS, "$COLUMN_ID = ?", arrayOf(attractionId.toString()))
    }
}
