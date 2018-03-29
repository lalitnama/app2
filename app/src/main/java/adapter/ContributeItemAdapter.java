package adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import model.ContributedItem;
import trailblazelearn.nus.edu.sg.trailblazelearn.R;
import trailblazelearn.nus.edu.sg.trailblazelearn.activity.TrailStationDetailActivity;

/**
 * Created by Asif on 3/30/2018.
 */

public class ContributeItemAdapter extends RecyclerView.Adapter<ContributeItemAdapter.MyViewHolder> {

    private List<ContributedItem> itemList;

    public ContributeItemAdapter(TrailStationDetailActivity trailStationDetailActivity, List<ContributedItem> itemList) {
        this.itemList =itemList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contributed_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvDiss.setText(itemList.get(position).getDiscussion());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void setList(List<ContributedItem> itemList) {
        this.itemList  = itemList;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvDiss;
        ImageView ivImage;
        public MyViewHolder(View itemView) {
            super(itemView);
            tvDiss = itemView.findViewById(R.id.tv_discussion);
            ivImage = itemView.findViewById(R.id.iv_image);
        }
    }
}
