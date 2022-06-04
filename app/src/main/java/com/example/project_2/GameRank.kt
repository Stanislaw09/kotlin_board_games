package com.example.project_2

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.widget.*
import com.example.project_2.databinding.ActivityGameRankBinding

class GameRank : AppCompatActivity() {
    private lateinit var binding: ActivityGameRankBinding
    private lateinit var list:ListView
    var gameName: String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_rank)
        binding = ActivityGameRankBinding.inflate(layoutInflater)
        setContentView(binding.root)

        list=findViewById(R.id.list)

        var p  =  intent.getStringExtra("gameName");
        if (p != null) {
            gameName=p.toString()
            println(gameName)
        }

        val game=findGame(gameName)
        val positions=game?.position?.split(",")

        if (positions != null) {
            addTable(positions)
        }

        var items= arrayOf("positions of $gameName | Date")

        if (positions != null) {
            for(p in positions){
                items+=p
            }
        }

        val adapter=ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        list.adapter=adapter
    }

    fun findGame(name:String): Game? {
        val gamesDb=GamesDatabase(this, null, 1)
        val foundGame=gamesDb.findGame(name)
        return foundGame
    }

    private fun addTable(positions : List<String>){

    }
}
