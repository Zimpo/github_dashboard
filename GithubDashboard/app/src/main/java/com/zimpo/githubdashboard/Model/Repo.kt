package com.zimpo.githubdashboard.Model

import java.io.Serializable
import java.util.Date

class Repo(val id: Int, val name: String, val language: String, val stars: Int, val description: String, val date_created: Date, val url: String) : Serializable {

    /*var id = -1
    var name: String? = null
    var language: String? = null
    var stars = -1
    var description: String? = null
    var date_created: Date? = null
    var url: String? = null

    init {
        this.id = id
        this.name = name
        this.language = language
        this.stars = stars
        this.description = description
        this.date_created = date_created
        this.url = url
    }*/
}