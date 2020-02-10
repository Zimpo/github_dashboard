package com.zimpo.githubdashboard.Object;

import java.io.Serializable;
import java.util.Date;

public class Repo implements Serializable {

    //VARIABLES
    private int id = -1;
    private String name = null;
    private String language = null;
    private int stars = -1;
    private String description = null;
    private Date date_created = null;
    private String url = null;

    public Repo(int id, String name, String language, int stars, String description, Date date_created, String url)
    {
        this.id = id;
        this.name = name;
        this.language = language;
        this.stars = stars;
        this.description = description;
        this.date_created = date_created;
        this.url = url;
    }

    //SETTER
    public void setId(int id) { this.id = id; }
    public void setName(String name) {
        this.name = name;
    }
    public void setLanguage(String language) {
        this.language = language;
    }
    public void setStars(int stars) {
        this.stars = stars;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setDate_created(Date date_created) {
        this.date_created = date_created;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    //GETTER
    public int getId()
    { return id; }
    public String getName() {
        return name;
    }
    public String getLanguage() {
        return language;
    }
    public int getStars() {
        return stars;
    }
    public String getDescription() {
        return description;
    }
    public Date getDate_created() {
        return date_created;
    }
    public String getUrl() {
        return url;
    }
}