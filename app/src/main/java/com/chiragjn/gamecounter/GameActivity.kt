package com.chiragjn.gamecounter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridView

class GameActivity : AppCompatActivity() {

    private lateinit var playerNames: GridView
    private lateinit var roundScores: GridView
    private lateinit var finalScores: GridView

    var numPlayers = 0
    var maxScorePerRound = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        bindViews()
    }

    private fun bindViews() {
        val arguments = requireNotNull(intent?.extras)
        with(arguments) {
            numPlayers = getInt(Constants.PLAYERS)
            maxScorePerRound = getInt(Constants.POINTS_PER_ROUND)
        }

        playerNames = findViewById(R.id.player_names)
        roundScores = findViewById(R.id.round_scores)
        finalScores = findViewById(R.id.final_scores)

        if (numPlayers > 0) {
            playerNames.numColumns = numPlayers
            roundScores.numColumns = numPlayers
            finalScores.numColumns = numPlayers
        }
    }
}