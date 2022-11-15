package com.example.finalprojectflashcards

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.os.Bundle
import android.content.Intent
import com.example.finalprojectflashcards.AddActivity2
import androidx.recyclerview.widget.LinearLayoutManager
import android.widget.Toast
import android.content.DialogInterface
import android.database.Cursor
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import java.util.ArrayList

class MainActivity2 : AppCompatActivity() {
    var recyclerView: RecyclerView? = null
    var AddButton: FloatingActionButton? = null
    var deckId: ArrayList<String>? = null
    var deckTitle: ArrayList<String>? = null
    var Adapter: CustomAdapter2? = null
    var DataBase: MyDatabaseHelper? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView) as RecyclerView
        AddButton = findViewById<FloatingActionButton>(R.id.addbtn) as FloatingActionButton
        AddButton!!.setOnClickListener {
            val intent = Intent(this@MainActivity2, AddActivity2::class.java)
            startActivity(intent)
        }
        DataBase = MyDatabaseHelper(this@MainActivity2)
        deckId = ArrayList()
        deckTitle = ArrayList()
        storeDataInArrays()
        Adapter = CustomAdapter2(this@MainActivity2, this, deckId!!, deckTitle!!)
        recyclerView!!.adapter = Adapter
        recyclerView!!.layoutManager = LinearLayoutManager(this@MainActivity2)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            recreate()
        }
    }

    private fun storeDataInArrays() {
        val cursor: Cursor = DataBase?.readDeck()!!
        if (cursor.count == 0) {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show()
        } else {
            while (cursor.moveToNext()) {
                deckId!!.add(cursor.getString(0))
                deckTitle!!.add(cursor.getString(1))
            }
        }
    }

    fun storeDataInArrays1() {
        val cursor: Cursor? = DataBase?.readDeckSort()
        if (cursor != null) {
            if (cursor.count == 0) {
                Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show()
            } else {
                while (cursor.moveToNext()) {
                    deckId!!.add(cursor.getString(0))
                    deckTitle!!.add(cursor.getString(1))
                }
            }
        }
    }

    fun confirmDeleteDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete entire deck")
        builder.setMessage("Are you sure you want to delete entire deck ?")
        builder.setPositiveButton("Yes") { dialogInterface: DialogInterface?, i: Int ->
            val Database = MyDatabaseHelper(this@MainActivity2)
            Database.deleteAllDeck()
            Clear()
            storeDataInArrays()
            Adapter = CustomAdapter2(this@MainActivity2, this@MainActivity2, deckId!!, deckTitle!!)
            recyclerView!!.adapter = Adapter
            recyclerView!!.layoutManager = LinearLayoutManager(this@MainActivity2)
            Toast.makeText(this@MainActivity2, "Deleted all decks", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No") { dialogInterface: DialogInterface?, i: Int -> }
        builder.create().show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.my_menu2, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun Clear() {
        deckId!!.clear()
        deckTitle!!.clear()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete_all) {
            confirmDeleteDialog()
        }
        if (item.itemId == R.id.sort1) {
            Clear()
            storeDataInArrays()
            Adapter = CustomAdapter2(this@MainActivity2, this, deckId!!, deckTitle!!)
            recyclerView!!.adapter = Adapter
            recyclerView!!.layoutManager = LinearLayoutManager(this@MainActivity2)
            Toast.makeText(this, "Sort by Last added", Toast.LENGTH_SHORT).show()
        }
        if (item.itemId == R.id.sort3) {
            Clear()
            storeDataInArrays1()
            Adapter = CustomAdapter2(this@MainActivity2, this, deckId!!, deckTitle!!)
            recyclerView!!.adapter = Adapter
            recyclerView!!.layoutManager = LinearLayoutManager(this@MainActivity2)
            Toast.makeText(this, "Sort by Last added", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }
}