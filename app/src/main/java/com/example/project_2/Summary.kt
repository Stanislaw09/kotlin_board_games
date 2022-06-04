package com.example.project_2

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.project_2.databinding.ActivitySummaryBinding
import java.io.File
import java.util.*
import kotlin.system.exitProcess

class Summary : AppCompatActivity() {
    var userName: String=""
    var lastSync=""
    private lateinit var binding: ActivitySummaryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)
        binding = ActivitySummaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras = intent.extras

        var name  = extras?.getString("userName");
        var gamesNum  =  extras?.getInt("numOfGames");
        lastSync  = extras?.getString("syncDate").toString();

        if (name != null) {
            userName=name.toString()
        }

        binding.userName.text=name.toString()
        binding.gamesNum.text=gamesNum.toString()
        binding.lastSync.text=lastSync
    }

    fun clearData(view: View) {
        Toast.makeText(this, "Data cleared (☞ﾟヮﾟ)☞", Toast.LENGTH_SHORT).show()

        var intent = Intent(this@Summary, MainActivity::class.java)
        intent.putExtra("clear", "true")
        startActivity(intent)
    }

    fun refresh(view: View) {
        var intent = Intent(this@Summary, MainActivity::class.java)
        intent.putExtra("refresh", userName)
        startActivity(intent)
    }
}