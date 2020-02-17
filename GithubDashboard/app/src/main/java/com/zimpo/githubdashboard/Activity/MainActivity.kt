package com.zimpo.githubdashboard.Activity

import androidx.appcompat.app.AppCompatActivity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

import com.zimpo.githubdashboard.Api.GithubUserApi
import com.zimpo.githubdashboard.R


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configure()
    }

    private lateinit var searchET: EditText
    private lateinit var warningTV: TextView
    private lateinit var searchB: Button
    private lateinit var getB: Button

    fun configure() {
        //VARIABLES
        searchET = findViewById(R.id.searchET) as EditText
        warningTV = findViewById(R.id.warningTV) as TextView
        searchB = findViewById(R.id.searchB) as Button
        getB = findViewById(R.id.getB) as Button

        searchUser()

        getUser()
    }

    fun searchUser() {
        searchB.setOnClickListener {
            if (searchET.text.toString().length > 0) {
                warningTV.visibility = View.GONE

                val intent = Intent(applicationContext, UserListActivity::class.java)
                intent.putExtra("EXTRA_SEARCH", searchET.text.toString())
                startActivity(intent)

            } else
                warningTV.visibility = View.VISIBLE
        }
    }

    fun getUser() {
        getB.setOnClickListener {
            if (searchET.text.toString().length > 0) {
                warningTV.visibility = View.GONE

                val githubUserApi = GithubUserApi(applicationContext, GithubUserApi.ProcessFinish { users ->

                    if (users != null && users.size > 0) {
                        val intent = Intent(applicationContext, UserActivity::class.java)
                        intent.putExtra("EXTRA_USER", users[0])
                        startActivity(intent)
                    } else
                        Toast.makeText(applicationContext, "Oups, un problème est survenu", Toast.LENGTH_LONG).show()

                })
                githubUserApi.execute(searchET.text.toString())

            } else
                warningTV.visibility = View.VISIBLE
        }
    }
}