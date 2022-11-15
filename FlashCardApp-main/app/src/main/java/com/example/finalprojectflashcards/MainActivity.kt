package com.example.finalprojectflashcards

import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.database.CursorWindow
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    var recyclerView: RecyclerView? = null
    var addButton: FloatingActionButton? = null
    var addButton2: FloatingActionButton? = null
    var Database: MyDatabaseHelper? = null
    var cardId: ArrayList<String>? = null
    var cardTitle: ArrayList<String>? = null
    var cardAnswer: ArrayList<String>? = null
    private var cardCount: ArrayList<String>? = null
    private var Adapter: CustomAdapter? = null
    var pageID: String? = null
    var pageTitle: String? = null
    var warn0 = false
    var warn1 = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView) as RecyclerView
        addButton = findViewById<FloatingActionButton>(R.id.addbtn) as FloatingActionButton
        addButton2 = findViewById<FloatingActionButton>(R.id.addbtn2) as FloatingActionButton
        addButton!!.setOnClickListener {
            val intent = Intent(this@MainActivity, AddActivity::class.java)
            intent.putExtra("pid", pageID.toString())
            intent.putExtra("ptitle", pageTitle.toString())
            startActivity(intent)
        }
        addButton2!!.setOnClickListener {
            val intent = Intent(this@MainActivity, AddImageCard::class.java)
            intent.putExtra("pid", pageID.toString())
            intent.putExtra("ptitle", pageTitle.toString())
            startActivity(intent)
        }
        if (intent.hasExtra("pid")) {
            pageID = intent.getStringExtra("pid")
            pageTitle = intent.getStringExtra("ptitle")
            this@MainActivity.title = "Deck: $pageTitle"
        } else {
            Toast.makeText(this, "No data ", Toast.LENGTH_SHORT).show()
        }
        Database = MyDatabaseHelper(this@MainActivity)
        cardId = ArrayList()
        cardTitle = ArrayList()
        cardAnswer = ArrayList()
        cardCount = ArrayList()
        try {
            val field = CursorWindow::class.java.getDeclaredField("sCursorWindowSize")
            field.isAccessible = true
            field[null] = 100 * 1024 * 1024
        } catch (e: Exception) {
            e.printStackTrace()
        }
        readAllIdDefault(pageID)
        if (warn0) {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show()
        }
        Adapter = CustomAdapter(this@MainActivity, this, cardId!!,
            cardTitle!!, cardAnswer!!, cardCount!!
        )
        recyclerView!!.adapter = Adapter
        recyclerView!!.layoutManager = LinearLayoutManager(this@MainActivity)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            recreate()
        }
    }

    fun readAllDefault(pid: String?) {
        val cursor: Cursor = Database?.ViewAll(pid)!!
        if (cursor.count == 0) {
            warn0 = true
        } else {
            while (cursor.moveToNext()) {
                cardId!!.add(cursor.getString(0))
                cardTitle!!.add(cursor.getString(1))
                try {
                    cardAnswer!!.add(cursor.getString(2))
                } catch (e: Exception) {
                    cardAnswer!!.add("SwitchToImg")
                }
                cardCount!!.add(cursor.getString(3))
            }
        }
    }

    fun readAllDesc(pid: String?) {
        val cursor: Cursor? = Database?.ViewAllDesc(pid)
        if (cursor != null) {
            if (cursor.count == 0) {
                warn0 = true
            } else {
                while (cursor.moveToNext()) {
                    cardId!!.add(cursor.getString(0))
                    cardTitle!!.add(cursor.getString(1))
                    try {
                        cardAnswer!!.add(cursor.getString(2))
                    } catch (e: Exception) {
                        cardAnswer!!.add("SwitchToImg")
                    }
                    cardCount!!.add(cursor.getString(3))
                }
            }
        }
    }

    fun readAllIdDefault(pid: String?) {
        val cursor: Cursor? = Database?.ViewAllIdDefault(pid)
        if (cursor != null) {
            if (cursor.count == 0) {
                warn0 = true
            } else {
                while (cursor.moveToNext()) {
                    cardId!!.add(cursor.getString(0))
                    cardTitle!!.add(cursor.getString(1))
                    try {
                        cardAnswer!!.add(cursor.getString(2))
                    } catch (e: Exception) {
                        cardAnswer!!.add("SwitchToImg")
                    }
                    cardCount!!.add(cursor.getString(3))
                }
            }
        }
    }

    fun readAllIdDesc(pid: String?) {
        val cursor: Cursor? = Database?.ViewAllIdDesc(pid)
        if (cursor != null) {
            if (cursor.count == 0) {
                warn0 = true
            } else {
                while (cursor.moveToNext()) {
                    cardId!!.add(cursor.getString(0))
                    cardTitle!!.add(cursor.getString(1))
                    try {
                        cardAnswer!!.add(cursor.getString(2))
                    } catch (e: Exception) {
                        cardAnswer!!.add("SwitchToImg")
                    }
                    cardCount!!.add(cursor.getString(3))
                }
            }
        }
    }

    fun storeDataInArrays(pid: String?) {
        val cursor: Cursor? = Database?.readAllData(pid)
        if (cursor != null) {
            if (cursor.count == 0) {
                warn0 = true
            } else {
                while (cursor.moveToNext()) {
                    cardId!!.add(cursor.getString(0))
                    cardTitle!!.add(cursor.getString(1))
                    cardAnswer!!.add(cursor.getString(2))
                    cardCount!!.add(cursor.getString(3))
                }
            }
        }
    }

    fun storeDataInArrays2(pid: String?) {
        val cursor: Cursor? = Database?.sortByCount(pid)
        if (cursor != null) {
            if (cursor.count == 0) {
                Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show()
            } else {
                while (cursor.moveToNext()) {
                    cardId!!.add(cursor.getString(0))
                    cardTitle!!.add(cursor.getString(1))
                    cardAnswer!!.add(cursor.getString(2))
                    cardCount!!.add(cursor.getString(3))
                }
            }
        }
    }

    fun storeDataInArrays3(pid: String?) {
        val cursor: Cursor? = Database?.sortByCount1(pid)
        if (cursor != null) {
            if (cursor.count == 0) {
                Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show()
            } else {
                while (cursor.moveToNext()) {
                    cardId!!.add(cursor.getString(0))
                    cardTitle!!.add(cursor.getString(1))
                    cardAnswer!!.add(cursor.getString(2))
                    cardCount!!.add(cursor.getString(3))
                }
            }
        }
    }

    fun storeDataInArrays4(pid: String?) {
        val cursor: Cursor? = Database?.sortByCount2(pid)
        if (cursor != null) {
            if (cursor.count == 0) {
                Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show()
            } else {
                while (cursor.moveToNext()) {
                    cardId!!.add(cursor.getString(0))
                    cardTitle!!.add(cursor.getString(1))
                    cardAnswer!!.add(cursor.getString(2))
                    cardCount!!.add(cursor.getString(3))
                }
            }
        }
    }

    fun storeDataInArrays5(pid: String?) {
        val cursor: Cursor? = Database?.readImageTable(pid)
        if (cursor != null) {
            if (cursor.count == 0) {
                warn1 = true
            } else {
                while (cursor.moveToNext()) {
                    cardId!!.add(cursor.getString(0))
                    cardTitle!!.add(cursor.getString(1))
                    cardAnswer!!.add("SwitchToImg")
                    cardCount!!.add(cursor.getString(3))
                }
            }
        }
    }

    fun storeDataInArrays6(pid: String?) {
        val cursor: Cursor? = Database?.sortByImgCount(pid)
        if (cursor != null) {
            if (cursor.count == 0) {
                Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show()
            } else {
                while (cursor.moveToNext()) {
                    cardId!!.add(cursor.getString(0))
                    cardTitle!!.add(cursor.getString(1))
                    cardAnswer!!.add("SwitchToImg")
                    cardCount!!.add(cursor.getString(3))
                }
            }
        }
    }

    fun storeDataInArrays7(pid: String?) {
        val cursor: Cursor? = Database?.sortByImgCount1(pid)
        if (cursor != null) {
            if (cursor.count == 0) {
                Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show()
            } else {
                while (cursor.moveToNext()) {
                    cardId!!.add(cursor.getString(0))
                    cardTitle!!.add(cursor.getString(1))
                    cardAnswer!!.add("SwitchToImg")
                    cardCount!!.add(cursor.getString(3))
                }
            }
        }
    }

    fun storeDataInArrays8(pid: String?) {
        val cursor: Cursor? = Database?.sortByImgCount2(pid)
        if (cursor != null) {
            if (cursor.count == 0) {
                Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show()
            } else {
                while (cursor.moveToNext()) {
                    cardId!!.add(cursor.getString(0))
                    cardTitle!!.add(cursor.getString(1))
                    cardAnswer!!.add("SwitchToImg")
                    cardCount!!.add(cursor.getString(3))
                }
            }
        }
    }

    fun confirmDeleteAllDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete all cards")
        builder.setMessage("Are you sure you want to delete all cards in $pageTitle ?")
        builder.setPositiveButton("Yes") { dialogInterface: DialogInterface?, i: Int ->
            val Database = MyDatabaseHelper(this@MainActivity)
            Database.deleteAll(pageID)
            cardId!!.clear()
            cardTitle!!.clear()
            cardAnswer!!.clear()
            cardCount!!.clear()
            Adapter = CustomAdapter(
                this@MainActivity,
                this@MainActivity,
                cardId!!,
                cardTitle!!,
                cardAnswer!!,
                cardCount!!
            )
            recyclerView!!.adapter = Adapter
            recyclerView!!.layoutManager = LinearLayoutManager(this@MainActivity)
            Toast.makeText(this@MainActivity, "Delete all", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton(
            "No"
        ) { dialogInterface: DialogInterface?, i: Int -> }
        builder.create().show()
    }

    fun confirmDeleteDeck() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete entire deck")
        builder.setMessage("Are you sure you want to delete entire deck ?")
        builder.setPositiveButton("Yes") { dialogInterface: DialogInterface?, i: Int ->
            val Database = MyDatabaseHelper(this@MainActivity)
            pageID?.let { Database.deleteDeck(it) }
            Toast.makeText(this@MainActivity, "Deck $pageTitle deleted", Toast.LENGTH_SHORT)
                .show()
            finish()
        }
        builder.setNegativeButton(
            "No"
        ) { dialogInterface: DialogInterface?, i: Int -> }
        builder.create().show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.my_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete_all) {
            confirmDeleteAllDialog()
        } else if (item.itemId == R.id.sort) {
            cardId!!.clear()
            cardTitle!!.clear()
            cardAnswer!!.clear()
            cardCount!!.clear()
            readAllDefault(pageID)
            Adapter =
                CustomAdapter(this@MainActivity, this, cardId!!, cardTitle!!,
                    cardAnswer!!, cardCount!!
                )
            recyclerView!!.adapter = Adapter
            recyclerView!!.layoutManager = LinearLayoutManager(this@MainActivity)
            Toast.makeText(this, "Sort by count asc", Toast.LENGTH_SHORT).show()
        } else if (item.itemId == R.id.sort1) {
            cardId!!.clear()
            cardTitle!!.clear()
            cardAnswer!!.clear()
            cardCount!!.clear()
            readAllIdDefault(pageID)
            Adapter =
                CustomAdapter(this@MainActivity, this, cardId!!, cardTitle!!,
                    cardAnswer!!, cardCount!!
                )
            recyclerView!!.adapter = Adapter
            recyclerView!!.layoutManager = LinearLayoutManager(this@MainActivity)
            Toast.makeText(this, "Default Sort", Toast.LENGTH_SHORT).show()
        } else if (item.itemId == R.id.sort4) {
            cardId!!.clear()
            cardTitle!!.clear()
            cardAnswer!!.clear()
            cardCount!!.clear()
            readAllIdDefault(pageID)
            Adapter =
                CustomAdapter(this@MainActivity, this,
                    cardId!!, cardTitle!!, cardAnswer!!, cardCount!!)
            recyclerView!!.adapter = Adapter
            recyclerView!!.layoutManager = LinearLayoutManager(this@MainActivity)
            Toast.makeText(this, "Sort by first added", Toast.LENGTH_SHORT).show()
        } else if (item.itemId == R.id.sort2) {
            cardId!!.clear()
            cardTitle!!.clear()
            cardAnswer!!.clear()
            cardCount!!.clear()
            readAllDesc(pageID)
            Adapter =
                CustomAdapter(this@MainActivity, this, cardId!!, cardTitle!!,
                    cardAnswer!!, cardCount!!
                )
            recyclerView!!.adapter = Adapter
            recyclerView!!.layoutManager = LinearLayoutManager(this@MainActivity)
            Toast.makeText(this, "Sort by count desc", Toast.LENGTH_SHORT).show()
        } else if (item.itemId == R.id.sort3) {
            cardId!!.clear()
            cardTitle!!.clear()
            cardAnswer!!.clear()
            cardCount!!.clear()
            readAllIdDesc(pageID)
            Adapter =
                CustomAdapter(this@MainActivity, this, cardId!!, cardTitle!!,
                    cardAnswer!!, cardCount!!
                )
            recyclerView!!.adapter = Adapter
            recyclerView!!.layoutManager = LinearLayoutManager(this@MainActivity)
            Toast.makeText(this, "Sort by last added", Toast.LENGTH_SHORT).show()
        } else if (item.itemId == R.id.delete_deck) {
            confirmDeleteDeck()
        }
        return super.onOptionsItemSelected(item)
    }
}