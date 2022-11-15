package com.example.finalprojectflashcards

import androidx.appcompat.app.AppCompatActivity
import android.graphics.Bitmap
import android.annotation.SuppressLint
import android.os.Bundle
import com.example.finalprojectflashcards.MyDatabaseHelper
import android.content.Intent
import com.example.finalprojectflashcards.UpdateImageActivity
import android.database.CursorWindow
import android.graphics.BitmapFactory
import android.view.View
import android.widget.*
import java.lang.Exception

class ImageCardView : AppCompatActivity() {
    var QuestionTV: TextView? = null
    var countTV: TextView? = null
    var imgView: ImageView? = null
    var id: String? = null
    var title: String? = null
    var count: String? = null
    var reveal: Button? = null
    var update: Button? = null
    var reset: Button? = null
    var bitmapImage: Bitmap? = null
    var bytesImage: ByteArray? = null
    private var zoomOut = false
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_card_view)
        QuestionTV = findViewById<TextView>(R.id.question_tv) as TextView
        countTV = findViewById<TextView>(R.id.count_tv) as TextView
        imgView = findViewById<TextView>(R.id.imageView2) as ImageView
        reveal = findViewById<Button>(R.id.revealBtn) as Button
        update = findViewById<Button>(R.id.updateViewBtn) as Button
        reset = findViewById<Button>(R.id.resetCountBtn) as Button
        intentData
        reveal!!.setOnClickListener { view: View? ->
            imgView!!.visibility = View.VISIBLE
            countTV!!.visibility = View.VISIBLE
            val dataBase = MyDatabaseHelper(this@ImageCardView)
            val c = (count?.toInt() ?: +1)
            count = c.toString()
            id?.let { dataBase.updateImageCard1(title, bytesImage, c, it) }
            countTV!!.text = "Count: $count"
            reveal!!.isEnabled = false
        }
        update!!.setOnClickListener {
            val intent = Intent(this@ImageCardView, UpdateImageActivity::class.java)
            intent.putExtra("id", id.toString())
            intent.putExtra("title", title.toString())
            intent.putExtra("count", count.toString())
            startActivity(intent)
            finish()
        }
        imgView!!.setOnClickListener {
            val intent = Intent(this@ImageCardView, UpdateImageActivity::class.java)
            intent.putExtra("id", id.toString())
            intent.putExtra("title", title.toString())
            intent.putExtra("count", count.toString())
            startActivity(intent)
        }
        reset!!.setOnClickListener(View.OnClickListener { view: View? ->
            count = 0.toString()
            var mydb = MyDatabaseHelper(this@ImageCardView)
            mydb.resetCountImgTable(id)
            countTV!!.text = "Count: $count"
        })
    }

    val intentData: Unit
        @SuppressLint("DiscouragedPrivateApi", "SetTextI18n")
        get() {
            if (intent.hasExtra("id")) {
                id = intent.getStringExtra("id")
                title = intent.getStringExtra("title")
                count = intent.getStringExtra("count")
                QuestionTV!!.text = "Question: $title"
                val mydb = MyDatabaseHelper(this@ImageCardView)
                try {
                    val field = CursorWindow::class.java.getDeclaredField("sCursorWindowSize")
                    field.isAccessible = true
                    field[null] = 100 * 1024 * 1024
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                bytesImage = mydb.readImageTable1(id)
                if (bytesImage != null) {
                    bitmapImage = BitmapFactory.decodeByteArray(bytesImage, 0, bytesImage!!.size)
                    imgView!!.setImageBitmap(bitmapImage)
                } else {
                    Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "No data ", Toast.LENGTH_SHORT).show()
            }
        }
}