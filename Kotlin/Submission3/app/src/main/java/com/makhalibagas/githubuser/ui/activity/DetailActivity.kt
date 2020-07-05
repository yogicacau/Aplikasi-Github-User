package com.makhalibagas.githubuser.ui.activity

import android.annotation.SuppressLint
import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.makhalibagas.githubuser.R
import com.makhalibagas.githubuser.adapter.TabsAdapter
import com.makhalibagas.githubuser.database.UserContract.UserColumns.Companion.AVATAR
import com.makhalibagas.githubuser.database.UserContract.UserColumns.Companion.CONTENT_URI_USER
import com.makhalibagas.githubuser.database.UserContract.UserColumns.Companion.HTML
import com.makhalibagas.githubuser.database.UserContract.UserColumns.Companion.USERNAME
import com.makhalibagas.githubuser.database.UserHelper
import com.makhalibagas.githubuser.model.user.User
import com.makhalibagas.githubuser.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {


    companion object{
        const val EXTRA_USER = "extra_user"
    }

    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        initTabs()
        initData()
        pb.visibility = View.VISIBLE
    }

    @SuppressLint("SetTextI18n")
    private fun initData() {
        val user = intent.getParcelableExtra<User>(EXTRA_USER)
        if (user != null) {

            detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java)
            detailViewModel.loadUser(applicationContext, user.login)
            detailViewModel.getDetailUser.observe(this, Observer {


                Glide.with(applicationContext)
                    .load(it.avatarUrl)
                    .into(findViewById(R.id.imgUser))
                tvName.text = it.name
                tvUsername.text = it.login

                if (it.bio.isNullOrEmpty()) {
                    tvEmail.visibility = View.GONE
                } else {
                    tvBio.text = it.bio
                    tvBio.visibility = View.VISIBLE
                }
                if (it.email.isNullOrEmpty()) {
                    tvEmail.visibility = View.GONE
                } else {
                    tvEmail.text = it.email
                    tvEmail.visibility = View.VISIBLE
                }
                if (it.blog.isNullOrEmpty()) {
                    tvBlog.visibility = View.GONE
                } else {
                    tvBlog.text = it.blog
                    tvBlog.visibility = View.VISIBLE
                }

                tvRepo.text = it.publicRepos.toString() + " Repository"
                tvFollow.text =
                    it.followers.toString() + " Followers . " + it.following.toString() + " Following "


                imgUser.visibility = View.VISIBLE
                tvName.visibility = View.VISIBLE
                tvUsername.visibility = View.VISIBLE
                tvRepo.visibility = View.VISIBLE
                linear.visibility = View.VISIBLE
                pb.visibility = View.INVISIBLE
                btFavorite.visibility = View.VISIBLE
            })

            val userHelper = UserHelper.getDatabase(applicationContext)
            userHelper.open()
            if (userHelper.check(user.id.toString())){
                btFavorite.visibility = View.GONE
                btUnFavorite.visibility = View.VISIBLE
            }
            btFavorite.setOnClickListener {
                val values = ContentValues()
                values.put(USERNAME, user.login)
                values.put(AVATAR, user.avatarUrl)
                values.put(HTML, user.htmlUrl)
                contentResolver.insert(CONTENT_URI_USER, values)
                btFavorite.visibility = View.INVISIBLE
                btUnFavorite.visibility = View.VISIBLE
            }

            btUnFavorite.setOnClickListener {
                contentResolver.delete(Uri.parse(CONTENT_URI_USER.toString() + "/" + user.id),null,null)
                btFavorite.visibility = View.VISIBLE
                btUnFavorite.visibility = View.INVISIBLE
            }

        }
    }

    private fun initTabs() {
        viewpager2.adapter = TabsAdapter(this)
        TabLayoutMediator(tabs, viewpager2,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                when (position) {
                    0 -> tab.text = "Repository"
                    1 -> tab.text = "Followers"
                    2 -> tab.text = "Following"
                }
            }).attach()
    }
}
