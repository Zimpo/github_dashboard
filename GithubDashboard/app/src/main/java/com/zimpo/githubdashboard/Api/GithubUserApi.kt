package com.zimpo.githubdashboard.Api

import android.content.Context
import android.os.AsyncTask
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.zimpo.githubdashboard.Adapter.RepoAdapter
import com.zimpo.githubdashboard.Model.User
import java.util.ArrayList
import com.squareup.picasso.Downloader
import org.json.JSONException
import org.json.JSONObject


class GithubUserApi(private val context: Context, private val delegate: ProcessFinish) : AsyncTask<String, Void, Void>() {

    val users = ArrayList<User>()

    interface ProcessFinish {
        fun processFinish(users: ArrayList<User>)

        companion object {
            inline operator fun invoke(crossinline op: (users: ArrayList<User>) -> Unit) =
                    object : ProcessFinish {
                        override fun processFinish(users: ArrayList<User>) = op(users)
                    }
        }
    }

    override fun doInBackground(vararg searches: String): Void? {
        // HTTP POST
        var search = searches[0]
        search = search.replace(' ', '+')
        val url = "https://api.github.com/search/users?q=$search"
        val requestQueue = Volley.newRequestQueue(context)
        val jsonObject = JSONObject()
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url,
                jsonObject, Response.Listener { response ->
            try {
                //JSONObject count = (JSONObject) response.get("total_count");
                val count = response.getInt("total_count")
                if (count < 0) {
                    Toast.makeText(context, "Aucun utiliseur n'a été trouvé", Toast.LENGTH_LONG).show()
                    return@Listener
                }

                val items = response.getJSONArray("items")
                for (i in 0 until items.length()) {
                    val user = items.getJSONObject(i)
                    //ID
                    val id = user.getInt("id")
                    //LOGIN
                    val login = user.getString("login")
                    //AVATAR_URL
                    val avatar_url = user.getString("avatar_url")
                    //URL
                    val url = user.getString("url")
                    //REPOS_URL
                    val repos_url = user.getString("repos_url")

                    users.add(User(id, login, avatar_url, url, repos_url))
                }

                delegate.processFinish(users)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }, Response.ErrorListener { error ->
            Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show()
            return@ErrorListener
        })
        requestQueue.add(jsonObjectRequest)
        return null
    }
}
