package com.example.mobiledevtest.Objects;

import java.util.LinkedHashMap;
import java.util.Map;

public class Repository {
    /*
        name = Nome do repositorio
        description = Descrição do Repositorio
        forks_count = Numero de Forks
        stargazers_count= Numero de Estrelas

        owner>url>(Link)>login = Nome de usuario
        owner>url>(Link)>avatar_url = imagem
        owner>url>(Link)>name = Nome Completo
     */

    private String name;
    private String description;
    private int forks_count;
    private int stargazers_count;
    private String userUrl;
    private User user;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getForks_count() {
        return forks_count;
    }

    public void setForks_count(int forks_count) {
        this.forks_count = forks_count;
    }

    public int getStargazers_count() {
        return stargazers_count;
    }

    public void setStargazers_count(int stargazers_count) {
        this.stargazers_count = stargazers_count;
    }

    public String getUserUrl() {
        return userUrl;
    }

    public void setUserUrl(String userUrl) {
        this.userUrl = userUrl;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
