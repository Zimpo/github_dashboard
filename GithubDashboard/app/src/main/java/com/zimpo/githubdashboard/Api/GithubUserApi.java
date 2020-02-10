package com.zimpo.githubdashboard.Api;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.zimpo.githubdashboard.Object.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.RequiresApi;

public class GithubUserApi extends AsyncTask {

    Context context;
    ProcessFinish delegate = null;
    ArrayList<User> users = null;

    public interface ProcessFinish
    {
        void processFinish(ArrayList<User> users);
    }

    public GithubUserApi(Context context, ProcessFinish delegate)
    {
        this.context = context;
        this.delegate = delegate;
        this.users = new ArrayList<>();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected Object doInBackground(Object[] params) {
        // HTTP POST
        String search = (String) params[0];
        search = search.replace(' ', '+');
        String url = "https://api.github.com/search/users?q=" + search;
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JSONObject jsonObject = new JSONObject();
        //jsonObject.put("key1", "value1");
        //jsonObject.put("key2", "value2");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //JSONObject count = (JSONObject) response.get("total_count");
                    int count = response.getInt("total_count");
                    if (count < 0)
                    {
                        Toast.makeText(context, "Aucun utiliseur n'a été trouvé", Toast.LENGTH_LONG).show();
                        return;
                    }

                    JSONArray items = response.getJSONArray("items");
                    for (int i = 0; i < items.length(); i++)
                    {
                        JSONObject user = items.getJSONObject(i);
                        //ID
                        int id = user.getInt("id");
                        //LOGIN
                        String login = user.getString("login");
                        //AVATAR_URL
                        String avatar_url = user.getString("avatar_url");
                        //URL
                        String url = user.getString("url");
                        //REPOS_URL
                        String repos_url = user.getString("repos_url");

                        users.add(new User(id, login, avatar_url, url, repos_url));
                    }

                    delegate.processFinish(users);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                return;
            }
        });
        /* {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                final Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer BQA1f5XXzb4GkMWxsjQVyqhFSolnVLaAgXgLGG-MT3_BARxhRDWO0M2a3_rwIuYh95bHiXdu3mkcINLTQwxotM8LhtuS10Ghg2HqOWYL0z39R77l4kZBhSqS0SxGh3FMialRBTinehiWjA");
                return headers;
            }
        };*/
        requestQueue.add(jsonObjectRequest);
        return true;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }
}
