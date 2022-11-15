package com.example.finalprojectflashcards

//import com.example.finalprojectflashcards.MyDatabaseHelper.readImageTable1
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.graphics.Bitmap
import android.os.Bundle
import com.example.finalprojectflashcards.MyDatabaseHelper
import android.database.CursorWindow
import android.graphics.BitmapFactory
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import java.lang.Exception

class Test : AppCompatActivity() {
    var imgView: ImageView? = null
    var id: String? = null
    var title: String? = null
    var count: String? = null
    var bitmapImage: Bitmap? = null
    var bytesImage: ByteArray? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        imgView = findViewById<View>(R.id.imageView3) as ImageView
        intentData
        this@Test.setTitle(title)
    }

    val intentData: Unit
        @SuppressLint("DiscouragedPrivateApi")
        get() {
            if (intent.hasExtra("id")) {
                id = intent.getStringExtra("id")
                title = intent.getStringExtra("title")
                val mydb = MyDatabaseHelper(this@Test)
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