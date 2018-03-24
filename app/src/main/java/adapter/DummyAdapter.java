package adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import trailblazelearn.nus.edu.sg.trailblazelearn.R;
import trailblazelearn.nus.edu.sg.trailblazelearn.activity.LearningTrailActivity;
import trailblazelearn.nus.edu.sg.trailblazelearn.activity.LearningTrailDetailActivity;
import trailblazelearn.nus.edu.sg.trailblazelearn.dummy.DummyContent;
import trailblazelearn.nus.edu.sg.trailblazelearn.fragment.LearningTrailDetailFragment;

/**
 * Created by Asif on 3/19/2018.
 */

public class DummyAdapter extends RecyclerView.Adapter<DummyAdapter.MyViewHolder>  {
    public Context context;
    public LearningTrailActivity mParentActivity;
    public List<DummyContent.DummyItem> mValues;
    public boolean mTwoPane;



    public DummyAdapter(Context parent, List<DummyContent.DummyItem> cartList) {
        this.context = parent;
        this.mValues = cartList;
    }


    @Override
    public void onBindViewHolder(DummyAdapter.MyViewHolder holder, int position) {
        holder.id.setText(mValues.get(position).id);
        holder.content.setText(mValues.get(position).content);
        holder.details.setText(mValues.get(position).details);
        holder.thumbnail.setImageResource(R.drawable.ic_hdr_weak_black_18dp);

        holder.itemView.setTag(mValues.get(position));
        holder.itemView.setOnClickListener(mOnClickListener);
    }



    @Override
    public DummyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.module_list_content, parent, false);
        return new DummyAdapter.MyViewHolder(view);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView content, details, id;
        public ImageView thumbnail;
        public RelativeLayout viewBackground, viewForeground;

        public MyViewHolder(View view) {
            super(view);

            content = (TextView) view.findViewById(R.id.learningtrailname);
            details = (TextView) view.findViewById(R.id.learningtrailid);
            id = (TextView) view.findViewById(R.id.userid);
            thumbnail = view.findViewById(R.id.thumbnail);
            viewBackground = view.findViewById(R.id.view_background);
            viewForeground = view.findViewById(R.id.view_foreground);

        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public  View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            DummyContent.DummyItem item = (DummyContent.DummyItem) view.getTag();
            if (mTwoPane) {
                Bundle arguments = new Bundle();
                arguments.putString(LearningTrailDetailFragment.ARG_ITEM_ID, item.id);
                LearningTrailDetailFragment fragment = new LearningTrailDetailFragment();
                fragment.setArguments(arguments);
                mParentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.module_detail_container, fragment)
                        .commit();
            } else {
                Context context = view.getContext();
                Intent intent = new Intent(context, LearningTrailDetailActivity.class);
                intent.putExtra(LearningTrailDetailFragment.ARG_ITEM_ID, item.id);

                context.startActivity(intent);
            }
        }
    };
}
