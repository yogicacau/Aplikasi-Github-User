package com.makhalibagas.favoriteuser.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import com.makhalibagas.favoriteuser.R
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        tvSelectLanguage.setOnClickListener {
            val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(intent)
        }

        tvFavoriteUser.setOnClickListener {
            startActivity(Intent(applicationContext, FavoriteActivity::class.java))
        }


        tvAuthor.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/makhalibagas")))
        }

        tvSearchMain.setOnClickListener {
            startActivity(Intent(applicationContext, MainActivity::class.java))
        }
    }
}
