package com.example.mahmoud.bakingapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mahmoud.bakingapp.models.Ingredient;
import com.example.mahmoud.bakingapp.models.Recipe;
import com.example.mahmoud.bakingapp.models.Step;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mahmoud on 03/06/2017.
 */

public class DetailFragment extends android.support.v4.app.Fragment {

    Recipe recipe;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.rv_ingridient)
    RecyclerView rv_ingridient;
    @BindView(R.id.rv_steps)
    RecyclerView rv_steps;

    Context mcontext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_layout, container, false);
        ButterKnife.bind(this, view);
        MainActivity.backCounter = 0;
        mcontext = getActivity().getApplicationContext();

        Bundle bundle = this.getArguments();
        if (!bundle.isEmpty()) {
            recipe = bundle.getParcelable("recipe");
        } else {
            recipe = savedInstanceState.getParcelable("detail_recipe");
        }
        int id = recipe.getId();
        String img = recipe.getImage();
        String name = recipe.getName();
        tv_name.setText(name);
        int serving = recipe.getServings();
        List<Step> steps = recipe.getSteps();
        List<Ingredient> ingredients = recipe.getIngredients();

        LinearLayoutManager layoutManager = new LinearLayoutManager(mcontext, LinearLayoutManager.HORIZONTAL, false);

        rv_ingridient.setLayoutManager(layoutManager);
        rv_ingridient.setItemAnimator(new DefaultItemAnimator());
        rv_ingridient.setAdapter(new IngrAdapter(mcontext, ingredients));

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(mcontext, 1);
        rv_steps.setLayoutManager(mLayoutManager);
        rv_steps.setItemAnimator(new DefaultItemAnimator());
        rv_steps.setAdapter(new StepsAdapter(mcontext, steps, rv_steps));
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        if (recipe != null)
            outState.putParcelable("detail_recipe", recipe);
        super.onSaveInstanceState(outState);


    }
}
