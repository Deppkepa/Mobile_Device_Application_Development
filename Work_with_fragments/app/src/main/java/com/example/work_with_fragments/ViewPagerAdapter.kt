package com.example.work_with_fragments


import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment = when(position) {
        0 -> BookFragment()
        1 -> MovieFragment()
        else -> throw IllegalStateException()
    }
}