package com.example.tabbed_activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

data class Book(val title: String, val author: String, val desc: String)

class BookFragment : Fragment() {
    private val books = listOf(
        Book("1984", "Джордж Оруэлл", "Оруэлловская антиутопия про тоталитарное общество."),
        Book("Мастер и Маргарита", "М. Булгаков", "Роман с мистикой и философией."),
        Book("Властелин колец", "Дж. Р. Р. Толкин", "Фэнтезийная сага."),
    )
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_book, container, false)
        val rv = v.findViewById<RecyclerView>(R.id.book_list)
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = BookAdapter(books)
        return v
    }

    inner class BookAdapter(private val books: List<Book>) : RecyclerView.Adapter<BookAdapter.ViewHolder>() {
        override fun getItemCount() = books.size
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_2, parent, false)
            return ViewHolder(view)
        }

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val book = books[position]
            holder.title.text = "${book.title} — ${book.author}"
            holder.subtitle.text = book.desc
        }

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val title: TextView = view.findViewById(android.R.id.text1)
            val subtitle: TextView = view.findViewById(android.R.id.text2)
        }
    }
}