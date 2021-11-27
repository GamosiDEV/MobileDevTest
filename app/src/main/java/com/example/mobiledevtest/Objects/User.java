package com.example.mobiledevtest.Objects;

import java.util.LinkedHashMap;
import java.util.Map;

public class User {
    /*
        owner>url>(Link)>login = Nome de usuario
        owner>url>(Link)>avatar_url = imagem
        owner>url>(Link)>name = Nome Completo
     */

    private String username;
    private String avatarUrl;
    private String fullName;

    public User(String username, String avatarUrl) {
        this.username = username;
        this.avatarUrl = avatarUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
