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

    private String repositoryName;
    private String repositoryDescription;
    private int forksCount;
    private int starGazersCount;
    private String userUrl;

    Map<String, Object> informations = new LinkedHashMap<>();

    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    public String getRepositoryDescription() {
        return repositoryDescription;
    }

    public void setRepositoryDescription(String repositoryDescription) {
        this.repositoryDescription = repositoryDescription;
    }

    public int getForksCount() {
        return forksCount;
    }

    public void setForksCount(int forksCount) {
        this.forksCount = forksCount;
    }

    public int getStarGazersCount() {
        return starGazersCount;
    }

    public void setStarGazersCount(int starGazersCount) {
        this.starGazersCount = starGazersCount;
    }

    public String getUserUrl() {
        return userUrl;
    }

    public void setUserUrl(String userUrl) {
        this.userUrl = userUrl;
    }

    public Map<String, Object> getInformations() {
        return informations;
    }

    void setInformations(String key, Object value) {
        informations.put(key, value);
    }

}
