package com.example.project_2

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.project_2.databinding.ActivitySummaryBinding

class Summary : AppCompatActivity() {
    var userName: String=""
    private lateinit var binding: ActivitySummaryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)
        binding = ActivitySummaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras = intent.extras

        var name  = extras?.getString("userName");
        var gamesNum  =  extras?.getInt("numOfGames");

        if (name != null) {
            userName=name.toString()
            println("games number in summary is " + gamesNum)
        }

        binding.userName.text=name.toString()
        binding.gamesNum.text=gamesNum.toString()
    }

    fun synchronizeData(view: View) {}
    fun clearData(view: View) {}
}