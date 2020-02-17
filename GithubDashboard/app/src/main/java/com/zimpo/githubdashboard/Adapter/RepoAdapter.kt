package com.zimpo.githubdashboard.Adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView

import com.zimpo.githubdashboard.Model.Repo
import com.zimpo.githubdashboard.R

import java.util.ArrayList

import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.zimpo.githubdashboard.Api.GithubUserApi
import com.zimpo.githubdashboard.Model.User

class RepoAdapter(private var context: Context, private var listener: OnItemClickListener) : RecyclerView.Adapter<RepoAdapter.RepoViewHolder>() {
    private var repos: ArrayList<Repo>

    interface OnItemClickListener {
        fun onItemClickListener(repo: Repo)

        companion object {
            inline operator fun invoke(crossinline op: (repo: Repo) -> Unit) =
                    object : RepoAdapter.OnItemClickListener {
                        override fun onItemClickListener(repo: Repo) = op(repo)
                    }
        }
    }

    init {
        repos = ArrayList()
    }


    fun insertAll(repos: ArrayList<Repo>) {
        this.repos.clear()
        this.repos.addAll(repos)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_repo, parent, false)
        return RepoViewHolder(view)
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.Bind(repos[position])
    }

    override fun getItemCount(): Int {
        return repos.size
    }


    inner class RepoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //VARIABLES
        private var repoRL: RelativeLayout
        private var nameTV: TextView

        init {
            repoRL = itemView.findViewById(R.id.repoRL) as RelativeLayout
            nameTV = itemView.findViewById(R.id.nameTV) as TextView
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        fun Bind(repo: Repo) {

            repoRL.setOnClickListener { listener.onItemClickListener(repo) }

            nameTV.setText(repo.name)
        }
    }
}