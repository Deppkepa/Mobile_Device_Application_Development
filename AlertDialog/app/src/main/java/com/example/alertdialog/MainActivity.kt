package com.example.alertdialog

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var pagerAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager = findViewById(R.id.view_pager)
        tabLayout = findViewById(R.id.tab_layout)
        pagerAdapter = ViewPagerAdapter(this)
        viewPager.adapter = pagerAdapter

        val tabTitles = listOf("Короткая погода", "Подробная погода")
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
        val chooseFragmentButton: Button = findViewById(R.id.btn_choose_fragment)
        chooseFragmentButton.setOnClickListener {
            showFragmentChoiceDialog()
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

}