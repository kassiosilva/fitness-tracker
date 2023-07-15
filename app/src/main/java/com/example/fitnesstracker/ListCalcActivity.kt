package com.example.fitnesstracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.fitnesstracker.model.Calc
import java.text.SimpleDateFormat
import java.util.Locale

class ListCalcActivity : AppCompatActivity() {
    private lateinit var recyclerRecords: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_calc)

        val type =
            intent?.extras?.getString("type") ?: throw IllegalStateException("type is not found")

        val calcItems = mutableListOf<Calc>()
        val adapter = CalcAdapter(calcItems)

        recyclerRecords = findViewById(R.id.recycler_records)
        recyclerRecords.adapter = adapter
        recyclerRecords.layoutManager = LinearLayoutManager(this)

        Thread {
            val app = application as App
            val dao = app.database.calcDao()

            val response = dao.getRegisterByType(type)

            runOnUiThread {
                calcItems.addAll(response)
                adapter.notifyDataSetChanged()
            }

        }.start()
    }

    private inner class CalcAdapter(
        private val calcItems: List<Calc>
    ) : RecyclerView.Adapter<CalcViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalcViewHolder {
            val view = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false)

            return CalcViewHolder(view)
        }

        override fun getItemCount(): Int {
            return calcItems.size
        }

        override fun onBindViewHolder(holder: CalcViewHolder, position: Int) {
            val currentItem = calcItems[position]
            holder.bind(currentItem)
        }

    }

    private inner class CalcViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Calc) {
            val textView = itemView as TextView

            val simpleDateFormat =
                SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("pt", "BR"))
            val dateFormatted = simpleDateFormat.format(item.createdDate)

            textView.text = getString(R.string.list_response, item.res, dateFormatted)
        }
    }
}