package com.makhalibagas.favoriteuser.helper

import android.database.Cursor
import com.makhalibagas.favoriteuser.database.UserContract.UserColumns.Companion.AVATAR
import com.makhalibagas.favoriteuser.database.UserContract.UserColumns.Companion.HTML
import com.makhalibagas.favoriteuser.database.UserContract.UserColumns.Companion.ID
import com.makhalibagas.favoriteuser.database.UserContract.UserColumns.Companion.USERNAME
import com.makhalibagas.favoriteuser.model.repository.Repository
import com.makhalibagas.favoriteuser.model.user.User

/**
 * Created by Bagas Makhali on 7/4/2020.
 */
object MappingHelper {

    fun mapCursorToList(cursor: Cursor?) : List<User>{
        val userList = ArrayList<User>()

        cursor?.apply {
            while (moveToNext()){
                val id = getInt(getColumnIndexOrThrow(ID))
                val username = getString(getColumnIndexOrThrow(USERNAME))
                val avatar = getString(getColumnIndexOrThrow(AVATAR))
                val html = getString(getColumnIndexOrThrow(HTML))
                userList.add(User(username, avatar, html, id))
            }
        }

        return userList
    }

}