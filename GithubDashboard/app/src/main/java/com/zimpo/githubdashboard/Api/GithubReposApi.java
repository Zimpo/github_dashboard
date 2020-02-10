package com.zimpo.githubdashboard.Api;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.zimpo.githubdashboard.Object.Repo;
import com.zimpo.githubdashboard.Object.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import androidx.annotation.RequiresApi;

public class GithubReposApi extends AsyncTask {

    Context context;
    ProcessFinish delegate = null;
    ArrayList<Repo> repos = null;

    public interface ProcessFinish
    {
        void processFinish(ArrayList<Repo> repos);
    }

    public GithubReposApi(Context context, ProcessFinish delegate)
    {
        this.context = context;
        this.delegate = delegate;
        this.repos = new ArrayList<>();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected Object doInBackground(Object[] params) {
        // HTTP POST
        String url = (String) params[0];
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JSONArray jsonArray = new JSONArray();
        //jsonObject.put("key1", "value1");
        //jsonObject.put("key2", "value2");
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                jsonArray, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    //JSONObject count = (JSONObject) response.get("total_count");
                    int count = response.length();
                    if (count < 0)
                    {
                        Toast.makeText(context, "Aucun repo n'a été trouvé pour cet utilisateur.", Toast.LENGTH_LONG).show();
                        return;
                    }

                    for (int i = 0; i < response.length(); i++)
                    {
                        JSONObject repo = response.getJSONObject(i);
                        //ID
                        int id = repo.getInt("id");
                        //NAME
                        String name = repo.getString("name");
                        //LANGUAGES_URL
                        String language = repo.getString("language");
                        //STARS
                        int stars = repo.getInt("stargazers_count");
                        //DESCRIPTION
                        String description = "";
                        if (!repo.isNull("description"))
                            description = repo.getString("description");
                        //DATE_CREATED
                        String dateStr = repo.getString("created_at");
                        Date date = null;
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                        try {
                            date = format.parse(dateStr);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        //URL
                        String url = repo.getString("url");

                        repos.add(new Repo(id, name, language, stars, description, date, url));
                    }

                    delegate.processFinish(repos);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        , new Response.ErrorListener() {
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

        requestQueue.add(jsonArrayRequest);
        return true;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }
}
