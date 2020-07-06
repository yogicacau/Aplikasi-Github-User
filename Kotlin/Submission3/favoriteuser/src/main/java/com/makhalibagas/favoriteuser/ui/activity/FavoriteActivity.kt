package com.makhalibagas.favoriteuser.ui.activity

import android.content.Intent
import android.database.ContentObserver
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.makhalibagas.favoriteuser.R
import com.makhalibagas.favoriteuser.adapter.UserAdapter
import com.makhalibagas.favoriteuser.database.UserContract.UserColumns.Companion.CONTENT_URI_USER
import com.makhalibagas.favoriteuser.helper.MappingHelper
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.android.synthetic.main.activity_favorite.btSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)


        btSettings.setOnClickListener {
            startActivity(Intent(applicationContext, SettingsActivity::class.java))
        }
        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        val myObserver = object : ContentObserver(handler){
            override fun onChange(selfChange: Boolean) {
                loadUserAsync()
            }
        }

        contentResolver.registerContentObserver(CONTENT_URI_USER, true, myObserver)

        if (savedInstanceState == null){
            loadUserAsync()
        }

        rvFav.layoutManager = LinearLayoutManager(this)
    }

    private fun loadUserAsync(){
        GlobalScope.launch(Dispatchers.Main){
            val deferredUser = async(Dispatchers.IO){
                val cursor = contentResolver.query(CONTENT_URI_USER,null,null,null,null)
                MappingHelper.mapCursorToList(cursor)
            }

            val user = deferredUser.await()
            if (user.isNotEmpty()){
                rvFav.adapter = UserAdapter(applicationContext, user)
            }else{
                notFound.visibility = View.VISIBLE
            }
        }
    }
}
