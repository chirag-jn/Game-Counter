package com.chiragjn.gamecounter

import android.os.Bundle
import android.util.Log
import android.widget.AdapterView.OnItemClickListener
import android.widget.GridView
import android.widget.NumberPicker
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.chiragjn.gamecounter.adapters.FinalScoreGridAdapter
import com.chiragjn.gamecounter.adapters.PlayerNameGridAdapter
import com.chiragjn.gamecounter.adapters.RoundScoreGridAdapter
import com.google.android.material.switchmaterial.SwitchMaterial

class GameActivity : AppCompatActivity() {

    private lateinit var playerNamesGView: GridView
    private lateinit var roundScoresGView: GridView
    private lateinit var finalScoresGView: GridView

    private var roundScoreAdapter: RoundScoreGridAdapter? = null
    private var finalScoreAdapter: FinalScoreGridAdapter? = null

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

        playerNamesGView = findViewById(R.id.player_names)
        roundScoresGView = findViewById(R.id.round_scores)
        finalScoresGView = findViewById(R.id.final_scores)

        if (numPlayers > 0) {
            playerNamesGView.numColumns = numPlayers
            roundScoresGView.numColumns = numPlayers
            finalScoresGView.numColumns = numPlayers
        }
    }

    private fun connectAdapters() {
        val playerNamesStr = setPlayerNames()
        val playerNameAdapter = PlayerNameGridAdapter(this, playerNamesStr)
        playerNamesGView.adapter = playerNameAdapter

        roundScoreAdapter = RoundScoreGridAdapter(this, numPlayers)
        roundScoresGView.adapter = roundScoreAdapter
        roundScoreAdapter!!.createNewRow()
        roundScoreInitOnClickListener()

        val finalScoreInt = setFinalScore()
        finalScoreAdapter = FinalScoreGridAdapter(this, finalScoreInt)
        finalScoresGView.adapter = finalScoreAdapter
    }

    private fun setPlayerNames(): Array<String?> {
        val playerNamesStr = arrayOfNulls<String>(numPlayers)
        for (i in 0 until numPlayers) {
            playerNamesStr[i] = "P" + (i + 1)
        }
        return playerNamesStr
    }

    private fun setFinalScore(): Array<Int?> {
        val playerNamesStr = arrayOfNulls<Int>(numPlayers)
        for (i in 0 until numPlayers) {
            playerNamesStr[i] = 0
        }
        return playerNamesStr
    }

    private fun roundScoreInitOnClickListener() {
        roundScoresGView.onItemClickListener = OnItemClickListener { _, _, position, _ ->
            showNumChangeDialog(position)
        }
    }

    private fun showNumChangeDialog(position: Int) {
        val builder = AlertDialog.Builder(this)

        val view = layoutInflater.inflate(R.layout.score_input_dialog, null)
        val slider: NumberPicker = view.findViewById(R.id.score_slider)
        val isPositiveSwitch: SwitchMaterial = view.findViewById(R.id.is_positive_switch)

        slider.minValue = 0
        slider.maxValue = maxScorePerRound
        val curValue = roundScoreAdapter!!.getItem(position)
        if (curValue == "+") {
            slider.value = 0
        } else {
            slider.value = curValue.toInt()
        }

        var isPositiveMultiplier: Int =
            if (isPositiveSwitch.isChecked) 1
            else -1

        isPositiveSwitch.setOnCheckedChangeListener { _, isChecked ->
            isPositiveMultiplier =
                if (isChecked) 1
                else -1
        }

        builder.setView(view)

        var input: Int

        builder.setPositiveButton("Done") { _, _ ->
            input = slider.value
            val playerFinalScore =
                roundScoreAdapter!!.updateScore(position, input * isPositiveMultiplier)
            roundScoreAdapter!!.checkLastElems()
            finalScoreAdapter!!.updateScore(position % numPlayers, playerFinalScore)
        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }
}