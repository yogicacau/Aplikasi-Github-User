package com.makhalibagas.searchgithub.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makhalibagas.searchgithub.View.Activity.DetailActivity;
import com.makhalibagas.searchgithub.Model.Useritem;
import com.makhalibagas.searchgithub.R;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {


    private Context context;
    private List<Useritem> userItemList;

    public SearchAdapter(Context context, List<Useritem> userItemList) {
        this.context = context;
        this.userItemList = userItemList;
    }



    @NonNull
    @Override
    public SearchAdapter.SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row,parent,false);


        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.SearchViewHolder holder, int position) {
        Useritem github = userItemList.get(position);
        holder.username.setText(github.getLogin());
        holder.url.setText(github.getHtmlUrl());
        Glide.with(context)
                .load(github.getAvatarUrl())
                .placeholder(R.color.colorPrimary)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return userItemList.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView username, url,detail;
        ImageView imageView;
        SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            url = itemView.findViewById(R.id.url);
            detail = itemView.findViewById(R.id.detail);
            imageView = itemView.findViewById(R.id.img_profile);

            detail.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Useritem useritem = userItemList.get(getAdapterPosition());
            Intent intent = new Intent(v.getContext(), DetailActivity.class);
            intent.putExtra(DetailActivity.DATA_SEARCH, useritem);
            v.getContext().startActivity(intent);
            Toast.makeText(context, useritem.getLogin(), Toast.LENGTH_SHORT).show();
        }
    }
}
