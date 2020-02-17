package com.zimpo.githubdashboard.Adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView

import com.squareup.picasso.Picasso
import com.zimpo.githubdashboard.Model.User
import com.zimpo.githubdashboard.R

import java.util.ArrayList

import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.zimpo.githubdashboard.Model.Repo

class UserAdapter(private var context: Context, private var listener: UserAdapter.OnItemClickListener) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private var users: ArrayList<User>

    interface OnItemClickListener {
        fun onItemClickListener(user: User)

        companion object {
            inline operator fun invoke(crossinline op: (user: User) -> Unit) =
                    object : UserAdapter.OnItemClickListener {
                        override fun onItemClickListener(user: User) = op(user)
                    }
        }
    }

    init {
        users = ArrayList()
    }


    fun insertAll(users: ArrayList<User>) {
        this.users.clear()
        this.users.addAll(users)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_user, parent, false)
        return UserViewHolder(view)
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: UserAdapter.UserViewHolder, position: Int) {
        holder.Bind(users[position])
    }

    override fun getItemCount(): Int {
        return users.size
    }


    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //VARIABLES
        private var userRL: RelativeLayout
        private var iconIV: ImageView
        private var nameTV: TextView

        init {
            userRL = itemView.findViewById(R.id.userRL) as RelativeLayout
            nameTV = itemView.findViewById(R.id.nameTV) as TextView
            iconIV = itemView.findViewById(R.id.iconIV) as ImageView
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        fun Bind(user: User) {

            userRL.setOnClickListener { listener.onItemClickListener(user) }

            nameTV.setText(user.login)

            Picasso.with(context)
                    .load(user.avatar_url)
                    .resize(500, 500)
                    .centerCrop()
                    .into(iconIV)
        }
    }
}