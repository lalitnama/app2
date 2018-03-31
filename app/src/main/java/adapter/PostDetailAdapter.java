package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import model.ContributedItem;
import trailblazelearn.nus.edu.sg.trailblazelearn.R;
import trailblazelearn.nus.edu.sg.trailblazelearn.activity.PostDetailActivity;

/**
 * Created by Asif on 3/31/2018.
 */

public class PostDetailAdapter extends RecyclerView.Adapter<PostDetailAdapter.MyViewHolder> {

    private List<ContributedItem> itemList;
    public Context context;

    public PostDetailAdapter(PostDetailActivity postDetailActivity, List<ContributedItem> itemList) {
        this.itemList =itemList;
    }

    @Override
    public PostDetailAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contributed_item, parent, false);
        return new PostDetailAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PostDetailAdapter.MyViewHolder holder, int position) {
        holder.blog_desc.setText(itemList.get(position).getDiscussion());
        holder.blog_title.setText(itemList.get(position).getTitle());
        String image_url = itemList.get(position).getFileUrl();
        holder.setImageUrl(context,image_url);


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void setList(List<ContributedItem> itemList) {
        this.itemList  = itemList;
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder {
        TextView blog_desc,blog_title;
        ImageView blog_image;


        public MyViewHolder(View itemView) {
            super(itemView);
            blog_desc = itemView.findViewById(R.id.blog_desc);
            blog_title = itemView.findViewById(R.id.blog_title);



        }

        public void setImageUrl(Context ctx, String imageUrl){
            blog_image = itemView.findViewById(R.id.blog_image);

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.image_placeholder);
            Glide.with(ctx).applyDefaultRequestOptions(requestOptions).load(imageUrl).into(blog_image);

        }


    }


}
