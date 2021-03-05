package com.example.parstagram;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private Context context;
    private List<Post> posts;
    private boolean clicked;

    public PostAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.one_post, parent, false);
        clicked = false;
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return this.posts.size();
    }


    public void clear(){
        posts.clear();
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvUsername;
        private TextView tvDescription;
        private ImageView ivPostImage;
        private ImageButton ibLikes;
        private ImageButton ibComments;
        private ImageButton ibDirect;
        private ImageButton ibSaved;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername      = itemView.findViewById(R.id.tvUsername);
            tvDescription   = itemView.findViewById(R.id.tvDescription);
            ivPostImage     = itemView.findViewById(R.id.ivPostImage);
            ibLikes         = itemView.findViewById(R.id.ibLikes);
            ibComments      = itemView.findViewById(R.id.ibComments);
            ibDirect        = itemView.findViewById(R.id.ibDirect);
            ibSaved         = itemView.findViewById(R.id.ibSaved);

        }

        public void bind(Post post) {
            ParseFile image = post.getImage();
            if (image != null) {
                tvDescription.setText(post.getDescription());
                tvUsername.setText(post.getUser().getUsername());
                Glide.with(context).load(image.getUrl()).into(ivPostImage);
            }


            ibLikes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clicked) {
                        ibLikes.setBackground(context.getDrawable(R.drawable.ufi_heart));
                        Toast.makeText(context, "Liked Post", Toast.LENGTH_SHORT).show();
                        clicked = false;
                    }else{
                        ibLikes.setBackground(context.getDrawable(R.drawable.ufi_heart_active));
                        Toast.makeText(context, "Unliked Post", Toast.LENGTH_SHORT).show();
                        clicked = true;
                    }
                }
            });
        }
    }
}
