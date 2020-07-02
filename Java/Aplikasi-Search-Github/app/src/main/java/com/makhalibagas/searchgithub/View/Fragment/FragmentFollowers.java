package com.makhalibagas.searchgithub.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.makhalibagas.searchgithub.Adapter.SearchAdapter;
import com.makhalibagas.searchgithub.Model.Useritem;
import com.makhalibagas.searchgithub.R;
import com.makhalibagas.searchgithub.ViewModel.FollowersViewModel;

import java.util.List;
import java.util.Objects;

import static com.makhalibagas.searchgithub.View.Activity.DetailActivity.DATA_SEARCH;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentFollowers extends Fragment {

    private RecyclerView recyclerView;
    private SearchAdapter searchAdapter;
    private ProgressBar progressBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_followers, container, false);

        progressBar = view.findViewById(R.id.progress_circular_followers);
        showLoading(true);
        recyclerView = view.findViewById(R.id.rvFollowers);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Useritem useritem = Objects.requireNonNull(getActivity()).getIntent().getParcelableExtra(DATA_SEARCH);
        if (useritem != null){
            FollowersViewModel followersViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(FollowersViewModel.class);
            followersViewModel.setDataFollowers(useritem.getLogin());
            followersViewModel.getDataFollowers().observe(getViewLifecycleOwner(), new Observer<List<Useritem>>() {
                @Override
                public void onChanged(List<Useritem> users) {
                    searchAdapter = new SearchAdapter(getContext(), users);
                    recyclerView.setAdapter(searchAdapter);
                    showLoading(false);
                }
            });
        }


        return view;
    }


    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
