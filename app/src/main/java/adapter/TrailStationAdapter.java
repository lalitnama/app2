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


    public TrailStationAdapter(Context parent, List<TrailStation> stationList) {
        this.tailstationcontext = parent;
        this.mTrailstationValues = stationList;
    }

    public void onBindViewHolder(TrailStationViewHolder holder, int position) {
        holder.learningtrailid.setText(mTrailstationValues.get(position).getTrailid());
        holder.trailstationid.setText(mTrailstationValues.get(position).getTrailstationid());
        holder.stationname.setText(mTrailstationValues.get(position).getStationname());
        holder.instruction.setText(mTrailstationValues.get(position).getInstruction());
        holder.thumbnail.setImageResource(R.drawable.ic_add_circle_black_24dp);

        holder.itemView.setTag(mTrailstationValues.get(position));
    }

    @Override
    public TrailStationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trail_station_list_data, parent, false);
        return new TrailStationViewHolder(view);
    }
    public class TrailStationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView trailstationid,learningtrailid,stationname,instruction;
        public ImageView thumbnail;
        public RelativeLayout trail_view_background, trail_view_foreground;

        ItemClickListener itemClickListener;

        public TrailStationViewHolder(View view) {
            super(view);
            trailstationid = (TextView) view.findViewById(R.id.trailstationid);
            learningtrailid = (TextView) view.findViewById(R.id.learningtrailid);
            stationname = (TextView) view.findViewById(R.id.stationname);
            instruction = (TextView) view.findViewById(R.id.instruction);
            thumbnail = view.findViewById(R.id.thumbnail);
            trail_view_foreground = view.findViewById(R.id.trail_view_foreground);

            trail_view_background = view.findViewById(R.id.trail_view_background);

            itemView.setOnClickListener(this);
        }
        public void setItemClickListener(ItemClickListener itemClickListener)
        {
            this.itemClickListener=itemClickListener;
        }

        public void onClick(View view) {
            this.itemClickListener.onItemClick(this.getLayoutPosition());
        }
    }

    public int getItemCount() {
        return mTrailstationValues.size();
    }






}
