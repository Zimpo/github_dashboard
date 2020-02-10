package com.zimpo.githubdashboard.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zimpo.githubdashboard.Api.GithubUserApi;
import com.zimpo.githubdashboard.Object.User;
import com.zimpo.githubdashboard.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configure();
    }

    private EditText searchET = null;
    private TextView warningTV = null;
    private Button searchB = null;
    private Button getB = null;

    public void configure()
    {
        //VARIABLES
        searchET = (EditText) findViewById(R.id.searchET);
        warningTV = (TextView) findViewById(R.id.warningTV);
        searchB = (Button) findViewById(R.id.searchB);
        getB = (Button) findViewById(R.id.getB);

        searchUser();

        getUser();
    }

    public void searchUser()
    {

    }

    public void getUser()
    {
        getB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (searchET.getText().toString().length() > 0)
               {
                   warningTV.setVisibility(View.GONE);

                   GithubUserApi githubUserApi = new GithubUserApi(getApplicationContext(), new GithubUserApi.ProcessFinish() {
                       @Override
                       public void processFinish(ArrayList<User> users) {
                           if (users != null && users.size() > 0)
                           {
                               Intent intent = new Intent(getApplicationContext(), UserActivity.class);
                               intent.putExtra("EXTRA_USER", users.get(0));
                               startActivity(intent);
                           }
                           else
                               Toast.makeText(getApplicationContext(), "Oups, un problème est survenu", Toast.LENGTH_LONG).show();
                       }
                   });
                   githubUserApi.execute(searchET.getText().toString());

               }
               else
                   warningTV.setVisibility(View.VISIBLE);
            }
        });
    }
}
