package com.makhalibagas.githubuser.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import androidx.constraintlayout.widget.Constraints.TAG
import com.makhalibagas.githubuser.database.ReposContract.ReposColumns.Companion.ID_REPOS
import com.makhalibagas.githubuser.database.ReposContract.ReposColumns.Companion.TABLE_REPOS
import java.sql.SQLException


/**
 * Created by Bagas Makhali on 7/4/2020.
 */
class ReposHelper(context: Context) {

    companion object{
        private const val DATABASE_TABLE = TABLE_REPOS
        private lateinit var databaseHelper: DatabaseHelper
        private var reposHelper : ReposHelper? = null

        fun getDatabase(context: Context) : ReposHelper =
            reposHelper ?: synchronized(this){
                reposHelper ?: ReposHelper(context)
            }

        private lateinit var database : SQLiteDatabase

    }

    init {
        databaseHelper = DatabaseHelper(context)
    }

    @Throws(SQLException::class)
    fun open(){
        database = databaseHelper.writableDatabase
    }

    fun close(){
        databaseHelper.close()
        if (database.isOpen)
            database.close()
    }

    fun queryAll() : Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$ID_REPOS ASC")
    }

    fun queryById(id: String) : Cursor{
        return database.query(
            DATABASE_TABLE,
            null,
            "$ID_REPOS = ?",
            arrayOf(id),
            null,
            null,
            null,
            null)
    }

    fun insert(contentValues: ContentValues) : Long{
        return database.insert(DATABASE_TABLE, null, contentValues)
    }

    fun update(id: String, contentValues: ContentValues?) : Int{
        return database.update(DATABASE_TABLE, contentValues, "$ID_REPOS = ?", arrayOf(id))
    }

    fun delete(id: String): Int{
        return database.delete(DATABASE_TABLE, "$ID_REPOS = '$id'", null)
    }

    fun check(id: String): Boolean {
        database = databaseHelper.writableDatabase
        val selectId = "SELECT * FROM $DATABASE_TABLE WHERE $ID_REPOS =?"
        val cursor = database.rawQuery(selectId, arrayOf(id))
        var check = false
        if (cursor.moveToFirst()) {
            check = true
            var i = 0
            while (cursor.moveToNext()) {
                i++
            }
            Log.d(TAG, String.format("%d records found", i))
        }
        cursor.close()
        return check
    }

}