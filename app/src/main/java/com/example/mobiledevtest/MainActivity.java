package com.example.mobiledevtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.SearchView;
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
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_actionbar,menu);

        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Digite para pesquisar");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query) {

                ArrayList<Repository> filteredList = new ArrayList<>();

                for (Repository re : repositories) {
                    if (re.getName().matches(query+"(.*)") || re.getUser().getUsername().matches(query+"(.*)")){
                        filteredList.add(re);
                    }
                }

                setOnRecyclerViewChanges(filteredList);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                return true;
            case R.id.sort_star:

                sortRepositoriesByStar();

                setOnRecyclerViewChanges(repositories);

                return true;
            case R.id.sort_repository_name:

                sortRepositoriesByName();
                setOnRecyclerViewChanges(repositories);

                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    private void setOnRecyclerViewChanges(ArrayList<Repository> list){
        adapter = new RepositoryAdapter(MainActivity.this, list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    private void sortRepositoriesByStar(){
        if (repositories.get(0).getStargazers_count() > repositories.get(repositories.size()-1).getStargazers_count()){

            Collections.sort(repositories, new Comparator<Repository>() {
                @Override
                public int compare(Repository o1, Repository o2) {
                    return Integer.valueOf(o1.getStargazers_count()).compareTo(Integer.valueOf(o2.getStargazers_count()));
                }
            });

        }else{
            Collections.sort(repositories, new Comparator<Repository>() {
                @Override
                public int compare(Repository o1, Repository o2) {
                    return Integer.valueOf(o2.getStargazers_count()).compareTo(Integer.valueOf(o1.getStargazers_count()));

                }
            });
        }
    }

    private void sortRepositoriesByName(){
        if(repositories.get(0).getName().compareToIgnoreCase(repositories.get(repositories.size()-1).getName()) <= 0) {
            Collections.sort(repositories, new Comparator<Repository>() {
                @Override
                public int compare(Repository o1, Repository o2) {
                    return o2.getName().compareToIgnoreCase(o1.getName());

                }
            });
        }else{
            Collections.sort(repositories, new Comparator<Repository>() {
                @Override
                public int compare(Repository o1, Repository o2) {
                    return o1.getName().compareToIgnoreCase(o2.getName());
                }
            });
        }
    }

    private class getRepositories extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Carregando...");
            progressDialog.setCancelable(false);
            progressDialog.show();

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

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);

            if (progressDialog.isShowing()){
                progressDialog.dismiss();
            }

            adapter = new RepositoryAdapter(MainActivity.this, repositories);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        }


    }

}