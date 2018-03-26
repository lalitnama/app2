package adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import model.LearningTrial;
import trailblazelearn.nus.edu.sg.trailblazelearn.R;
import trailblazelearn.nus.edu.sg.trailblazelearn.activity.LearningTrailActivity;
import trailblazelearn.nus.edu.sg.trailblazelearn.activity.LearningTrailDetailActivity;
import util.ItemClickListener;

/**
 * Created by Asif on 3/18/2018.
 */

public class LearningTrailAdapter extends RecyclerView.Adapter<LearningTrailAdapter.MyViewHolder> {
    public Context context;
    public LearningTrailActivity mParentActivity;
    public List<LearningTrial> mValues;
    public boolean mTwoPane;



    public LearningTrailAdapter(Context parent, List<LearningTrial> cartList) {
        this.context = parent;
        this.mValues = cartList;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.learningtrailid.setText(mValues.get(position).getLearningtrailid());
        holder.trailname.setText(mValues.get(position).getTrailname());
        holder.userid.setText(mValues.get(position).getUserid());
        holder.thumbnail.setImageResource(R.drawable.ic_hdr_weak_black_18dp);

        holder.itemView.setTag(mValues.get(position));
        //holder.itemView.setOnClickListener(mOnClickListener);

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                //OPEN DETAI ACTIVITY
                openDetailActivity(mValues.get(pos).getLearningtrailid(),mValues.get(pos).getTrailname(),mValues.get(pos).getUserid());
            }
        });

    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.module_list_content, parent, false);
        return new MyViewHolder(view);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView learningtrailid,userid,trailname;
        public Date traildate;
        public ImageView thumbnail;
        public RelativeLayout viewBackground, viewForeground;
        ItemClickListener itemClickListener;
        public MyViewHolder(View view) {
            super(view);

            learningtrailid = (TextView) view.findViewById(R.id.learningtrailid);
            trailname = (TextView) view.findViewById(R.id.learningtrailname);
            userid = (TextView) view.findViewById(R.id.userid);
            thumbnail = view.findViewById(R.id.thumbnail);
            viewBackground = view.findViewById(R.id.view_background);
            viewForeground = view.findViewById(R.id.view_foreground);

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


    @Override
    public int getItemCount() {
        return mValues.size();
    }

    private void openDetailActivity(String...details)
    {
        Intent i=new Intent(context,LearningTrailDetailActivity.class);

        i.putExtra("LEARNING_TRAIL_ID",details[0]);
        i.putExtra("LEARNING_TRAIL_NAME",details[1]);
        i.putExtra("USER_ID",details[2]);

        context.startActivity(i);
    }
}
