package com.zimpo.githubdashboard.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.zimpo.githubdashboard.Adapter.UserAdapter;
import com.zimpo.githubdashboard.Api.GithubUserApi;
import com.zimpo.githubdashboard.Object.User;
import com.zimpo.githubdashboard.R;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class UserListActivity extends AppCompatActivity {

    private String search = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        //Bundle
        final Bundle extra_bundle = getIntent().getExtras();
        //User
        if (extra_bundle.containsKey("EXTRA_SEARCH"))
        {
            search = extra_bundle.getString("EXTRA_SEARCH");
            configure();
        }
        else
            Toast.makeText(getApplicationContext(), "Oups, un problème est survenu", Toast.LENGTH_LONG).show();
    }

    private RecyclerView userRV = null;

    public void configure()
    {
        //VARIABLES
        userRV = (RecyclerView) findViewById(R.id.userRV);

        setUserList();
    }

    UserAdapter userAdapter = null;
    public void setUserList()
    {
        userAdapter = new UserAdapter(getApplicationContext(), new UserAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(User user) {
                Intent intent = new Intent(getApplicationContext(), UserActivity.class);
                intent.putExtra("EXTRA_USER", user);
                startActivity(intent);
            }
        });
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        userRV.setLayoutManager(linearLayoutManager);
        userRV.setAdapter(userAdapter);

        getUsers();
    }

    public void getUsers()
    {
        GithubUserApi githubUserApi = new GithubUserApi(getApplicationContext(), new GithubUserApi.ProcessFinish() {
            @Override
            public void processFinish(ArrayList<User> users) {
                if (users != null && users.size() > 0)
                {
                    userAdapter.insertAll(users);
                }
                else
                    Toast.makeText(getApplicationContext(), "Oups, un problème est survenu", Toast.LENGTH_LONG).show();
            }
        });
        githubUserApi.execute(search);
    }
}