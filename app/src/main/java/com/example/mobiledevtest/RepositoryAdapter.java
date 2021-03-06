package com.example.mobiledevtest;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobiledevtest.Objects.Repository;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RepositoryAdapter extends RecyclerView.Adapter<RepositoryAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Repository> repositories;

    public RepositoryAdapter(Context ct, ArrayList<Repository> repositories){
        this.context = ct;
        this.repositories = repositories;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.card_view,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.repositoryName.setText(repositories.get(position).getName());
        holder.repositoryDescription.setText(repositories.get(position).getDescription());
        holder.repositoryUserName.setText(repositories.get(position).getUser().getUsername());
        holder.forkNumber.setText(String.valueOf(repositories.get(position).getForks_count()));
        holder.starNumber.setText(String.valueOf(repositories.get(position).getStargazers_count()));
        if(repositories.get(position).getUser().getFullName() == null
                || repositories.get(position).getUser().getFullName() == "null"){
            holder.userName.setText(" ");
        }else{
            holder.userName.setText(repositories.get(position).getUser().getFullName());
        }
        Picasso.get()
                .load(repositories.get(position).getUser().getAvatarUrl())
                .fit()
                .centerCrop()
                .into(holder.userImage);
    }

    @Override
    public int getItemCount() {
        return repositories.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView repositoryName, repositoryDescription, repositoryUserName, userName, forkNumber, starNumber;
        ImageView userImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            repositoryName = itemView.findViewById(R.id.repositoryName);
            repositoryDescription = itemView.findViewById(R.id.repositoryDescription);
            repositoryUserName = itemView.findViewById(R.id.repositoryUserName);
            userName = itemView.findViewById(R.id.userName);
            forkNumber = itemView.findViewById(R.id.forkNumber);
            starNumber = itemView.findViewById(R.id.starNumber);
            userImage = itemView.findViewById(R.id.userImage);
        }
    }


}
