package com.zimpo.githubdashboard.Api

import android.content.Context
import android.os.AsyncTask
import android.os.Build
import android.widget.Toast

import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.zimpo.githubdashboard.Model.Repo

import org.json.JSONArray
import org.json.JSONException

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Date

class GithubReposApi(private val context: Context, private val delegate: ProcessFinish) : AsyncTask<String, Void, Void>() {

    val repos = ArrayList<Repo>()

    interface ProcessFinish {
        fun processFinish(repos: ArrayList<Repo>)

        companion object {
            inline operator fun invoke(crossinline op: (repos: ArrayList<Repo>) -> Unit) =
                    object : GithubReposApi.ProcessFinish {
                        override fun processFinish(repos: ArrayList<Repo>) = op(repos)
                    }
        }
    }

    override fun doInBackground(vararg urls: String): Void? {
        // HTTP POST
        val url = urls[0]
        val requestQueue = Volley.newRequestQueue(context)
        val jsonArray = JSONArray()
        val jsonArrayRequest = JsonArrayRequest(Request.Method.GET, url,
                jsonArray, Response.Listener { response ->
            try {
                val count = response.length()
                if (count < 0) {
                    Toast.makeText(context, "Aucun repo n'a été trouvé pour cet utilisateur.", Toast.LENGTH_LONG).show()
                    return@Listener
                }

                for (i in 0 until response.length()) {
                    val repo = response.getJSONObject(i)
                    //ID
                    val id = repo.getInt("id")
                    //NAME
                    val name = repo.getString("name")
                    //LANGUAGES_URL
                    val language = repo.getString("language")
                    //STARS
                    val stars = repo.getInt("stargazers_count")
                    //DESCRIPTION
                    var description = ""
                    if (!repo.isNull("description"))
                        description = repo.getString("description")
                    //DATE_CREATED
                    val dateStr = repo.getString("created_at")
                    var date: Date = Date()
                    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
                    try {
                        date = format.parse(dateStr)
                    } catch (e: ParseException) {
                        e.printStackTrace()
                    }

                    //URL
                    val url = repo.getString("url")

                    repos.add(Repo(id, name, language, stars, description, date, url))
                }

                delegate.processFinish(repos)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }, Response.ErrorListener { error ->
            Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show()
            return@ErrorListener
        })
        requestQueue.add(jsonArrayRequest)
        return null
    }
}