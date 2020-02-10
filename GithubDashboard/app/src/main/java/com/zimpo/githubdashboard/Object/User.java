package com.zimpo.githubdashboard.Object;

import java.io.Serializable;

public class User implements Serializable {

    //VARIABLES
    private int id = -1;
    private String login = null;
    private String avatar_url = null;
    private String url = null;
    private String repos_url = null;

    public User(int id, String login, String avatar_url, String url, String repos_url)
    {
        this.id = id;
        this.login = login;
        this.avatar_url = avatar_url;
        this.url = url;
        this.repos_url = repos_url;
    }

    //SETTER
    public void setId(int id)
    { this.id = id; }
    public void setLogin(String login) {
        this.login = login;
    }
    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public void setRepos_url(String repos_url) {
        this.repos_url = repos_url;
    }

    //GETTER
    public int getId()
    { return id; }
    public String getLogin() {
        return login;
    }
    public String getAvatar_url() {
        return avatar_url;
    }
    public String getUrl() {
        return url;
    }
    public String getRepos_url() {
        return repos_url;
    }
}