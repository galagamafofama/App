package com.example.finalprojectflashcards

import com.example.finalprojectflashcards.MyDatabaseHelper
import android.view.View

import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import android.content.Intent
import android.widget.Button
import android.widget.TextView

class AddActivity : AppCompatActivity() {
    private var titleInput: EditText? = null
    var answerInput: EditText? = null
    var pagesInput = 0
    var pageID: String? = null
    var pageTitle: String? = null
    var addCard: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        titleInput = findViewById<TextView>(R.id.title_input) as EditText
        answerInput = findViewById<TextView>(R.id.answerInput) as EditText
        pagesInput = 0
        addCard = findViewById<Button>(R.id.addCardBtn) as Button
        titleInput!!.requestFocus()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        addCard!!.setOnClickListener {
            if (intent.hasExtra("pid")) {
                pageID = intent.getStringExtra("pid")
                pageTitle = intent.getStringExtra("ptitle")
            } else {
                Toast.makeText(this@AddActivity, "No data", Toast.LENGTH_SHORT).show()
            }
            if (titleInput!!.text.toString().trim { it <= ' ' } == "" || answerInput!!.text
                    .toString().trim { it <= ' ' } == "") {
                Toast.makeText(this@AddActivity, "Fields cannot be empty", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val DataBase = MyDatabaseHelper(this@AddActivity)
                DataBase.addBook(
                    titleInput!!.text.toString().trim { it <= ' ' },
                    answerInput!!.text.toString().trim { it <= ' ' },
                    pagesInput,
                    pageID
                )
                val intent = Intent(this@AddActivity, MainActivity::class.java)
                intent.putExtra("pageid", pageID.toString())
                intent.putExtra("ptitle", pageTitle.toString())
                startActivity(intent)
            }
        }
    }
}