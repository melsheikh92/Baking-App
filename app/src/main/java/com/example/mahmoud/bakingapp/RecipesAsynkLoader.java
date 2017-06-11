package com.example.mahmoud.bakingapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.example.mahmoud.bakingapp.models.Ingredient;
import com.example.mahmoud.bakingapp.models.Recipe;
import com.example.mahmoud.bakingapp.models.Step;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Mahmoud on 31/05/2017.
 */

public class RecipesAsynkLoader extends AsyncTaskLoader<ArrayList<Recipe>> {

    String URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";


    public RecipesAsynkLoader(Context context) {
        super(context);


    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
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
}
