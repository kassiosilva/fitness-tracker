package com.example.fitnesstracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class ImcActivity : AppCompatActivity() {
    private lateinit var editWeight: EditText
    private lateinit var editHeight: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imc)

        editWeight = findViewById(R.id.edit_imc_weight)
        editHeight = findViewById(R.id.edit_imc_height)

        val buttonSend: Button = findViewById(R.id.button_imc_send)
        buttonSend.setOnClickListener {
            if (!validate()) {
                Toast.makeText(this, R.string.fields_message, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }
    }

    private fun validate(): Boolean {
        val hasValueFields =
            editWeight.text.toString().isNotEmpty() && editHeight.text.toString().isNotEmpty()
        val hasBeginningZero =
            !editWeight.text.toString().startsWith("0") && !editHeight.text.toString()
                .startsWith("0")

        return hasValueFields && hasBeginningZero
    }
}