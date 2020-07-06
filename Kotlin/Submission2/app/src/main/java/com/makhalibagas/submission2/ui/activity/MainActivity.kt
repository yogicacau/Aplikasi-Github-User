package com.makhalibagas.submission2.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import com.makhalibagas.submission2.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

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

        btSettings.setOnClickListener {
            startActivity(Intent(applicationContext, AboutActivity::class.java))
        }
    }
}
