package com.example.finalprojectflashcards

//import com.example.finalprojectflashcards.MyDatabaseHelper.addImageCard
import androidx.appcompat.app.AppCompatActivity
import android.graphics.Bitmap
import android.os.Bundle
import com.example.finalprojectflashcards.R
import android.view.WindowManager
import android.content.Intent
import com.example.finalprojectflashcards.MyDatabaseHelper
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultCallback
import android.app.Activity
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import java.io.ByteArrayOutputStream
import java.io.IOException

class AddImageCard : AppCompatActivity() {
    var addBtn: Button? = null
    var selectImageBtn: Button? = null
    var imgView: ImageView? = null
    var title: EditText? = null
    var SELECT_PICTURE = 200
    private var selectedImageBitmap: Bitmap? = null
    private var PageId: String? = null
    private var PageTitle: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_image_card)
        addBtn = findViewById<Button>(R.id.updateImgCard) as Button
        imgView = findViewById(R.id.imageView)
        title = findViewById<TextView>(R.id.title_input3) as EditText
        selectImageBtn = findViewById<Button>(R.id.selectImageBtn) as Button
        title!!.requestFocus()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        selectImageBtn!!.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            launchSomeActivity.launch(intent)
        }
        addBtn!!.setOnClickListener {
            if (intent.hasExtra("PageId")) {
                PageId = intent.getStringExtra("PageId")
                PageTitle = intent.getStringExtra("PageTitle")
            } else {
                Toast.makeText(this@AddImageCard, "No data", Toast.LENGTH_SHORT).show()
            }
            if (title!!.text.toString()
                    .trim { it <= ' ' } == "" || selectedImageBitmap == null
            ) {
                Toast.makeText(this@AddImageCard, "Fields cannot be empty", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val byteArrayOutputStream = ByteArrayOutputStream()
                selectedImageBitmap!!.compress(Bitmap.CompressFormat.PNG, 10, byteArrayOutputStream)
                val bytesImage = byteArrayOutputStream.toByteArray()
                val myDB = MyDatabaseHelper(this@AddImageCard)
                myDB.addImageCard(
                    title!!.text.toString().trim { it <= ' ' },
                    bytesImage,
                    0,
                    PageId
                )
                val intent = Intent(this@AddImageCard, MainActivity::class.java)
                intent.putExtra("PageId", PageId.toString())
                intent.putExtra("PageTitle", PageTitle.toString())
                startActivity(intent)
            }
        }
    }

    var launchSomeActivity =
        registerForActivityResult<Intent, ActivityResult>(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
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
                        )
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    imgView!!.visibility = View.VISIBLE
                    imgView!!.setImageBitmap(selectedImageBitmap)
                }
            }
        }
}