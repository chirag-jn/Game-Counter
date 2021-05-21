package com.chiragjn.gamecounter.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.chiragjn.gamecounter.R

internal class RoundScoreGridAdapter(private val context: Context, private val numPlayers: Int) :
    BaseAdapter() {

    private var scores: ArrayList<String>? = ArrayList()
    private var layoutInflater: LayoutInflater? = null
    private lateinit var textView: TextView

    override fun getCount(): Int {
        return scores!!.size
    }

    override fun getItem(position: Int): Any {
        return scores!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var cView = convertView
        if (layoutInflater == null) {
            layoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
        if (cView == null) {
            cView = layoutInflater!!.inflate(R.layout.grid_score_item, null)
        }
        textView = cView!!.findViewById(R.id.grid_score_item_tv)
        textView.text = scores!![position]
        return cView
    }

    fun getPlayerTotalScore(playerNum: Int): Int {
        var totalScore = 0
        for (i in 0 until scores!!.size) {
            if (i % numPlayers == playerNum) {
                totalScore += scores!![i].toInt()
            }
        }
        return totalScore
    }

    fun createNewRow() {
        for (i in 0 until numPlayers) {
            scores!!.add("+")
        }
        notifyDataSetChanged()
    }

    fun updateScore(position: Int, score: Int): Int {
        scores!![position] = score.toString()
        notifyDataSetChanged()
        return getPlayerTotalScore(position % numPlayers)
    }

    fun checkLastElems() {
        var allDone = true
        for (i in scores!!.size - 1 downTo scores!!.size - numPlayers) {
            if (scores!![i] == "+") {
                allDone = false
            }
        }
        if (allDone) {
            createNewRow()
        }
    }

    override fun notifyDataSetChanged() {
        super.notifyDataSetChanged()
    }
}