package com.zimpo.githubdashboard.Activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.format.DateUtils
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast

import com.squareup.picasso.Picasso
import com.zimpo.githubdashboard.Model.Repo
import com.zimpo.githubdashboard.Model.User
import com.zimpo.githubdashboard.R

import java.util.Calendar

import androidx.appcompat.app.AppCompatActivity

class RepoActivity : AppCompatActivity() {

    private lateinit var user: User
    private lateinit var repo: Repo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo)

        //Bundle
        val extra_bundle = intent.extras
        //User
        if (extra_bundle.containsKey("EXTRA_REPO") && extra_bundle.containsKey("EXTRA_USER")) {
            user = extra_bundle.getSerializable("EXTRA_USER") as User

            repo = extra_bundle.getSerializable("EXTRA_REPO") as Repo

            configureHeader()

            configureBody()
        } else
            Toast.makeText(applicationContext, "Oups, un problème est survenu", Toast.LENGTH_LONG).show()
    }

    fun configureHeader() {
        //VARIABLES
        val headerRL = findViewById(R.id.headerRL) as RelativeLayout
        val iconIV = findViewById(R.id.iconIV) as ImageView
        val loginTV = findViewById(R.id.loginTV) as TextView
        val urlTV = findViewById(R.id.urlTV) as TextView

        //
        headerRL.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(repo.url)
            startActivity(i)
        }

        loginTV.text = user.login

        urlTV.text = repo.url

        Picasso.with(applicationContext)
                .load(user.avatar_url)
                .resize(500, 500)
                .centerCrop()
                .into(iconIV)
    }

    fun configureBody() {
        //VARIABLES
        val nameTV = findViewById(R.id.nameTV) as TextView
        val languageTV = findViewById(R.id.languageTV) as TextView
        val descriptionTV = findViewById(R.id.descTV) as TextView
        val starTV = findViewById(R.id.starTV) as TextView
        val dateTV = findViewById(R.id.dateTV) as TextView

        nameTV.text = repo.name

        languageTV.text = repo.language

        if (repo.description != null)
            descriptionTV.text = repo.description

        starTV.text = repo.stars.toString()

        val infoStr = "Créé " + DateUtils.getRelativeTimeSpanString(repo.date_created.time,
                Calendar.getInstance().timeInMillis, DateUtils.MINUTE_IN_MILLIS) as String
        dateTV.text = infoStr
    }
}