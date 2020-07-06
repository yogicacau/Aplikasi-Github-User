package com.makhalibagas.favoriteuser.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.makhalibagas.favoriteuser.R
import com.makhalibagas.favoriteuser.adapter.UserAdapter
import com.makhalibagas.favoriteuser.model.user.User
import com.makhalibagas.favoriteuser.ui.activity.DetailActivity
import com.makhalibagas.favoriteuser.viewmodel.FollowViewModel
import kotlinx.android.synthetic.main.fragment_following.*

/**
 * A simple [Fragment] subclass.
 */
class FollowingFragment : Fragment() {

    private lateinit var followViewModel: FollowViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_following, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setFollowing()

        super.onViewCreated(view, savedInstanceState)
    }

    private fun setFollowing() {
        val user = activity!!.intent.getParcelableExtra<User>(DetailActivity.EXTRA_USER)
        if (user != null) {
            followViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowViewModel::class.java)
            followViewModel.loadFollowing(context, user.login, "1")
            followViewModel.getFollowing.observe(viewLifecycleOwner, Observer {
                rvFollowing.adapter = UserAdapter(context, it)
                rvFollowing.visibility = View.VISIBLE
                pb.visibility = View.INVISIBLE
            })
        }

        rvFollowing.layoutManager = LinearLayoutManager(context)
    }

}
