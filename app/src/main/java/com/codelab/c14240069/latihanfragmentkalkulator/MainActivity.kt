package com.codelab.c14240069.latihanfragmentkalkulator

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val _mainLayout = findViewById<View>(R.id.main)
        ViewCompat.setOnApplyWindowInsetsListener(_mainLayout) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val _etAngka1 = findViewById<EditText>(R.id.etAngka1)
        val _etAngka2 = findViewById<EditText>(R.id.etAngka2)
        val _btnTambah = findViewById<Button>(R.id.btnTambah)
        val _btnKurang = findViewById<Button>(R.id.btnKurang)
        val _btnKali = findViewById<Button>(R.id.btnKali)
        val _btnBagi = findViewById<Button>(R.id.btnBagi)

        _btnTambah.setOnClickListener {
            val hasil = hitung(_etAngka1, _etAngka2, "+")
            if (hasil != null) {
                cekBackground(_mainLayout, hasil)
                bukaFragment(AddFragment())
            }
        }

        _btnKurang.setOnClickListener {
            val hasil = hitung(_etAngka1, _etAngka2, "-")
            if (hasil != null) {
                cekBackground(_mainLayout, hasil)
                bukaFragment(SubtractFragment())
            }
        }

        _btnKali.setOnClickListener {
            val hasil = hitung(_etAngka1, _etAngka2, "*")
            if (hasil != null) {
                cekBackground(_mainLayout, hasil)
                bukaFragment(MultiplyFragment())
            }
        }

        _btnBagi.setOnClickListener {
            val hasil = hitung(_etAngka1, _etAngka2, "/")
            if (hasil != null) {
                cekBackground(_mainLayout, hasil)
                bukaFragment(DivideFragment())
            }
        }
    }

    private fun hitung(angka1: EditText, angka2: EditText, operasi: String): Double? {
        val str1 = angka1.text.toString()
        val str2 = angka2.text.toString()

        if (str1.isEmpty() || str2.isEmpty()) {
            Toast.makeText(this, "Isi kedua angka terlebih dahulu", Toast.LENGTH_SHORT).show()
            return null
        }

        val num1 = str1.toDouble()
        val num2 = str2.toDouble()

        return when (operasi) {
            "*" -> {
                val hasil = num1 * num2
                DataHistory.historyKali.add("$num1 * $num2 = $hasil")
                hasil
            }
            "/" -> {
                if (num2 == 0.0) {
                    Toast.makeText(this, "Tidak bisa membagi dengan 0", Toast.LENGTH_SHORT).show()
                    return null
                }
                val hasil = num1 / num2
                DataHistory.historyBagi.add("$num1 / $num2 = $hasil")
                hasil
            }
            "+" -> {
                val hasil = num1 + num2
                DataHistory.historyTambah.add("$num1 + $num2 = $hasil")
                hasil
            }
            "-" -> {
                val hasil = num1 - num2
                DataHistory.historyKurang.add("$num1 - $num2 = $hasil")
                hasil
            }
            else -> null
        }
    }

    private fun cekBackground(layout: View, hasil: Double) {
        if (hasil < 0) {
            layout.setBackgroundColor(Color.RED)
        } else {
            layout.setBackgroundColor(Color.WHITE)
        }
    }

    private fun bukaFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}