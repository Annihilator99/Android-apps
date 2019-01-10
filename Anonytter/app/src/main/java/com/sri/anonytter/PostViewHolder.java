package com.sri.anonytter;

/**
 * Created by USER on 09-01-2018.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sri.anonytter.R;
import com.sri.anonytter.Post;

public class PostViewHolder extends RecyclerView.ViewHolder {

    public TextView titleView;

    public TextView bodyView;

    public PostViewHolder(View itemView) {
        super(itemView);

        titleView = itemView.findViewById(R.id.title);

        bodyView = itemView.findViewById(R.id.body);
    }

    public void bindToPost(Post post) {
        titleView.setText(post.title);
        bodyView.setText(post.body);

    }
}
