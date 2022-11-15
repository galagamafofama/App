package com.example.finalprojectflashcards

//import com.example.finalprojectflashcards.MyDatabaseHelper.updateData
//import com.example.finalprojectflashcards.MyDatabaseHelper.updateData2
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.annotation.SuppressLint
import android.os.Bundle
import com.example.finalprojectflashcards.MyDatabaseHelper
import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.example.finalprojectflashcards.UpdateActivity
import android.widget.Toast

class ViewActivity : AppCompatActivity() {
    var id: String? = null
    var title: String? = null
    var answer: String? = null
    var count: String? = null
    var question: TextView? = null
    var answered: TextView? = null
    var countTV: TextView? = null
    var reveal: Button? = null
    var update: Button? = null
    var reset: Button? = null
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)
        question = findViewById<TextView>(R.id.question_tv) as TextView
        answered = findViewById<TextView>(R.id.answer_tv) as TextView
        countTV = findViewById<TextView>(R.id.count_tv) as TextView
        reveal = findViewById<Button>(R.id.revealBtn) as Button
        update = findViewById<Button>(R.id.updateBtn) as Button
        reset = findViewById<Button>(R.id.resetCountBtn) as Button
        intentData
        reset!!.setOnClickListener {
            count = 0.toString()
            val DataBase = MyDatabaseHelper(this@ViewActivity)
            DataBase.updateData(id!!, title, answer, 0)
            countTV!!.text = "Count: $count"
        }
        reveal!!.setOnClickListener {
            answered!!.visibility = View.VISIBLE
            countTV!!.visibility = View.VISIBLE
            val DataBase = MyDatabaseHelper(this@ViewActivity)
            val c = count?.let { Integer.valueOf(it) + 1 }
            count = c.toString()
            if (c != null) {
                DataBase.updateData2(id!!, title, answer, c)
            }
            countTV!!.text = "Count: $count"
            reveal!!.isEnabled = false
        }
        update!!.setOnClickListener {
            val intent = Intent(this@ViewActivity, UpdateActivity::class.java)
            intent.putExtra("id", id.toString())
            intent.putExtra("title", title.toString())
            intent.putExtra("answer", answer.toString())
            intent.putExtra("count", count.toString())
            startActivity(intent)
            finish()
        }
    }

    @get:SuppressLint("SetTextI18n")
    val intentData: Unit
        get() {
            if (intent.hasExtra("id")) {
                id = intent.getStringExtra("id")
                title = intent.getStringExtra("title")
                answer = intent.getStringExtra("answer")
                count = intent.getStringExtra("count")
                question!!.text = "Question: $title"
                answered!!.text = "Answer: $answer"
            } else {
                Toast.makeText(this, "No data ", Toast.LENGTH_SHORT).show()
            }
        }
}

