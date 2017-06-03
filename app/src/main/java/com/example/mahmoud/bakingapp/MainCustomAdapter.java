package com.example.mahmoud.bakingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mahmoud.bakingapp.models.Recipe;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * Created by Mahmoud on 31/05/2017.
 */

public class MainCustomAdapter extends RecyclerView.Adapter<MainCustomAdapter.CustomViewHolder> {


    Context mcontext;

    ArrayList<Recipe> data;
    ClickListener clickListener;

    public MainCustomAdapter(final Context mcontext, final ArrayList<Recipe> data, RecyclerView recyclerView) {
        this.mcontext = mcontext;
        this.data = data;

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(mcontext, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
              //  Toast.makeText(mcontext, data.get(position).getName(), Toast.LENGTH_SHORT).show();

                MainActivity.loadDetailedFragment(data.get(position));

            }
        }));

    }

    View itemView;

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem, parent, false);


        return new CustomViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        final Recipe element = data.get(position);
        holder.title.setText(data.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        public CustomViewHolder(View itemView) {
            super(itemView);
            //  ButterKnife.bind(CustomViewHolder.this, itemView);
            title = (TextView) itemView.findViewById(R.id.tv_title);

        }


    }



}
