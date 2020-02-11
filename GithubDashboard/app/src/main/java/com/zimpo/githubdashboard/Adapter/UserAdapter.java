package com.zimpo.githubdashboard.Adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zimpo.githubdashboard.Object.Repo;
import com.zimpo.githubdashboard.Object.User;
import com.zimpo.githubdashboard.R;

import java.util.ArrayList;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    Context context;
    ArrayList<User> users;
    UserAdapter.OnItemClickListener listener;

    public interface OnItemClickListener
    {
        void onItemClickListener(User user);
    }

    public UserAdapter(Context context, UserAdapter.OnItemClickListener listener)
    {
        this.context = context;
        this.listener = listener;
        users = new ArrayList<>();
    }


    public void insertAll(ArrayList<User> users)
    {
        this.users.clear();
        this.users.addAll(users);
        notifyDataSetChanged();
    }

    @Override
    public UserAdapter.UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_user, parent, false);
        UserAdapter.UserViewHolder userViewHolder = new UserAdapter.UserViewHolder(view);
        return userViewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(UserAdapter.UserViewHolder holder, int position) {
        holder.Bind(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }


    public class UserViewHolder extends RecyclerView.ViewHolder
    {
        //VARIABLES
        RelativeLayout userRL;
        ImageView iconIV;
        TextView nameTV;

        public UserViewHolder(View itemView) {
            super(itemView);

            userRL = (RelativeLayout) itemView.findViewById(R.id.userRL);
            nameTV = (TextView) itemView.findViewById(R.id.nameTV);
            iconIV = (ImageView) itemView.findViewById(R.id.iconIV);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public void Bind(final User user) {

            userRL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClickListener(user);
                }
            });

            nameTV.setText(user.getLogin());

            Picasso.with(context)
                    .load(user.getAvatar_url())
                    .resize(500, 500)
                    .centerCrop()
                    .into(iconIV);
        }
    }
}
