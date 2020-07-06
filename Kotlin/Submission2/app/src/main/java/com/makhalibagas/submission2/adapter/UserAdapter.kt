package com.makhalibagas.submission2.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.makhalibagas.submission2.R
import com.makhalibagas.submission2.model.user.User
import com.makhalibagas.submission2.ui.activity.DetailActivity
import de.hdodenhof.circleimageview.CircleImageView

/**
 * Created by Bagas Makhali on 7/2/2020.
 */
class UserAdapter(private val context:Context?, private val userList : List<User>)
    : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    class ViewHolder(view : View)  : RecyclerView.ViewHolder(view){

        private val imgUser = view.findViewById<CircleImageView>(R.id.imgUser)
        private val tvUser = view.findViewById<TextView>(R.id.tvUser)
        private val tvHtml = view.findViewById<TextView>(R.id.tvHtml)
        fun bind(user: User){
            Glide.with(itemView.context)
                .load(user.avatarUrl)
                .into(imgUser)
            tvUser.text = user.login
            tvHtml.text = user.htmlUrl
            tvHtml.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(user.htmlUrl))
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_user, parent, false))
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(userList[position])
        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_USER, userList[position])
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context?.startActivity(intent)
        }
    }
}