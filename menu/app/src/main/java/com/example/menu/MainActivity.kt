package com.example.menu

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import android.widget.Spinner
import android.widget.Toolbar
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var pagerAdapter: ViewPagerAdapter
    private var currentLocale: Locale = Locale("Rus")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadLanguagePreference()
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setTitle(R.string.app_name)
        toolbar.inflateMenu(R.menu.langs)
        toolbar.setOnMenuItemClickListener { item ->  Log.d("mytag", "item: $item"); true }
        val spinner: Spinner = findViewById(R.id.spinner)
        val items = arrayOf("English", "Русский", "日本語")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter=adapter
        var lang = when (currentLocale) {
            Locale.ENGLISH -> "English"
            Locale("ru") -> "Русский"
            Locale.JAPANESE -> "日本語"
            else -> "Русский"}
        spinner.setSelection(items.indexOf(lang))

        viewPager = findViewById(R.id.view_pager)
        tabLayout = findViewById(R.id.tab_layout)
        pagerAdapter = ViewPagerAdapter(this)
        viewPager.adapter = pagerAdapter

        val tabTitles = listOf(getResources().getString(R.string.short_weather), getResources().getString(R.string.long_weather))
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
        val chooseFragmentButton: Button = findViewById(R.id.btn_choose_fragment)
        chooseFragmentButton.setOnClickListener {
            showFragmentChoiceDialog()
        }
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position)
                lang = when (currentLocale) {
                    Locale.ENGLISH -> "English"
                    Locale("ru") -> "Русский"
                    Locale.JAPANESE -> "日本語"
                    else -> "Русский"}
                if (selectedItem != lang) {
                    updateLocale(selectedItem.toString())
                    saveLanguagePreference(selectedItem.toString())
                    currentLocale = when (selectedItem) {
                        "English" -> Locale.ENGLISH
                        "Русский" -> Locale("ru")
                        "日本語" -> Locale.JAPANESE
                        else -> Locale("ru")
                    }
                    recreate()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Ничего не делать
            }
        }
    }
    private fun showFragmentChoiceDialog() {
        val items = arrayOf("Короткая погода", "Подробная погода")
        AlertDialog.Builder(this)
            .setTitle("Выберите отображение")
            .setItems(items) { _, which ->
                viewPager.currentItem = which
            }
            .setNegativeButton("Отмена", null)
            .show()
    }
    private fun saveLanguagePreference(language: String) {
        val prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)
        prefs.edit().putString("app_language", language).apply()
    }

    private fun loadLanguagePreference() {
        val prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)
        val language = prefs.getString("app_language", "English") ?: "English"
        updateLocale(language)
    }

    private fun updateLocale(language: String) {
        currentLocale = when (language) {
            "English" -> Locale.ENGLISH
            "Русский" -> Locale("ru")
            "日本語" -> Locale.JAPANESE
            else -> Locale("ru")
        }
        Locale.setDefault(currentLocale)
        val configuration = resources.configuration
        configuration.setLocale(currentLocale)
        resources.updateConfiguration(configuration, resources.displayMetrics)


    }

}