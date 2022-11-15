package com.example.finalprojectflashcards

import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.finalprojectflashcards.MainActivity2
import com.example.finalprojectflashcards.MyDatabaseHelper

class AddActivity2 : AppCompatActivity() {
    var TitleInput: EditText? = null
    var answerInput: EditText? = null
    var pagesInput = 0
    var addCard: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add2)

        TitleInput = findViewById<TextView>(R.id.title_input) as EditText
        answerInput = findViewById<TextView>(R.id.answerInput) as EditText
        pagesInput = 0
        addCard = findViewById<Button>(R.id.addCardBtn) as Button
        TitleInput!!.requestFocus()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        addCard!!.setOnClickListener(View.OnClickListener { view: View? ->
            if (TitleInput!!.text.toString().trim { it <= ' ' } == "") {
                Toast.makeText(this@AddActivity2, "Fields cannot be empty", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val myDB = MyDatabaseHelper(this@AddActivity2)
                myDB.addDeck(TitleInput!!.text.toString().trim { it <= ' ' })
                finish()
                val intent = Intent(this@AddActivity2, MainActivity2::class.java)
                startActivity(intent)
            }
        })
    }
}