package com.example.finalprojectflashcards

//import com.example.finalprojectflashcards.MyDatabaseHelper.updateData
//import com.example.finalprojectflashcards.MyDatabaseHelper.deleteRow
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.os.Bundle
import com.example.finalprojectflashcards.MyDatabaseHelper
import android.widget.Toast
import android.content.DialogInterface
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

class UpdateActivity : AppCompatActivity() {
    private var title_input: EditText? = null
    private var author_input: EditText? = null
    private var pages_input: EditText? = null
    var updateButton: Button? = null
    var deleteButton: Button? = null
    var testButton: Button? = null
    var id: String? = null
    var title: String? = null
    var author: String? = null
    var pages: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
        title_input = findViewById<TextView>(R.id.title_input2) as EditText
        author_input = findViewById<TextView>(R.id.author_input2) as EditText
        pages_input = findViewById<TextView>(R.id.numpages_input2) as EditText
        updateButton = findViewById<Button>(R.id.updateBtn) as Button
        deleteButton = findViewById<Button>(R.id.deleteBtn2) as Button
        intentData
        val ab = supportActionBar
        ab?.title = title
        updateButton!!.setOnClickListener {
            val DataBase = MyDatabaseHelper(this@UpdateActivity)
            DataBase.updateData(
                id!!,
                title_input!!.text.toString().trim { it <= ' ' },
                author_input!!.text.toString().trim { it <= ' ' },
                Integer.valueOf(pages_input!!.text.toString().trim { it <= ' ' })
            )
            finish()
        }
        deleteButton!!.setOnClickListener { confirmDialog() }
    }

    val intentData: Unit
        get() {
            if (intent.hasExtra("id")) {
                id = intent.getStringExtra("id")
                title = intent.getStringExtra("title")
                author = intent.getStringExtra("answer")
                pages = intent.getStringExtra("count")
                title_input!!.setText(title)
                author_input!!.setText(author)
                pages_input!!.setText(pages)
            } else {
                Toast.makeText(this, "No data ", Toast.LENGTH_SHORT).show()
            }
        }

    fun confirmDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete$title ?")
        builder.setMessage("Are you sure you want to delete $title ?")
        builder.setPositiveButton("Yes") { dialogInterface: DialogInterface?, i: Int ->
            val DataBase = MyDatabaseHelper(this@UpdateActivity)
            DataBase.deleteRow(id!!)
            finish()
        }
        builder.setNegativeButton("No") { dialogInterface: DialogInterface?, i: Int -> }
        builder.create().show()
    }
}