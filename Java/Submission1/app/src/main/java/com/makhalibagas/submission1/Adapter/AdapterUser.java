package com.makhalibagas.submission1.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.makhalibagas.submission1.Activity.DetailActivity;
import com.makhalibagas.submission1.R;
import com.makhalibagas.submission1.Model.User;

import java.util.ArrayList;

public class AdapterUser extends BaseAdapter {
    private Context context;
    private ArrayList<User> userArrayList;

    public AdapterUser(Context context, ArrayList<User> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
    }

    @Override
    public int getCount() {
        return userArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return userArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null){
            itemView = LayoutInflater.from(context).inflate(R.layout.item_user,parent,false);

        }

        ViewHolder viewHolder = new ViewHolder(itemView);

        final User user = (User) getItem(position);
        viewHolder.data(user);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("DATA_USER", user);
                context.startActivity(intent);
            }
        });
        return itemView;
    }

    public static class ViewHolder{
        ImageView img_user;
        TextView tv_name,tv_company;
        ViewHolder(View view){
            img_user = view.findViewById(R.id.img_user);
            tv_name  = view.findViewById(R.id.tv_name);
            tv_company = view.findViewById(R.id.tv_company);
        }
        void data(User user){
            img_user.setImageResource(user.getAvatar());
            tv_name.setText(user.getName());
            tv_company.setText(user.getCompany());
        }

    }
}
