package com.example.mahmoud.bakingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mahmoud.bakingapp.models.Ingredient;
import com.example.mahmoud.bakingapp.models.Recipe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mahmoud on 03/06/2017.
 */

public class IngrAdapter extends RecyclerView.Adapter<IngrAdapter.CustomViewHolder> {
    Context mcontext;

    List<Ingredient> data;
    View itemView;

    public IngrAdapter(Context mcontext, List<Ingredient> data) {
        this.mcontext = mcontext;
        this.data = data;
    }

    @Override
    public IngrAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingr_item, parent, false);

        return new CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        try {
            // String xxx=data.get(position).getIngredient();
            holder.tv_ing_name.setText(data.get(position).getIngredient());
            holder.tv_mes.setText(data.get(position).getMeasure());
            holder.tv_quant.setText(data.get(position).getQuantity() + "");
        } catch (Exception ex) {

            ex.printStackTrace();
            String x;
        }

    }


    @Override
    public int getItemCount() {
        return data.size();
    }


    class CustomViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_ing_name)
        TextView tv_ing_name;
        @BindView(R.id.tv_mes)
        TextView tv_mes;
        @BindView(R.id.tv_quan)
        TextView tv_quant;

        public CustomViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(CustomViewHolder.this, itemView);

        }


    }


}
