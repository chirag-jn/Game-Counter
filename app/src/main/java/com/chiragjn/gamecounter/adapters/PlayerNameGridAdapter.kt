package com.chiragjn.gamecounter.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.chiragjn.gamecounter.R

internal class PlayerNameGridAdapter(
    private val context: Context,
    private val playerNames: Array<String?>
) : BaseAdapter() {

    private var layoutInflater: LayoutInflater? = null
    private lateinit var textView: TextView

    override fun getCount(): Int {
        return playerNames.size
    }

    override fun getItem(position: Int): String? {
        return playerNames[position]
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
            cView = layoutInflater!!.inflate(R.layout.grid_name_item, null)
        }
        textView = cView!!.findViewById(R.id.grid_name_item_tv)
        textView.text = playerNames[position]
        return cView
    }
}