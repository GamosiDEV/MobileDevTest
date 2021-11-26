package com.example.mobiledevtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;

import com.example.mobiledevtest.Objects.Repository;
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
import java.util.LinkedList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    //ActivityMainBinding binding;
    Handler handler = new Handler();
    ProgressDialog progressBar ;
    FetchData fetchData ;
    ObjectMapper objectMapper;




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

        //binding = ActivityMainBinding.inflate(getLayoutInflater());
        //setContentView(binding.getRoot());

        fetchData = new FetchData();
        fetchData.start();

        /*
        repository = new Repository();

        try {
            repository = objectMapper.readValue(fetchData.getJsonData(), Repository.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        Log.d("Gabriel", repository.getName());*/

    }


    class FetchData extends Thread{

        private String jsonData = "";
        List<Repository> repositories = new LinkedList<Repository>();

        @Override
        public void run() {

            handler.post(new Runnable() {
                @Override
                public void run() {
                    progressBar = new ProgressDialog(MainActivity.this);
                    progressBar.setMessage("Wait");
                    progressBar.setCancelable(false);
                    progressBar.show();
                }
            });


            try {
                URL url = new URL("https://api.github.com/search/repositories?q=language:Java&sort=stars&page=1");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;

                while ((line = bufferedReader.readLine()) != null){
                    jsonData = jsonData + line;
                }

                if(!jsonData.isEmpty()){
                    JSONObject jsonObject = new JSONObject(jsonData);

                    JSONArray itens = jsonObject.getJSONArray("items");
                    repositories.clear();
                    for(int i=0; i<itens.length();i++){
                        JSONObject obj = itens.getJSONObject(i);

                        //criar construtor
                        Repository repository = new Repository();

                        repository.setName(obj.getString("name"));
                        repository.setDescription(obj.getString("description"));
                        repository.setForks_count(obj.getInt("forks_count"));
                        repository.setStargazers_count(obj.getInt("stargazers_count"));

                        JSONObject owner = obj.getJSONObject("owner");
                        repository.setUserUrl(owner.getString("url"));

                        repositories.add(repository);

                    }




//                    Log.d("Here", "run: "+line.toString());
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (progressBar.isShowing()){
                        progressBar.dismiss();
                    }
                }
            });

        }

        public String getJsonData() {
            return jsonData;
        }
    }

}