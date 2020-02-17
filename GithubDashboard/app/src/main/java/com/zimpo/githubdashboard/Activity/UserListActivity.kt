package com.zimpo.githubdashboard.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast

import com.zimpo.githubdashboard.Adapter.UserAdapter
import com.zimpo.githubdashboard.Api.GithubUserApi
import com.zimpo.githubdashboard.R

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class UserListActivity : AppCompatActivity() {

    private lateinit var search: String
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)

        //Bundle
        val extra_bundle = intent.extras
        //User
        if (extra_bundle.containsKey("EXTRA_SEARCH")) {
            search = extra_bundle.getString("EXTRA_SEARCH")
            setUserList()
        } else
            Toast.makeText(applicationContext, "Oups, un problème est survenu", Toast.LENGTH_LONG).show()
    }


    fun setUserList() {

        val userRV = findViewById(R.id.userRV) as RecyclerView

        userAdapter = UserAdapter(applicationContext, UserAdapter.OnItemClickListener { user ->
            val intent = Intent(applicationContext, UserActivity::class.java)
            intent.putExtra("EXTRA_USER", user)
            startActivity(intent)
        })
        val linearLayoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        userRV.layoutManager = linearLayoutManager
        userRV.adapter = userAdapter

        getUsers()
    }

    fun getUsers() {
        val githubUserApi = GithubUserApi(applicationContext, GithubUserApi.ProcessFinish { users ->
            if (users != null && users.size > 0) {
                userAdapter.insertAll(users)
            } else
                Toast.makeText(applicationContext, "Oups, un problème est survenu", Toast.LENGTH_LONG).show()
        })
        githubUserApi.execute(search)
    }
}