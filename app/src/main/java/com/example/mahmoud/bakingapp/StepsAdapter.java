package com.example.mahmoud.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mahmoud.bakingapp.models.Step;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mahmoud on 03/06/2017.
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.CustomViewHolder> {
    Context mcontext;

    List<Step> data;
    View itemView;

    public StepsAdapter(final Context mcontext, final List<Step> data, RecyclerView recyclerView) {
        this.mcontext = mcontext;
        this.data = data;
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(mcontext, new RecyclerItemClickListener.OnItemClickListener() {


            @Override
            public void onItemClick(View view, int position) {

            /*    String x = data.get(position).getDescription();
                Toast.makeText(mcontext, data.get(position).getShortDescription(), Toast.LENGTH_SHORT).show();
*/

                if (data.get(position).getVideoURL() != "" && data.get(position).getVideoURL() != null) {
                    Intent intent = new Intent(mcontext, VideoActivity.class);
                    intent.putExtra("choosen_step", data.get(position));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    mcontext.startActivity(intent);
                }
            }
        }));
    }

    @Override
    public StepsAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.step_item, parent, false);


        return new CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(StepsAdapter.CustomViewHolder holder, int position) {
        String desc = data.get(position).getDescription();
        String shortdesc = data.get(position).getShortDescription();
        String img = data.get(position).getThumbnailURL();
        String videoUrl = data.get(position).getVideoURL();

        holder.tv_short.setText(shortdesc);
        if (!img.isEmpty()) {
//            Glide.with(mcontext).load(img).into(holder.iv_go);

         //   holder.playerView.setVisibility(View.VISIBLE);


        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_short)
        TextView tv_short;

        public CustomViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }


    }
}
