package com.zimpo.githubdashboard.Model

import java.io.Serializable

class User(val id: Int, val login: String, val avatar_url: String, val url: String, val repos_url: String) : Serializable {

    /*var id = -1
    var login: String? = null
    var avatar_url: String? = null
    var url: String? = null
    var repos_url: String? = null*/

    /*init {
        this.id = id
        this.login = login
        this.avatar_url = avatar_url
        this.url = url
        this.repos_url = repos_url
    }*/
}