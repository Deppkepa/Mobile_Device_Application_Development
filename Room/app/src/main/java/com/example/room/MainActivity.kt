    package com.example.room

    import android.content.Intent
    import android.os.Bundle
    import android.widget.Button
    import android.widget.EditText
    import androidx.activity.compose.setContent
    import androidx.activity.enableEdgeToEdge
    import androidx.appcompat.app.AppCompatActivity
    import androidx.compose.foundation.layout.fillMaxSize
    import androidx.compose.foundation.layout.padding
    import androidx.compose.runtime.Composable
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.tooling.preview.Preview
    import androidx.recyclerview.widget.LinearLayoutManager
    import androidx.recyclerview.widget.RecyclerView
    import androidx.room.Room
    import com.example.room.ui.theme.RoomTheme

    class MainActivity : AppCompatActivity() {
        lateinit var db: AppDatabase
        lateinit var dao: ResultsDao
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)
            db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "results.db").allowMainThreadQueries().build()
            dao = db.resultsDao()
            for (i in TestData.russianCompanies2020){
                dao.insert(i)
            }
            val rv = findViewById<RecyclerView>(R.id.companies_list)
            rv.layoutManager = LinearLayoutManager(this)
            val adapter = ResultAdapter(dao.getAllNow())
            rv.adapter=adapter

            dao.getAll("name ASC").observe(this) {
                adapter.updateData(it)
            }

            findViewById<Button>(R.id.delete).setOnClickListener {
                val substring = findViewById<EditText>(R.id.toDelete).text.toString()
                dao.deleteBySubstring(substring)
            }

            findViewById<Button>(R.id.statistics).setOnClickListener {
                startActivity(Intent(this, StatActivity::class.java))
            }
        }
    }
