package com.chiragjn.gamecounter

import android.os.Bundle
import android.util.Log
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity
import com.chiragjn.gamecounter.adapters.FinalScoreGridAdapter
import com.chiragjn.gamecounter.adapters.PlayerNameGridAdapter
import com.chiragjn.gamecounter.adapters.RoundScoreGridAdapter

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
        connectAdapters()
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

    private fun connectAdapters() {
        val playerNamesStr = setPlayerNames()
        val playerNameAdapter = PlayerNameGridAdapter(this, playerNamesStr)
        playerNames.adapter = playerNameAdapter

//        val roundScoreAdapter = RoundScoreGridAdapter(this)
//        roundScores.adapter = roundScoreAdapter
//
//        val finalScoreAdapter = FinalScoreGridAdapter(this)
//        finalScores.adapter = finalScoreAdapter
    }

    private fun setPlayerNames(): Array<String?> {
        val playerNamesStr = arrayOfNulls<String>(numPlayers)
        for (i in 0 until numPlayers) {
            playerNamesStr[i] = "P" + (i+1)
        }
        return playerNamesStr
    }
}