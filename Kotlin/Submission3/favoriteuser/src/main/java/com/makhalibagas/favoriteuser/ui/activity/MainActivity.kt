package com.makhalibagas.favoriteuser.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.makhalibagas.favoriteuser.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))
        searcUser.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                val intent = Intent(applicationContext, UserActivity::class.java)
                intent.putExtra("QUERY", query)
                startActivity(intent)
                return false
            }
        })

        btFavorite.setOnClickListener {
            startActivity(Intent(applicationContext, FavoriteActivity::class.java))
        }
        btSettings.setOnClickListener {
            startActivity(Intent(applicationContext, SettingsActivity::class.java))
        }

    }

}
