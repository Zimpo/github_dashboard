package com.zimpo.githubdashboard.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.zimpo.githubdashboard.Object.Repo;
import com.zimpo.githubdashboard.Object.User;
import com.zimpo.githubdashboard.R;

import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;

public class RepoActivity extends AppCompatActivity {

    private User user = null;
    private Repo repo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo);

        //Bundle
        final Bundle extra_bundle = getIntent().getExtras();
        //User
        if (extra_bundle.containsKey("EXTRA_REPO") && extra_bundle.containsKey("EXTRA_USER"))
        {
            user = (User) extra_bundle.getSerializable("EXTRA_USER");

            repo = (Repo) extra_bundle.getSerializable("EXTRA_REPO");

            configureHeader();

            configureBody();
        }
        else
            Toast.makeText(getApplicationContext(), "Oups, un problème est survenu", Toast.LENGTH_LONG).show();
    }

    private RelativeLayout headerRL = null;
    private ImageView iconIV = null;
    private TextView loginTV = null;
    private TextView urlTV = null;

    public void configureHeader()
    {
        //VARIABLES
        headerRL = (RelativeLayout) findViewById(R.id.headerRL);
        iconIV = (ImageView) findViewById(R.id.iconIV);
        loginTV = (TextView) findViewById(R.id.loginTV);
        urlTV = (TextView) findViewById(R.id.urlTV);

        //
        headerRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(repo.getUrl()));
                startActivity(i);
            }
        });

        loginTV.setText(user.getLogin());

        urlTV.setText(repo.getUrl());

        Picasso.with(getApplicationContext())
                .load(user.getAvatar_url())
                .resize(500, 500)
                .centerCrop()
                .into(iconIV);
    }

    private TextView nameTV = null;
    private TextView languageTV = null;
    private TextView descriptionTV = null;
    private TextView starTV = null;
    private TextView dateTV = null;

    public void configureBody()
    {
        //VARIABLES
        nameTV = (TextView) findViewById(R.id.nameTV);
        languageTV = (TextView) findViewById(R.id.languageTV);
        descriptionTV = (TextView) findViewById(R.id.descTV);
        starTV = (TextView) findViewById(R.id.starTV);
        dateTV = (TextView) findViewById(R.id.dateTV);

        nameTV.setText(repo.getName());

        languageTV.setText(repo.getLanguage());

        if (repo.getDescription() != null)
            descriptionTV.setText(repo.getDescription());

        starTV.setText(String.valueOf(repo.getStars()));

        String infoStr = "Créé "
                + (String) DateUtils.getRelativeTimeSpanString(repo.getDate_created().getTime(),
                Calendar.getInstance().getTimeInMillis(), DateUtils.MINUTE_IN_MILLIS);
        dateTV.setText(infoStr);
    }
}