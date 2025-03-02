package com.example.work_with_fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class CurrentTaskFragment: Fragment() {
    override fun onCreateView(
        // вид переменная: тип данных
        inflater: LayoutInflater, // преобразовывает xml разметку в объекты для отображения на экране
        container: ViewGroup?, // родительский элемент, к которому будет добавлен фрагмент. может быть null
        savedInstanceState: Bundle? // используется для востановления состояния фрагмента если он был ранее уничтожен
    ): View? {
        val view = inflater.inflate(R.layout.fragment_current_task, container, false) // создает представление
        view.setBackgroundColor(Color.BLUE) // изменяем фон на синий цвет
        return view
        //return super.onCreateView(inflater, container, savedInstanceState)
    }
}
