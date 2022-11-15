package com.example.finalprojectflashcards

import android.app.Activity
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.TextView
import android.widget.LinearLayout
import java.util.ArrayList

class CustomAdapter2 internal constructor(
    var activity: Activity,
    private val context: Context,
    private val card_id: ArrayList<*>,
    private val card_title: ArrayList<*>
) : RecyclerView.Adapter<CustomAdapter2.MyViewHolder>() {
    private val card_answer: ArrayList<*>? = null
    private val card_count: ArrayList<*>? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view: View = layoutInflater.inflate(R.layout.my_row2, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        holder.card_id_txt.text = card_id[position].toString()
        holder.card_title_txt.text = card_title[position].toString()
        holder.mainLayout.setOnClickListener { view: View? ->
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra("pid", card_id[position].toString())
            intent.putExtra("ptitle", card_title[position].toString())
            activity.startActivityForResult(intent, 1)
        }
    }

    override fun getItemCount(): Int {
        return card_id.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var card_id_txt: TextView
        var card_title_txt: TextView
        var card_answer_txt: TextView? = null
        var card_count_txt: TextView? = null
        var mainLayout: LinearLayout

        init {
            card_id_txt = itemView.findViewById(R.id.card_id_txt)
            card_title_txt = itemView.findViewById(R.id.card_title_txt_view)
            mainLayout = itemView.findViewById(R.id.mainLayout)
        }
    }
}