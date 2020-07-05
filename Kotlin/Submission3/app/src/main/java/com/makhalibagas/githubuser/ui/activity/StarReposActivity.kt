package com.makhalibagas.githubuser.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.makhalibagas.githubuser.R
import com.makhalibagas.githubuser.adapter.ReposAdapter
import com.makhalibagas.githubuser.database.ReposHelper
import com.makhalibagas.githubuser.helper.MappingHelper
import kotlinx.android.synthetic.main.activity_star_repos.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class StarReposActivity : AppCompatActivity() {

    private lateinit var reposHelper: ReposHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_star_repos)


        setSupportActionBar(findViewById(R.id.toolbar))
        reposHelper = ReposHelper.getDatabase(applicationContext)
        reposHelper.open()
        if (savedInstanceState == null){
            loadStarAsync()
        }

        rvStar.layoutManager = LinearLayoutManager(applicationContext)
        btSettings.setOnClickListener {
            startActivity(Intent(applicationContext, SettingsActivity::class.java))
        }
    }

    private fun loadStarAsync(){
        GlobalScope.launch(Dispatchers.Main){
            val deferredStar = async(Dispatchers.IO){
                val cursor = reposHelper.queryAll()
                MappingHelper.mapCursorToListStar(cursor)
            }

            val starList = deferredStar.await()
            if (starList.isNotEmpty()){
                rvStar.adapter = ReposAdapter(applicationContext,starList)
            }else{
                Toast.makeText(applicationContext, "Belum ada data", Toast.LENGTH_SHORT).show()
            }
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        reposHelper.close()
    }
}
