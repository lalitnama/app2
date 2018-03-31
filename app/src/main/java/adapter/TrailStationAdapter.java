package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import model.TrailStation;
import trailblazelearn.nus.edu.sg.trailblazelearn.R;
import trailblazelearn.nus.edu.sg.trailblazelearn.activity.LearningTrailDetailActivity;
import util.ItemClickListener;

/**
 * Created by Asif on 3/24/2018.
 */

public class TrailStationAdapter extends RecyclerView.Adapter<TrailStationAdapter.TrailStationViewHolder> {

    public Context tailstationcontext;
    public LearningTrailDetailActivity mParentActivity;
    public List<TrailStation> mTrailstationValues;
    private  ItemClickListener itemClickListener;

    public TrailStationAdapter(Context parent, List<TrailStation> stationList, ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
        this.tailstationcontext = parent;
        this.mTrailstationValues = stationList;
    }

    public void onBindViewHolder(TrailStationViewHolder holder, int position) {
        holder.stationname.setText(mTrailstationValues.get(position).getStationname());
        holder.instruction.setText(mTrailstationValues.get(position).getInstruction());
        holder.thumbnail.setImageResource(R.drawable.wall3);

        holder.itemView.setTag(mTrailstationValues.get(position));
        holder.setPosition(position);
    }

    @Override
    public TrailStationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trail_station_list_data, parent, false);
        return new TrailStationViewHolder(view);
    }
    public class TrailStationViewHolder extends RecyclerView.ViewHolder {
        public TextView stationname, instruction;
        public ImageView thumbnail;
        public RelativeLayout trail_view_background, trail_view_foreground;
        int position;

        public TrailStationViewHolder(View view) {
            super(view);
            stationname = (TextView) view.findViewById(R.id.stationname);
            instruction = (TextView) view.findViewById(R.id.instruction);
            thumbnail = view.findViewById(R.id.thumbnail);
            trail_view_foreground = view.findViewById(R.id.trail_view_foreground);
            trail_view_background = view.findViewById(R.id.trail_view_background);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(itemClickListener != null)
                        itemClickListener.onItemClick(position);
                }
            });
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }

    public TrailStation getItemForPosition(int position){
        return mTrailstationValues.get(position);
    }

    public int getItemCount() {
        return mTrailstationValues.size();
    }




}
