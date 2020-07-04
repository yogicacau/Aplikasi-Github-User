package com.makhalibagas.githubuser.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.makhalibagas.githubuser.ui.fragment.FollowersFragment
import com.makhalibagas.githubuser.ui.fragment.FollowingFragment
import com.makhalibagas.githubuser.ui.fragment.RepositoryFragment


/**
 * Created by Bagas Makhali on 7/3/2020.
 */
class TabsAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 3
    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return RepositoryFragment()
            1 -> return FollowersFragment()
            2 -> return FollowingFragment()
        }
        return RepositoryFragment()
    }

}