package com.example.whatsapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsapp.ChatDetailActivity;
import com.example.whatsapp.Models.Users;
import com.example.whatsapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.viewFolder> {


    ArrayList<Users> list;
    Context context;

    public UsersAdapter(ArrayList<Users> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewFolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(context).inflate(R.layout.sample_show_user,parent,false);
       return new viewFolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewFolder holder, int position) {
        Users users=list.get(position);
        Picasso.get().load(users.getProfilePic()).placeholder(R.drawable.avatar3).into(holder.image);
        holder.userName.setText(users.getUserName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ChatDetailActivity.class);
                intent.putExtra("userId",users.getUserId());
                intent.putExtra("profilePic",users.getProfilePic());
                intent.putExtra("userName",users.getUserName());
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewFolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView userName,lastMessage;

        public viewFolder(@NonNull View itemView) {
            super(itemView);

            image=itemView.findViewById(R.id.profile_image);
            userName=itemView.findViewById(R.id.userNameList);
            lastMessage=itemView.findViewById(R.id.lastMessage);
        }
    }
}
