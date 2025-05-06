package com.example.room


import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room


class StatActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stat)

        val db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "results.db").allowMainThreadQueries().build()
        val dao = db.resultsDao()
        val list = dao.getAllNow()
        val sumCap = list.sumOf { it.result ?: 0 }
        findViewById<TextView>(R.id.money).text= sumCap.toString()

        val avg = if (list.isNotEmpty()) list.mapNotNull { it.result }.average() else 0.0
        val aboveAvg = list.count { (it.result
            ?: 0) > avg }
        findViewById<TextView>(R.id.good).text= aboveAvg.toString()
        val isEng = { s: String? -> s?.any { it in ('A'..'Z') || it in ('a'..'z')} ?: false }
        val cntEng = list.count { isEng(it.name) }
        findViewById<TextView>(R.id.english).text= cntEng.toString()
        val maxCap = list.filter { it.result == list.maxOfOrNull { it.result ?: 0 } }
            .minByOrNull { it.name ?: "" }?.name ?: "Нет данных"
        findViewById<TextView>(R.id.best).text= maxCap
        val maxLen = list.filter { it.name?.length == list.maxOfOrNull { it.name?.length ?: 0 } }
            .minByOrNull { it.name ?: "" }?.name ?: "Нет данных"
        findViewById<TextView>(R.id.longest).text= maxLen
    }
}