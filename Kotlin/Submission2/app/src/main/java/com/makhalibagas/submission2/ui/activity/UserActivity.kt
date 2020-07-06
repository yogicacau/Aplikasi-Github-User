package com.makhalibagas.submission2.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.makhalibagas.submission2.R
import com.makhalibagas.submission2.adapter.UserAdapter
import com.makhalibagas.submission2.viewmodel.BaseViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_user.*
import kotlinx.android.synthetic.main.activity_user.btSettings

class UserActivity : AppCompatActivity() {


    private lateinit var baseViewModel: BaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)


        val query = intent.getStringExtra("QUERY")
        setSupportActionBar(findViewById(R.id.toolbar))

        if(query != null){
            baseViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(BaseViewModel::class.java)
            baseViewModel.loadUser(applicationContext, query)
            baseViewModel.getListUser.observe(this, Observer {
                rvUser.adapter = UserAdapter(applicationContext, it)
            })

            tvToolbar.text = query
            rvUser.layoutManager = LinearLayoutManager(this)
        }


        btSettings.setOnClickListener {
            startActivity(Intent(applicationContext, AboutActivity::class.java))
        }

    }
}
