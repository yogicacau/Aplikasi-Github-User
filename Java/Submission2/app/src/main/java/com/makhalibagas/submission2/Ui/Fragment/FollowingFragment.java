package com.makhalibagas.submission2.Ui.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.makhalibagas.submission2.Adapter.AdapterUser;
import com.makhalibagas.submission2.Connection.ApiService;
import com.makhalibagas.submission2.Connection.RetrofitConfiguration;
import com.makhalibagas.submission2.Model.UserGithub;
import com.makhalibagas.submission2.R;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 created at  5 may 2020;
 by makhalibagas
 */
public class FollowingFragment extends Fragment {


    private RecyclerView recyclerView;
    private ShimmerFrameLayout shimmerFrameLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_following, container, false);
        setDataFollowers(view);
        return view;
    }

    private void setDataFollowers(View view){
        recyclerView = view.findViewById(R.id.rv_user);
        shimmerFrameLayout = view.findViewById(R.id.shimmer_layout);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        UserGithub userGithub = Objects.requireNonNull(getActivity()).getIntent().getParcelableExtra("DATA_USER");
        ApiService apiService = RetrofitConfiguration.getRetrofit().create(ApiService.class);
        Call<List<UserGithub>> call = apiService.getUserFollowing(Objects.requireNonNull(userGithub).getLogin());
        call.enqueue(new Callback<List<UserGithub>>() {
            @Override
            public void onResponse(Call<List<UserGithub>> call, Response<List<UserGithub>> response) {
                AdapterUser adapterUser = new AdapterUser(getContext(),response.body());
                recyclerView.setAdapter(adapterUser);
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<List<UserGithub>> call, Throwable t) {

            }
        });
    }
}
