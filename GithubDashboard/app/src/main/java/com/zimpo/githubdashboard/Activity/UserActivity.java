package com.zimpo.githubdashboard.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.zimpo.githubdashboard.Adapter.RepoAdapter;
import com.zimpo.githubdashboard.Api.GithubReposApi;
import com.zimpo.githubdashboard.Api.GithubUserApi;
import com.zimpo.githubdashboard.Object.Repo;
import com.zimpo.githubdashboard.Object.User;
import com.zimpo.githubdashboard.R;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class UserActivity extends AppCompatActivity {

    private User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        //Bundle
        final Bundle extra_bundle = getIntent().getExtras();
        //User
        if (extra_bundle.containsKey("EXTRA_USER"))
        {
            user = (User) extra_bundle.getSerializable("EXTRA_USER");
            configure();
        }
        else
            Toast.makeText(getApplicationContext(), "Oups, un problème est survenu", Toast.LENGTH_LONG).show();
    }

    private ImageView iconIV = null;
    private TextView nameTV = null;
    private RecyclerView repoRV = null;

    public void configure()
    {
        //VARIABLES
        iconIV = (ImageView) findViewById(R.id.iconIV);
        nameTV = (TextView) findViewById(R.id.nameTV);
        repoRV = (RecyclerView) findViewById(R.id.repoRV);

        setUserInfo();

        setUserRepos();
    }

    public void setUserInfo()
    {
        nameTV.setText(user.getLogin());

        Picasso.with(getApplicationContext())
                .load(user.getAvatar_url())
                .resize(500, 500)
                .centerCrop()
                .into(iconIV);
    }

    RepoAdapter repoAdapter = null;
    public void setUserRepos()
    {
        repoAdapter = new RepoAdapter(getApplicationContext(), new RepoAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(Repo repo) {
                /*TODO*/
            }
        });
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, true);
        repoRV.setLayoutManager(linearLayoutManager);
        repoRV.setAdapter(repoAdapter);

        getUserRepos();
    }

    public void getUserRepos()
    {
        GithubReposApi githubReposApi = new GithubReposApi(getApplicationContext(), new GithubReposApi.ProcessFinish() {
            @Override
            public void processFinish(ArrayList<Repo> repos) {
                if (repos.size() > 0)
                    repoAdapter.insertAll(repos);
                else
                    Toast.makeText(getApplicationContext(), "Oups, un problème est survenu", Toast.LENGTH_LONG).show();
            }
        });
        githubReposApi.execute(user.getRepos_url());
    }
}
