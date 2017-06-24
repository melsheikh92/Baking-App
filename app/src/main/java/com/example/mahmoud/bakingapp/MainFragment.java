package com.example.mahmoud.bakingapp;

import android.annotation.SuppressLint;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mahmoud.bakingapp.models.Ingredient;
import com.example.mahmoud.bakingapp.models.Recipe;
import com.example.mahmoud.bakingapp.models.Step;

import org.json.JSONArray;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainFragment extends android.support.v4.app.Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Recipe>> {

    @BindView(R.id.recycleview)
    RecyclerView recyclerView;
    Context mcontext;
    LoaderManager loaderManager;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    ArrayList<Recipe> recipes;
    Loader<ArrayList<Recipe>> mloader;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);
        MainActivity.backCounter = 2;
        mcontext = getActivity().getApplicationContext();
        ButterKnife.bind(this, view);
        loaderManager = getActivity().getLoaderManager();
        mloader = loaderManager.getLoader(12);
        if (mloader == null) {
            mloader = loaderManager.initLoader(12, null, this);
        } else {

            mloader = loaderManager.restartLoader(12, null, this);

        }
        // loaderManager.initLoader(12, null, this);
        if (savedInstanceState != null) {
            recipes = savedInstanceState.getParcelableArrayList("recipes");
            if (recipes != null) {
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(mcontext, 1);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(new MainCustomAdapter(mcontext, recipes, recyclerView));
                progressBar.setVisibility(View.INVISIBLE);

            }

        }


        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (recipes != null)
            outState.putParcelableArrayList("recipes", recipes);
        super.onSaveInstanceState(outState);
    }

    @Override
    public Loader<ArrayList<Recipe>> onCreateLoader(int id, Bundle args) {
        progressBar.setVisibility(View.VISIBLE);
        return new AsyncTaskLoader<ArrayList<Recipe>>(mcontext) {
            String URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

            @Override
            public void deliverResult(ArrayList<Recipe> data) {
                recipes = data;
                super.deliverResult(data);
            }

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                progressBar.setVisibility(View.VISIBLE);
                if (recipes != null) {
                    deliverResult(recipes);
                } else {
                    if (isConnectedToInternet())
                        forceLoad();
                    else {
                        Toast.makeText(mcontext, "No Internet connection.", Toast.LENGTH_LONG).show();

                    }
                }
            }

            public ArrayList<Recipe> loadInBackground() {

                ArrayList<Recipe> arr = new ArrayList<Recipe>();

                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(URL)
                            .build();
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        String s = response.body().string();
                        JSONArray jsonArray = new JSONArray(s);
                        for (int i = 0; i < jsonArray.length(); i++) {

                            Recipe recipe = new Recipe();
                            recipe.setId(jsonArray.getJSONObject(i).getInt("id"));
                            recipe.setName(jsonArray.getJSONObject(i).getString("name"));
                            recipe.setImage(jsonArray.getJSONObject(i).getString("image"));
                            recipe.setServings(jsonArray.getJSONObject(i).getInt("servings"));


                            JSONArray stepsArr = jsonArray.getJSONObject(i).getJSONArray("steps");
                            List<Step> steps = null;
                            steps = new ArrayList<Step>();
                            for (int j = 0; j < stepsArr.length(); j++) {
                                Step mstep = new Step();

                                mstep.setDescription(stepsArr.getJSONObject(j).getString("description"));
                                mstep.setShortDescription(stepsArr.getJSONObject(j).getString("shortDescription"));
                                mstep.setThumbnailURL(stepsArr.getJSONObject(j).getString("thumbnailURL"));
                                mstep.setVideoURL(stepsArr.getJSONObject(j).getString("videoURL"));
                                mstep.setId(stepsArr.getJSONObject(j).getInt("id"));
                                steps.add(mstep);
                            }
///////////////////////////////////////
                            JSONArray ingredientsArr = jsonArray.getJSONObject(i).getJSONArray("ingredients");
                            List<Ingredient> ingredients = null;
                            ingredients = new ArrayList<Ingredient>();
                            for (int j = 0; j < ingredientsArr.length(); j++) {
                                Ingredient mIngredient = new Ingredient();

                                mIngredient.setIngredient(ingredientsArr.getJSONObject(j).getString("ingredient"));
                                mIngredient.setQuantity(ingredientsArr.getJSONObject(j).getInt("quantity"));
                                mIngredient.setMeasure(ingredientsArr.getJSONObject(j).getString("measure"));

                                ingredients.add(mIngredient);
                            }
                            recipe.setSteps(steps);
                            recipe.setIngredients(ingredients);
                            arr.add(recipe);

                        }


                    } else {

                        return null;
                    }
                } catch (Exception ex) {

                    return null;
                }


                return arr;
            }


        };

    }

    public boolean isConnectedToInternet() {
        boolean isConnected;
        try {

            ConnectivityManager cm =
                    (ConnectivityManager) mcontext.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            isConnected = activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();


        } catch (Exception e) {
            return false;
        }
        return isConnected;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Recipe>> loader, ArrayList<Recipe> data) {

        if (data != null) {
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(mcontext, 1);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(new MainCustomAdapter(mcontext, data, recyclerView));
            progressBar.setVisibility(View.INVISIBLE);
            if (MainActivity.twoPaneFlag) {

                MainActivity.loadDetailedFragment(null, data.get(0));
            }


        } else {
            Toast.makeText(mcontext, "Error while fetching data.", Toast.LENGTH_LONG).show();


        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Recipe>> loader) {

        loaderManager.restartLoader(12, null, this).forceLoad();
    }
}
