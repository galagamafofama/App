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

class CustomAdapter internal constructor(
    var activity: Activity,
    private val context: Context,
    private val card_id: ArrayList<*>,
    private val card_title: ArrayList<*>,
    private val card_answer: ArrayList<*>,
    private val card_count: ArrayList<*>
) : RecyclerView.Adapter<CustomAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view: View = layoutInflater.inflate(R.layout.my_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        holder.card_id_txt.text = card_id[position].toString()
        holder.card_title_txt.text = card_title[position].toString()
        holder.card_count_txt.text = card_count[position].toString()
        holder.mainLayout.setOnClickListener { view: View? ->
            if (card_answer[position].toString() == "SwitchToImg") {
                val intent = Intent(context, ImageCardView::class.java)
                intent.putExtra("id", card_id[position].toString())
                intent.putExtra("title", card_title[position].toString())
                intent.putExtra("count", card_count[position].toString())
                activity.startActivityForResult(intent, 1)
            } else {
                val intent = Intent(context, ViewActivity::class.java)
                intent.putExtra("id", card_id[position].toString())
                intent.putExtra("title", card_title[position].toString())
                intent.putExtra("answer", card_answer[position].toString())
                intent.putExtra("count", card_count[position].toString())
                activity.startActivityForResult(intent, 1)
            }
        }
    }

    override fun getItemCount(): Int {
        return card_id.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var card_id_txt: TextView
        var card_title_txt: TextView
        var card_count_txt: TextView
        var mainLayout: LinearLayout

        init {
            card_id_txt = itemView.findViewById(R.id.card_id_txt)
            card_title_txt = itemView.findViewById(R.id.card_title_txt_view)
            card_count_txt = itemView.findViewById(R.id.card_count_txt_view)
            mainLayout = itemView.findViewById(R.id.mainLayout)
        }
    }
}