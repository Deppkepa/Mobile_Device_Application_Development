package com.example.start_recyclerview

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    val baseColor = mutableListOf(Color.YELLOW, Color.RED, Color.GREEN, Color.MAGENTA)
    val colorsList = generateColorShades(baseColor.random(), 20)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // пример использования RecyclerView с собственным адаптером
        val rv = findViewById<RecyclerView>(R.id.rview)
        val colorAdapter = ColorAdapter(LayoutInflater.from(this))
        // добавляем данные в список для отображения
        colorAdapter.submitList(colorsList)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = colorAdapter
    }

    fun generateColorShades(color: Int, numberOfShades: Int): List<Int> {
        val shades = mutableListOf<Int>()
        val step = 255 / (numberOfShades + 1)

        for (i in 0 until numberOfShades) {
            // Рассчитываем новый оттенок, изменяя яркость (уменьшая RGB компоненты)
            val adjustment = step * (i + 1)

            val r = Color.red(color)
            val g = Color.green(color)
            val b = Color.blue(color)

            val newR = (r - adjustment).coerceAtLeast(0)
            val newG = (g - adjustment).coerceAtLeast(0)
            val newB = (b - adjustment).coerceAtLeast(0)

            val newColor = Color.rgb(newR, newG, newB)
            shades.add(newColor)
        }
        return shades
    }
}