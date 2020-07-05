package com.makhalibagas.githubuser.database

import android.net.Uri
import android.provider.BaseColumns

/**
 * Created by Bagas Makhali on 7/4/2020.
 */
object ReposContract {

    const val AUTHOR_REPOS = "com.makhalibagas.githubuser"
    const val SCHEME_REPOS = "content"

    class ReposColumns : BaseColumns{
        companion object{
            const val TABLE_REPOS = "table_repos"
            const val ID_REPOS = "idrepos"
            const val NAME = "name"
            const val DESC = "desc"
            const val LANGUAGE = "language"
            const val STAR_COUNT = "star"
            const val TIME_UPDATE = "time"
            const val HTML_URL = "url"

            val CONTENT_URI_REPOS : Uri = Uri.Builder().scheme(SCHEME_REPOS)
                .authority(AUTHOR_REPOS)
                .appendPath(TABLE_REPOS)
                .build()
        }
    }
}