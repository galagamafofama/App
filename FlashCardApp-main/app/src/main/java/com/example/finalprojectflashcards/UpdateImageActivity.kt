package com.example.finalprojectflashcards

//import com.example.finalprojectflashcards.MyDatabaseHelper.updateImageCard
//import com.example.finalprojectflashcards.MyDatabaseHelper.deleteImageRow
//import com.example.finalprojectflashcards.MyDatabaseHelper.readImageTable1
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.graphics.Bitmap
import android.os.Bundle
import android.content.Intent
import com.example.finalprojectflashcards.MyDatabaseHelper
import android.widget.Toast
import android.content.DialogInterface
import android.graphics.BitmapFactory
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultCallback
import android.app.Activity
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import org.w3c.dom.Text
import java.io.ByteArrayOutputStream
import java.io.IOException

class UpdateImageActivity : AppCompatActivity() {
    var addBtn: Button? = null
    private var selectImageBtn: Button? = null
    var deleteBtn: Button? = null
    var imgView: ImageView? = null
    private var titletv: TextView? =null
    var SELECT_PICTURE = 200
    private var selectedImageBitmap: Bitmap? = null
    var pid: String? = null
    var ptitle: String? = null
    var title: String? = null
    var id: String? = null
    var count: String? = null
    var bytesImage: ByteArray? = null
    var bytesImage1: ByteArray? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_image)
        addBtn = findViewById<Button>(R.id.updateImgCard) as Button
        imgView = findViewById(R.id.imageView)
        titletv = findViewById<TextView>(R.id.title_input3) as TextView
        selectImageBtn = findViewById<Button>(R.id.selectImageBtn) as Button
        deleteBtn = findViewById<Button>(R.id.deleteImageBtn) as Button
        intentData
        selectImageBtn!!.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            launchSomeActivity.launch(intent)
        }
        addBtn!!.setOnClickListener {
            val DataBase = MyDatabaseHelper(this@UpdateImageActivity)
            if (bytesImage1 != null) {
                count?.let { Integer.valueOf(it) }?.let {
                    DataBase.updateImageCard(
                        titletv!!.text.toString(),
                        bytesImage1,
                        it,
                        id!!
                    )
                }
                Toast.makeText(this@UpdateImageActivity, "Successfully updated", Toast.LENGTH_SHORT)
                    .show()
            } else if (bytesImage != null) {
                count?.let { Integer.valueOf(it) }?.let {
                    DataBase.updateImageCard(
                        titletv!!.text.toString(),
                        bytesImage,
                        it,
                        id!!
                    )
                }
                Toast.makeText(this@UpdateImageActivity, "Successfully updated", Toast.LENGTH_SHORT)
                    .show()
            }
            finish()
        }
        deleteBtn!!.setOnClickListener { confirmDialog() }
    }

    fun confirmDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete$title ?")
        builder.setMessage("Are you sure you want to delete $title ?")
        builder.setPositiveButton("Yes") { dialogInterface: DialogInterface?, i: Int ->
            val DataBase = MyDatabaseHelper(this@UpdateImageActivity)
            DataBase.deleteImageRow(id!!)
            finish()
        }
        builder.setNegativeButton("No") { dialogInterface: DialogInterface?, i: Int -> }
        builder.create().show()
    }

    val intentData: Unit
        get() {
            if (intent.hasExtra("id")) {
                id = intent.getStringExtra("id")
                title = intent.getStringExtra("title")
                count = intent.getStringExtra("count")
                titletv!!.text = title
                val DataBase = MyDatabaseHelper(this@UpdateImageActivity)
                bytesImage = DataBase.readImageTable1(id)
                if (bytesImage != null) {
                    val bitmapImage =
                        BitmapFactory.decodeByteArray(bytesImage, 0, bytesImage!!.size)
                    imgView!!.setImageBitmap(bitmapImage)
                } else {
                    Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "No data ", Toast.LENGTH_SHORT).show()
            }
        }
    private var launchSomeActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data
                if (data != null
                    && data.data != null
                ) {
                    val selectedImageUri = data.data
                    try {
                        selectedImageBitmap = MediaStore.Images.Media.getBitmap(
                            this.contentResolver,
                            selectedImageUri
                        ) as Bitmap
                        val byteArrayOutputStream = ByteArrayOutputStream()
                        selectedImageBitmap!!.compress(
                            Bitmap.CompressFormat.PNG,
                            0,
                            byteArrayOutputStream
                        )
                        bytesImage1 = byteArrayOutputStream.toByteArray()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    imgView!!.setImageBitmap(selectedImageBitmap)
                }
            }
        }
}