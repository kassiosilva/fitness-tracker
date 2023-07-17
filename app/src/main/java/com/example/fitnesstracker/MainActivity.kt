package com.example.fitnesstracker

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerMain: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainItems = mutableListOf<MainItem>()
        mainItems.add(
            MainItem(
                id = 1,
                drawableId = R.drawable.ic_sunny,
                textStringId = R.string.imc,
                color = Color.GREEN
            )
        )

        mainItems.add(
            MainItem(
                id = 2,
                drawableId = R.drawable.ic_visibility,
                textStringId = R.string.tmb,
                color = Color.YELLOW
            )
        )

        val adapter = MainAdapter(mainItems) { id ->
            when (id) {
                1 -> {
                    startActivity(Intent(this@MainActivity, ImcActivity::class.java))
                }
                2 -> {
                    startActivity(Intent(this@MainActivity, TmbActivity::class.java))
                }
            }
        }

        recyclerMain = findViewById(R.id.recycler_main)
        recyclerMain.adapter = adapter
        recyclerMain.layoutManager = GridLayoutManager(this, 2)
    }

    private inner class MainAdapter(
        private val mainItems: List<MainItem>,
        private val onItemClickListener: (Int) -> Unit
    ) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            val view = layoutInflater.inflate(R.layout.main_item, parent, false)

            return MainViewHolder(view)
        }

        override fun getItemCount(): Int {
            return mainItems.size
        }

        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            val itemCurrent = mainItems[position]
            holder.bind(itemCurrent)
        }

        private inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bind(item: MainItem) {
                val icon: ImageView = itemView.findViewById(R.id.item_image_icon)
                val name: TextView = itemView.findViewById(R.id.item_text_name)
                val container: LinearLayout = itemView.findViewById(R.id.item_container_imc)

                icon.setImageResource(item.drawableId)
                name.setText(item.textStringId)
                container.setBackgroundColor(item.color)

                container.setOnClickListener {
                    onItemClickListener.invoke(item.id)
                }
            }
        }
    }
}