package com.chiragjn.gamecounter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.slider.Slider

class MainActivity : AppCompatActivity() {

    private lateinit var maxPointsSlider: Slider
    private lateinit var numPlayersSlider: Slider
    private lateinit var startGameBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindViews()
    }

    private fun bindViews() {
        maxPointsSlider = findViewById(R.id.max_points_slider)
        numPlayersSlider = findViewById(R.id.player_slider)
        startGameBtn = findViewById(R.id.start_button)

        startGameBtn.setOnClickListener { onStartClicked() }
    }

    private fun onStartClicked() {
        val maxPoints: Int = maxPointsSlider.value.toInt()
        val numPlayers: Int = numPlayersSlider.value.toInt()
        if (maxPoints > 0 && numPlayers > 0) {
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra(Constants.PLAYERS, numPlayers)
            intent.putExtra(Constants.POINTS_PER_ROUND, maxPoints)
            startActivity(intent)
        }
    }
}