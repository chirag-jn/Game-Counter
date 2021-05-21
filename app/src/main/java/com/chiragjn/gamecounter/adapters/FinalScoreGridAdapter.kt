package com.chiragjn.gamecounter.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.chiragjn.gamecounter.R

internal class FinalScoreGridAdapter(
    private val context: Context,
    private val playerScores: Array<Int?>
) : BaseAdapter() {

    private var layoutInflater: LayoutInflater? = null
    private lateinit var textView: TextView

    override fun getCount(): Int {
        return playerScores.size
    }

    override fun getItem(position: Int): Int? {
        return playerScores[position]
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
            cView = layoutInflater!!.inflate(R.layout.grid_final_score_item, null)
        }
        textView = cView!!.findViewById(R.id.grid_final_score_item_tv)
        textView.text = playerScores[position].toString()
        return cView
    }
}