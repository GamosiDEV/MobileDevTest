package com.example.mobiledevtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    //testando se a recycler esta funcionando;
    int userImages[] = {R.drawable.android,R.drawable.android,R.drawable.android,R.drawable.android,
            R.drawable.android,R.drawable.android,R.drawable.android,R.drawable.android,R.drawable.android};
    String user[];
    String name[];
    String repositoryName[];
    String repositoryDescription[];
    int forkNumber[] = {90,80,70,60,50,40,30,20,10};
    int starNumber[] = {10,20,30,40,50,60,70,80,90};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        //Populando String para teste
        user = getResources().getStringArray(R.array.repository_UserName);
        name = getResources().getStringArray(R.array.repository_Name);
        repositoryName=getResources().getStringArray(R.array.repository_names);
        repositoryDescription = getResources().getStringArray(R.array.repository_description);

        RepositoryAdapter repositoryAdapter = new RepositoryAdapter(this, userImages, user, name,
                repositoryName,repositoryDescription,forkNumber,starNumber);

        recyclerView.setAdapter(repositoryAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}