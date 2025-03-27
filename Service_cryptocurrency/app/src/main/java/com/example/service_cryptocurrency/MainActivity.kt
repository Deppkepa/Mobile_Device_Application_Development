package com.example.service_cryptocurrency

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel // управляет пользовательскимим данными для вашего интерфейса
    lateinit var textRate: TextView // отображает текст
    lateinit var textTargetRate: EditText // Позволяет вводить и редактировать текст
    lateinit var rootView: View // корневое представление вашей активности

    // `savedInstanceState` является объектом, который содержит состояние
    // предыдущей активности (если оно есть),
    // позволяя восстановить состояние UI при пересоздании.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // устанавливает основной макет активнсоти

        initViewModel()
        initView()
    }

    override fun onDestroy() { // вызывается когда система уничтожает активность
        super.onDestroy() // вызов родительского метода

    }

    fun initViewModel() { // инициализирует `MainViewModel`
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)  // проверяет, имеет ли уже хранящийся экземпляр для данного актививти если есть выводит его если нет то создает

        viewModel.usdRate.observe(this, { // устанавливается наблюдатель (observer) на данные содержащиеся в "usdRate"
            textRate.text = "$it RUB" // обновление цифры на экране
        })
        viewModel.onCreate()
    }

    fun initView() { // отвечает за инициализацию элементов пользовательского интерфейса UI
        textRate = findViewById(R.id.textUsdRubRate) // отображает текущий курс доллара к рублю
        textTargetRate = findViewById(R.id.textTargetRate) // для ввода целевого курса, к которому пользователь хочет подписаться
        rootView = findViewById(R.id.rootView)  // исспользуется для получения ссылки на корневой элемент, чтобы использовать его при создании уведомлений
        findViewById<Button>(R.id.btnRefresh).setOnClickListener {
            viewModel.onRefreshClicked()
        }
        findViewById<Button>(R.id.btnSubscribeToRate).setOnClickListener {
            val targetRate = textTargetRate.text.toString()
            val startRate = viewModel.usdRate.value
            if (targetRate.isNotEmpty() && startRate?.isNotEmpty() == true) {
                RateCheckService.stopService(this)
                RateCheckService.startService(this, startRate, targetRate)
            } else if (targetRate.isEmpty()) {
                Snackbar.make(rootView, R.string.target_rate_empty, Snackbar.LENGTH_SHORT).show()
            } else if (startRate.isNullOrEmpty()) {
                Snackbar.make(rootView, R.string.current_rate_empty, Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}