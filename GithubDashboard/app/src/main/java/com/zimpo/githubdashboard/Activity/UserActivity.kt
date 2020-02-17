package com.zimpo.githubdashboard.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import com.squareup.picasso.Picasso
import com.zimpo.githubdashboard.Adapter.RepoAdapter
import com.zimpo.githubdashboard.Api.GithubReposApi
import com.zimpo.githubdashboard.Model.User
import com.zimpo.githubdashboard.R

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class UserActivity : AppCompatActivity() {

    private lateinit var user: User
    private lateinit var repoAdapter: RepoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        //Bundle
        val extra_bundle = intent.extras
        //User
        if (extra_bundle!!.containsKey("EXTRA_USER")) {
            user = extra_bundle.getSerializable("EXTRA_USER") as User

            setUserInfo()
            setUserRepos()

        } else
            Toast.makeText(applicationContext, "Oups, un problème est survenu", Toast.LENGTH_LONG).show()
    }


    fun setUserInfo() {

        val nameTV = findViewById(R.id.nameTV) as TextView
        val iconIV = findViewById(R.id.iconIV) as ImageView

        nameTV.setText(user.login)

        Picasso.with(applicationContext)
                .load(user.avatar_url)
                .resize(500, 500)
                .centerCrop()
                .into(iconIV)
    }

    fun setUserRepos() {

        val repoRV = findViewById(R.id.repoRV) as RecyclerView

        repoAdapter = RepoAdapter(applicationContext, RepoAdapter.OnItemClickListener { repo ->
            val intent = Intent(applicationContext, RepoActivity::class.java)
            intent.putExtra("EXTRA_USER", user)
            intent.putExtra("EXTRA_REPO", repo)
            startActivity(intent)
        })
        val linearLayoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        repoRV.layoutManager = linearLayoutManager
        repoRV.adapter = repoAdapter

        getUserRepos()
    }

    fun getUserRepos() {
        val githubReposApi = GithubReposApi(applicationContext, GithubReposApi.ProcessFinish { repos ->
            if (repos.size > 0)
                repoAdapter.insertAll(repos)
            else
                Toast.makeText(applicationContext, "Oups, un problème est survenu", Toast.LENGTH_LONG).show()
        })
        githubReposApi.execute(user.repos_url)
    }
}