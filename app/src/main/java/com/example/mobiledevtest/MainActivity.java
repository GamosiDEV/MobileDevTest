package com.example.mobiledevtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mobiledevtest.Objects.Repository;
import com.example.mobiledevtest.Objects.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private static String URL="https://api.github.com/search/repositories?q=language:Java&sort=stars&page=1";

    ArrayList<Repository> repositories;

    RepositoryAdapter adapter;

    private ProgressDialog progressDialog;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        repositories = new ArrayList<>();

        new getRepositories().execute();

    }

    private class getRepositories extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);

            if (progressDialog.isShowing()){
                progressDialog.dismiss();
            }
            /*
            if (progressBar.getVisibility() == View.VISIBLE){
                progressBar.setIndeterminate(false);
                progressBar.setVisibility(View.GONE);
            }*/

            adapter = new RepositoryAdapter(MainActivity.this, repositories);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            //Adapter
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Carregando...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            /*
            progressBar = new ProgressBar(MainActivity.this);
            progressBar = (ProgressBar) findViewById(R.id.progress_bar);
            progressBar.setIndeterminate(true);
            progressBar.setVisibility(View.VISIBLE);*/

        }

        @Override
        protected Void doInBackground(Void... voids) {
            Handler handler = new Handler();

            String jsonString = handler.httpServiceCall(URL);
            if (jsonString != null){

                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    JSONArray items = jsonObject.getJSONArray("items");
                    for (int i=0;i<items.length();i++){
                        JSONObject obj=items.getJSONObject(i);
                        Repository repository = new Repository(obj.getString("name"),obj.getString("description")
                                ,obj.getInt("forks_count"),obj.getInt("stargazers_count"));

                        JSONObject owner = obj.getJSONObject("owner");//here
                        repository.setUserUrl(owner.getString("url"));

                        User user = new User(owner.getString("login"),owner.getString("avatar_url"));

                        String jsonStringFromUser = handler.httpServiceCall(repository.getUserUrl());
                        if (jsonStringFromUser!=null){
                            JSONObject personalInfoJSON = new JSONObject(jsonStringFromUser);
                            user.setFullName(personalInfoJSON.getString("name"));
                        }

                        repository.setUser(user);

                        repositories.add(repository);
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Json Parsing Error", Toast.LENGTH_SHORT).show();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Json Parsing Error", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
            else{
                Toast.makeText(getApplicationContext(), "Server Error", Toast.LENGTH_SHORT).show();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Server Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            return null;
        }


    }

}