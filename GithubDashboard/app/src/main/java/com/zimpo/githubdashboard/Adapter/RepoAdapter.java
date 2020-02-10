package com.zimpo.githubdashboard.Adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zimpo.githubdashboard.Object.Repo;
import com.zimpo.githubdashboard.R;

import java.util.ArrayList;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.RepoViewHolder> {

    Context context;
    ArrayList<Repo> repos;
    OnItemClickListener listener;

    public interface OnItemClickListener
    {
        void onItemClickListener(Repo repo);
    }

    public RepoAdapter(Context context, OnItemClickListener listener)
    {
        this.context = context;
        this.listener = listener;
        repos = new ArrayList<>();
    }


    public void insertAll(ArrayList<Repo> repos)
    {
        this.repos.clear();
        this.repos.addAll(repos);
        notifyDataSetChanged();
    }

    @Override
    public RepoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_repo, parent, false);
        RepoViewHolder repoViewHolder = new RepoViewHolder(view);
        return repoViewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(RepoViewHolder holder, int position) {
        holder.Bind(repos.get(position));
    }

    @Override
    public int getItemCount() {
        return repos.size();
    }


    public class RepoViewHolder extends RecyclerView.ViewHolder
    {
        //VARIABLES
        TextView nameTV;

        public RepoViewHolder(View itemView) {
            super(itemView);

            nameTV = (TextView) itemView.findViewById(R.id.nameTV);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public void Bind(final Repo repo) {

            nameTV.setText(repo.getName());
        }
    }
}