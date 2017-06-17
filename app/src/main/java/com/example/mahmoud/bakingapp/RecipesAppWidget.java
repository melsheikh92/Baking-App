package com.example.mahmoud.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.mahmoud.bakingapp.models.Ingredient;
import com.example.mahmoud.bakingapp.models.Recipe;
import com.example.mahmoud.bakingapp.models.Step;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Implementation of App Widget functionality.
 */
public class RecipesAppWidget extends AppWidgetProvider {


    private static final String ACTION_UPDATEWIDGET = "com.example.mahmoud.bakingapp.ACTION_UPDATEWIDGET";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, Recipe mRecipe,
                                int appWidgetId) {
        Log.d("myTag","updateAppWidget");

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipes_app_widget);
        views.setTextViewText(R.id.appwidget_text, mRecipe.getName());
        String ingr = "";
        for (Ingredient i : mRecipe.getIngredients()) {

            ingr += i.getQuantity() + " " + i.getMeasure() + " of " + i.getIngredient() + " + ";
        }

        views.setTextViewText(R.id.appwidget_detail, ingr);
    ;
        appWidgetManager.updateAppWidget(appWidgetId, views);

    }




    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Intent intent = new Intent(context, UpdateWidgetService.class);
        intent.setAction(ACTION_UPDATEWIDGET);
        context.startService(intent);
        Log.d("myTag","onUpdate");


    }
    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {

    }


    public static void updateMyAppWidget(Context context, AppWidgetManager appWidgetManager, Recipe mRecipe, int[] widgetIDS) {

        for (int id : widgetIDS) {
            Log.d("myTag","updateMyAppWidget");

            updateAppWidget(context, appWidgetManager, mRecipe, id);
        }
    }
}

