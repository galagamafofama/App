package com.example.finalprojectflashcards

import android.annotation.SuppressLint
import android.database.sqlite.SQLiteOpenHelper
import com.example.finalprojectflashcards.MyDatabaseHelper
import android.database.sqlite.SQLiteDatabase
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.widget.Toast
import android.database.CursorWindow
import java.lang.Exception

class MyDatabaseHelper(private val context: Context?) : SQLiteOpenHelper(
    context, DATABASE_NAME, null, DATABASE_VERSION
) {
    override fun onOpen(db: SQLiteDatabase) {
        super.onOpen(db)
        db.execSQL("PRAGMA foreign_keys=ON")
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("PRAGMA foreign_keys=ON")
        val query =
            ("CREATE TABLE " + PARENT_TABLE_NAME + " (" + PARENT_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + PARENT_TITLE + " TEXT );")
        db.execSQL(query)
        val query1 =
            ("CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_TITLE + " TEXT, "
                    + COLUMN_ANSWER + " TEXT, "
                    + COLUMN_COUNT + " INTEGER ,"
                    + COLUMN_REFER + " INTEGER REFERENCES " + PARENT_TABLE_NAME + " ( " + PARENT_COLUMN_ID + " ) ON DELETE CASCADE"
                    + ");")
        db.execSQL(query1)
        val query2 =
            ("CREATE TABLE " + IMAGE_CARD_TABLE + " (" + IMG_TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + IMG_TABLE_TITLE + " TEXT, "
                    + IMG_TABLE_IMAGE + " BLOB, "
                    + COLUMN_COUNT + " INTEGER ,"
                    + COLUMN_REFER + " INTEGER REFERENCES " + PARENT_TABLE_NAME + " ( " + PARENT_COLUMN_ID + " ) ON DELETE CASCADE"
                    + ");")
        db.execSQL(query2)
    }

    override fun onUpgrade(db: SQLiteDatabase, i: Int, i1: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        db.execSQL("DROP TABLE IF EXISTS " + PARENT_TABLE_NAME)
        db.execSQL("DROP TABLE IF EXISTS " + IMAGE_CARD_TABLE)
    }

    fun addDeck(title: String?) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(PARENT_TITLE, title)
        val result = db.insert(PARENT_TABLE_NAME, null, cv)
        if (result == -1L) {
            Toast.makeText(context, "Failed to add deck", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Deck successfully added", Toast.LENGTH_SHORT).show()
        }
    }

    fun addImageCard(title: String?, bytesImage: ByteArray?, v: Int, pid: String?) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(IMG_TABLE_TITLE, title)
        cv.put(IMG_TABLE_IMAGE, bytesImage)
        cv.put(IMG_COLUMN_COUNT, v)
        cv.put(COLUMN_REFER, pid)
        val result = db.insert(IMAGE_CARD_TABLE, null, cv)
        if (result == -1L) {
            Toast.makeText(context, "Failed to add card", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Image Card Successfully Added", Toast.LENGTH_SHORT).show()
        }
    }

    fun updateImageCard(title: String?, bytesImage: ByteArray?, v: Int, row_id: String) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(IMG_TABLE_TITLE, title)
        cv.put(IMG_TABLE_IMAGE, bytesImage)
        cv.put(IMG_COLUMN_COUNT, v)
        val result = db.update(IMAGE_CARD_TABLE, cv, "_id=?", arrayOf(row_id)).toLong()
        if (result == -1L) {
            Toast.makeText(context, "Failed to update card", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Image Card Successfully Updated", Toast.LENGTH_SHORT).show()
        }
    }

    fun updateImageCard1(title: String?, bytesImage: ByteArray?, v: Int, row_id: String) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(IMG_TABLE_TITLE, title)
        cv.put(IMG_TABLE_IMAGE, bytesImage)
        cv.put(IMG_COLUMN_COUNT, v)
        val result = db.update(IMAGE_CARD_TABLE, cv, "_id=?", arrayOf(row_id)).toLong()
        if (result == -1L) {
            Toast.makeText(context, "Failed to update card", Toast.LENGTH_SHORT).show()
        }
    }

    fun readDeck(): Cursor? {
        val query = "SELECT * FROM " + PARENT_TABLE_NAME
        val db = this.readableDatabase
        var cursor: Cursor? = null
        if (db != null) {
            cursor = db.rawQuery(query, null)
        }
        return cursor
    }

    fun readDeckSort(): Cursor? {
        val query = "SELECT * FROM " + PARENT_TABLE_NAME + " ORDER BY " + PARENT_COLUMN_ID + " DESC"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        if (db != null) {
            cursor = db.rawQuery(query, null)
        }
        return cursor
    }

    fun addBook(title: String?, answer: String?, count: Int, pid: String?) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COLUMN_TITLE, title)
        cv.put(COLUMN_ANSWER, answer)
        cv.put(COLUMN_COUNT, count)
        cv.put(COLUMN_REFER, pid?.let { Integer.valueOf(it) })
        val result = db.insert(TABLE_NAME, null, cv)
        if (result == -1L) {
            Toast.makeText(context, "Failed to add card", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Card successfully added", Toast.LENGTH_SHORT).show()
        }
    }

    fun readAllData(pid: String?): Cursor? {
        val query =
            "SELECT * FROM $TABLE_NAME WHERE $COLUMN_REFER = " + pid?.let {
                Integer.valueOf(
                    it
                )
            }
        val db = this.readableDatabase
        var cursor: Cursor? = null
        if (db != null) {
            cursor = db.rawQuery(query, null)
        }
        return cursor
    }

    fun sortByCount(pid: String?): Cursor? {
        val query =
            "SELECT * FROM $TABLE_NAME WHERE $COLUMN_REFER = " + pid?.let {
                Integer.valueOf(
                    it
                )
            } + " ORDER BY " + COLUMN_COUNT
        val db = this.readableDatabase
        var cursor: Cursor? = null
        if (db != null) {
            cursor = db.rawQuery(query, null)
        }
        return cursor
    }

    fun sortByCount1(pid: String?): Cursor? {
        val query =
            "SELECT * FROM $TABLE_NAME WHERE $COLUMN_REFER = " + pid?.let {
                Integer.valueOf(
                    it
                )
            } + " ORDER BY " + COLUMN_COUNT + " DESC"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        if (db != null) {
            cursor = db.rawQuery(query, null)
        }
        return cursor
    }

    fun sortByCount2(pid: String?): Cursor? {
        val query =
            "SELECT * FROM $TABLE_NAME WHERE $COLUMN_REFER = " + pid?.let {
                Integer.valueOf(
                    it
                )
            } + " ORDER BY " + COLUMN_ID + " DESC"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        if (db != null) {
            cursor = db.rawQuery(query, null)
        }
        return cursor
    }

    fun sortByImgCount(pid: String?): Cursor? {
        val query =
            "SELECT * FROM $IMAGE_CARD_TABLE WHERE $COLUMN_REFER = " + pid?.let {
                Integer.valueOf(
                    it
                )
            } + " ORDER BY " + IMG_COLUMN_COUNT
        val db = this.readableDatabase
        var cursor: Cursor? = null
        if (db != null) {
            cursor = db.rawQuery(query, null)
        }
        return cursor
    }

    fun sortByImgCount1(pid: String?): Cursor? {
        val query =
            "SELECT * FROM $IMAGE_CARD_TABLE WHERE $COLUMN_REFER = " + pid?.let {
                Integer.valueOf(
                    it
                )
            } + " ORDER BY " + IMG_COLUMN_COUNT + " DESC"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        if (db != null) {
            cursor = db.rawQuery(query, null)
        }
        return cursor
    }

    fun sortByImgCount2(pid: String?): Cursor? {
        val query =
            "SELECT * FROM $IMAGE_CARD_TABLE WHERE $COLUMN_REFER = " + pid?.let {
                Integer.valueOf(
                    it
                )
            } + " ORDER BY " + IMG_TABLE_ID + " DESC"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        if (db != null) {
            cursor = db.rawQuery(query, null)
        }
        return cursor
    }

    fun readImageTable(pid: String?): Cursor? {
        val query =
            "SELECT * FROM $IMAGE_CARD_TABLE WHERE $COLUMN_REFER = " + pid?.let {
                Integer.valueOf(
                    it
                )
            }
        val db = this.readableDatabase
        var cursor: Cursor? = null
        if (db != null) {
            cursor = db.rawQuery(query, null)
        }
        return cursor
    }

    @SuppressLint("DiscouragedPrivateApi")
    fun readImageTable1(id: String?): ByteArray? {
        val query =
            "SELECT * FROM $IMAGE_CARD_TABLE WHERE $IMG_TABLE_ID = " + id?.let {
                Integer.valueOf(
                    it
                )
            }
        val db = this.readableDatabase
        val cursor = db.rawQuery(query, null)
        var bytesImage: ByteArray? = null
        try {
            val field = CursorWindow::class.java.getDeclaredField("sCursorWindowSize")
            field.isAccessible = true
            field[null] = 100 * 1024 * 1024
        } catch (e: Exception) {
            e.printStackTrace()
        }
        try {
            cursor.moveToFirst()
            bytesImage = cursor.getBlob(2)
            cursor.close()
            return bytesImage
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return bytesImage
    }

    fun updateData(row_id: String, title: String?, answer: String?, count: Int) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COLUMN_TITLE, title)
        cv.put(COLUMN_ANSWER, answer)
        cv.put(COLUMN_COUNT, count)
        val result = db.update(TABLE_NAME, cv, "_id=?", arrayOf(row_id)).toLong()
        if (result == -1L) {
            Toast.makeText(context, "Update failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Successfully Updated", Toast.LENGTH_SHORT).show()
        }
    }

    fun resetCountImgTable(rid: String?) {
        val query =
            "UPDATE " + IMAGE_CARD_TABLE + " SET " + IMG_COLUMN_COUNT + " = " + Integer.valueOf(0) + " WHERE " + IMG_TABLE_ID + " = " + rid?.let {
                Integer.valueOf(
                    it
                )
            }
        val db = this.writableDatabase
        db.execSQL(query)
    }

    fun updateData2(row_id: String, title: String?, answer: String?, count: Int) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COLUMN_TITLE, title)
        cv.put(COLUMN_ANSWER, answer)
        cv.put(COLUMN_COUNT, count)
        val result = db.update(TABLE_NAME, cv, "_id=?", arrayOf(row_id)).toLong()
    }

    fun deleteRow(row_id: String) {
        val db = this.writableDatabase
        val cv = ContentValues()
        val result = db.delete(TABLE_NAME, "_id=?", arrayOf(row_id)).toLong()
        if (result == -1L) {
            Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Successfully deleted", Toast.LENGTH_SHORT).show()
        }
    }

    fun deleteImageRow(row_id: String) {
        val db = this.writableDatabase
        val cv = ContentValues()
        val result = db.delete(IMAGE_CARD_TABLE, "_id=?", arrayOf(row_id)).toLong()
        if (result == -1L) {
            Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Successfully deleted", Toast.LENGTH_SHORT).show()
        }
    }

    fun deleteAll(pid: String?) {
        val db = this.writableDatabase
        db.execSQL(
            "DELETE FROM $TABLE_NAME WHERE $COLUMN_REFER = " + pid?.let {
                Integer.valueOf(
                    it
                )
            }
        )
        db.execSQL(
            "DELETE FROM $IMAGE_CARD_TABLE WHERE $COLUMN_REFER = " + pid?.let {
                Integer.valueOf(
                    it
                )
            }
        )
    }

    fun deleteDeck(row_id: String) {
        val db = this.writableDatabase
        val cv = ContentValues()
        val result = db.delete(PARENT_TABLE_NAME, "_id=?", arrayOf(row_id)).toLong()
        if (result == -1L) {
            Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Successfully deleted", Toast.LENGTH_SHORT).show()
        }
    }

    fun deleteAllDeck() {
        val db = this.writableDatabase
        db.execSQL("DELETE FROM $PARENT_TABLE_NAME")
    }

    fun ViewAll(pid: String?): Cursor? {
        val query =
            "SELECT * FROM $TABLE_NAME WHERE $COLUMN_REFER = " + pid?.let {
                Integer.valueOf(
                    it
                )
            } + " UNION " + "SELECT * FROM " + IMAGE_CARD_TABLE + " WHERE " + COLUMN_REFER + " = " + pid?.let {
                Integer.valueOf(
                    it
                )
            } + " ORDER BY " + COLUMN_COUNT
        val db = this.readableDatabase
        var cursor: Cursor? = null
        if (db != null) {
            cursor = db.rawQuery(query, null)
        }
        return cursor
    }

    fun ViewAllDesc(pid: String?): Cursor? {
        val query =
            "SELECT * FROM $TABLE_NAME WHERE $COLUMN_REFER = " + pid?.let {
                Integer.valueOf(
                    it
                )
            } + " UNION " + "SELECT * FROM " + IMAGE_CARD_TABLE + " WHERE " + COLUMN_REFER + " = " + pid?.let {
                Integer.valueOf(
                    it
                )
            } + " ORDER BY " + COLUMN_COUNT + " DESC "
        val db = this.readableDatabase
        var cursor: Cursor? = null
        if (db != null) {
            cursor = db.rawQuery(query, null)
        }
        return cursor
    }

    fun ViewAllIdDefault(pid: String?): Cursor? {
        val query =
            "SELECT * FROM $TABLE_NAME WHERE $COLUMN_REFER = " + pid?.let {
                Integer.valueOf(
                    it
                )
            } + " UNION " + "SELECT * FROM " + IMAGE_CARD_TABLE + " WHERE " + COLUMN_REFER + " = " + pid?.let {
                Integer.valueOf(
                    it
                )
            } + " ORDER BY " + COLUMN_ID
        val db = this.readableDatabase
        var cursor: Cursor? = null
        if (db != null) {
            cursor = db.rawQuery(query, null)
        }
        return cursor
    }

    fun ViewAllIdDesc(pid: String?): Cursor? {
        val query =
            "SELECT * FROM $TABLE_NAME WHERE $COLUMN_REFER = " + pid?.let {
                Integer.valueOf(
                    it
                )
            } + " UNION " + "SELECT * FROM " + IMAGE_CARD_TABLE + " WHERE " + COLUMN_REFER + " = " + pid?.let {
                Integer.valueOf(
                    it
                )
            } + " ORDER BY " + COLUMN_ID + " DESC "
        val db = this.readableDatabase
        var cursor: Cursor? = null
        if (db != null) {
            cursor = db.rawQuery(query, null)
        }
        return cursor
    }

    companion object {
        private const val DATABASE_NAME = "flashcard5.db"
        private const val DATABASE_VERSION = 1
        private const val PARENT_TABLE_NAME = "decks"
        private const val PARENT_COLUMN_ID = "_id"
        private const val PARENT_TITLE = "deck_title"
        private const val TABLE_NAME = "cards"
        private const val COLUMN_ID = "_id"
        private const val COLUMN_TITLE = "card_title"
        private const val COLUMN_ANSWER = "card_answer"
        private const val COLUMN_COUNT = "rev_count"
        private const val COLUMN_REFER = "deck_id"
        private const val IMAGE_CARD_TABLE = "img_table"
        private const val IMG_TABLE_ID = "_id"
        private const val IMG_TABLE_TITLE = "img_title"
        private const val IMG_TABLE_IMAGE = "img_img"
        private const val IMG_COLUMN_COUNT = "rev_count"
    }
}