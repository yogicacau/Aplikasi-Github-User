package com.makhalibagas.favoriteuser.Adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makhalibagas.favoriteuser.DetailActivity;
import com.makhalibagas.favoriteuser.Model.Useritem;
import com.makhalibagas.favoriteuser.R;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {


    private Cursor cursor;
    public FavoriteAdapter(Context context){
    }

    public void setFavoriteList(Cursor favoriteList){
        this.cursor = favoriteList;
    }


    public Useritem getUserItem(int position){
        if (!cursor.moveToPosition(position)){
            throw new IllegalStateException("position invalid");
        }
        return new Useritem(cursor);
    }

    @NonNull
    @Override
    public FavoriteAdapter.FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row,parent,false);


        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FavoriteAdapter.FavoriteViewHolder holder, int position) {

        final Useritem useritem = getUserItem(position);
        holder.username.setText(useritem.getLogin());
        holder.url.setText(useritem.getUrl());
        Glide.with(holder.itemView.getContext())
                .load(useritem.getAvatarUrl())
                .into(holder.imageView);
        holder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), DetailActivity.class);
                intent.putExtra(DetailActivity.DATA_SEARCH, useritem);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (cursor == null) return 0;
        return cursor.getCount();
    }

    public class FavoriteViewHolder extends RecyclerView.ViewHolder {
        TextView username, url,detail;
        ImageView imageView;
        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            url = itemView.findViewById(R.id.url);
            detail = itemView.findViewById(R.id.detail);
            imageView = itemView.findViewById(R.id.img_profile);
        }
    }
}
