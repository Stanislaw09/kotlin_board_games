package com.example.project_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.project_2.databinding.ActivityGameRankBinding

class GameRank : AppCompatActivity() {
    private lateinit var binding: ActivityGameRankBinding
    var gameName: String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_rank)
        binding = ActivityGameRankBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var p  =  intent.getStringExtra("gameName");
        if (p != null) {
            gameName=p.toString()
            println(gameName)
        }

        val game=findGame(gameName)
        println(game?.position)

        val position=game?.position
        println("pos "+position)

        binding.currentRank.text=position.toString()
    }

    fun findGame(name:String): Game? {
        val gamesDb=GamesDatabase(this, null, 1)
        val foundGame=gamesDb.findGame(name)
        return foundGame
    }
}