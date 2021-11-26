package com.example.mobiledevtest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RepositoryAdapter extends RecyclerView.Adapter<RepositoryAdapter.MyViewHolder> {

    Context context;
    int userImages[];//mudar o tipo
    String user[];
    String name[];
    String repositoryName[];
    String repositoryDescription[];
    int forkNumber[];
    int starNumber[];

    public RepositoryAdapter(Context ct, int userImages[], String user[], String name[],
                             String repositoryName[], String repositoryDescription[], int forkNumber[], int starNumber[]){
        this.context = ct;
        this.userImages = userImages;
        this.user = user;
        this.name = name;
        this.repositoryName = repositoryName;
        this.repositoryDescription = repositoryDescription;
        this.forkNumber = forkNumber;
        this.starNumber = starNumber;
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
        holder.repositoryName.setText(this.repositoryName[position]);
        holder.repositoryDescription.setText(this.repositoryDescription[position]);
        holder.repositoryUserName.setText(this.user[position]);
        holder.userName.setText(this.name[position]);
        holder.forkNumber.setText(String.valueOf(this.forkNumber[position]));
        holder.starNumber.setText(String.valueOf(this.starNumber[position]));
        holder.userImage.setImageResource(userImages[position]);
    }

    @Override
    public int getItemCount() {
        return userImages.length;
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
