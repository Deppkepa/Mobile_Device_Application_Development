package com.example.work_with_fragments

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction


class MainActivity : AppCompatActivity() {
    lateinit var fm: FragmentManager // управляет фрагментами в активности
    lateinit var ft: FragmentTransaction // используется для выполнения операций над фрагментами(замена, удаление, добавление)
    lateinit var fr1: Fragment // CurrentTaskFragment
    lateinit var fr2: Fragment // FinishTaskFragment
    // кнопки для переключения между фрагментами
    lateinit var toFinishTask: Button
    lateinit var toCurrentTask: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // инициализация активности
        setContentView(R.layout.activity_main) // определяем определенную разметку активности
        fm = supportFragmentManager // получаем экземпляр для управления фрагментами
        ft = fm.beginTransaction() // Начинаем новую транзакцию фрагментов, которая позволяет заменять добавлять удалять фрагменты
        fr2 = FinishTaskFragment() // Создаем новый экземпляр фрагмента

        val fr = fm.findFragmentById(R.id.container_fragm) // проверяет существует ли уже фрагмент в контейнере с индефикатором
        if (fr == null) {
            fr1 = CurrentTaskFragment() // создаем новый экземпляр фрагмент
            fm.beginTransaction().add(R.id.container_fragm, fr1) // добавляем его в контейнер
                .commit()
        } else
            fr1 = fr
        // находим кнопки в макете и по их индефикаторам и сохраняем ссылки на них
        toCurrentTask = findViewById(R.id.currentTask)
        toFinishTask = findViewById(R.id.finishTask)
        // обработка кликов на кнопки
        toFinishTask.setOnClickListener {

            val ft = fm.beginTransaction()
            ft.replace(R.id.container_fragm, fr2) // замена на активность FinishTaskFragment
            ft.commit() }

        toCurrentTask.setOnClickListener {
            val ft = fm.beginTransaction()
            ft.replace(R.id.container_fragm, fr1) // замена на другую активность CurrentTaskFragment
            ft.commit() }
    }

}

