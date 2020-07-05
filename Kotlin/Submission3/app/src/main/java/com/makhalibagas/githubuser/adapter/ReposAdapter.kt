package com.makhalibagas.githubuser.adapter

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.makhalibagas.githubuser.R
import com.makhalibagas.githubuser.database.ReposContract.ReposColumns.Companion.DESC
import com.makhalibagas.githubuser.database.ReposContract.ReposColumns.Companion.HTML_URL
import com.makhalibagas.githubuser.database.ReposContract.ReposColumns.Companion.ID_REPOS
import com.makhalibagas.githubuser.database.ReposContract.ReposColumns.Companion.LANGUAGE
import com.makhalibagas.githubuser.database.ReposContract.ReposColumns.Companion.NAME
import com.makhalibagas.githubuser.database.ReposContract.ReposColumns.Companion.STAR_COUNT
import com.makhalibagas.githubuser.database.ReposContract.ReposColumns.Companion.TIME_UPDATE
import com.makhalibagas.githubuser.database.ReposHelper
import com.makhalibagas.githubuser.model.repository.Repository

/**
 * Created by Bagas Makhali on 7/2/2020.
 */
class ReposAdapter(private val context:Context?, private val reposList : List<Repository>)
    : RecyclerView.Adapter<ReposAdapter.ViewHolder>() {



    class ViewHolder(view : View)  : RecyclerView.ViewHolder(view){

        private val name = view.findViewById<TextView>(R.id.tvNameRepo)
        private val desc = view.findViewById<TextView>(R.id.tvDescRepo)
        private val language = view.findViewById<TextView>(R.id.tvLanguage)
        private val star = view.findViewById<TextView>(R.id.tvStar)
        private val time = view.findViewById<TextView>(R.id.tvTimeRepo)
        private val btStar = view.findViewById<Button>(R.id.btStarRepo)
        private val btUnStar = view.findViewById<Button>(R.id.btUnStarRepo)

        @SuppressLint("SetTextI18n")
        fun bind(repos : Repository){
            val reposHelper : ReposHelper = ReposHelper.getDatabase(itemView.context)
            reposHelper.open()

            name.text = repos.name
            desc.text = repos.description
            language.text = repos.language
            star.text = repos.stargazersCount.toString()
            time.text = "Update " + repos.updatedAt


            if (reposHelper.check(repos.id.toString())){
                btStar.visibility = View.GONE
                btUnStar.visibility = View.VISIBLE
            }

            btStar.setOnClickListener {
                val values = ContentValues()
                values.put(ID_REPOS, repos.id)
                values.put(NAME, repos.name)
                values.put(DESC, repos.description)
                values.put(LANGUAGE, repos.language)
                values.put(STAR_COUNT, repos.stargazersCount.toString())
                values.put(TIME_UPDATE, repos.updatedAt)
                values.put(HTML_URL, repos.htmlUrl)
                reposHelper.insert(values)
                btUnStar.visibility = View.VISIBLE
                btStar.visibility = View.GONE
            }

            btUnStar.setOnClickListener {
                btUnStar.visibility = View.GONE
                btStar.visibility = View.VISIBLE
                reposHelper.delete(repos.id.toString())
            }

        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_repository, parent, false))
    }

    override fun getItemCount(): Int {
        return reposList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(reposList[position])
        holder.itemView.setOnClickListener {

        }
    }
}