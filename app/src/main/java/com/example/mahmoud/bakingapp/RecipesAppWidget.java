package com.example.mahmoud.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
        Log.d("myTag", "updateAppWidget");

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipes_app_widget);
        views.setTextViewText(R.id.appwidget_text, mRecipe.getName());
        String ingr = "";
        for (Ingredient i : mRecipe.getIngredients()) {

            ingr += i.getQuantity() + " " + i.getMeasure() + " of " + i.getIngredient() + " + ";
        }

        views.setTextViewText(R.id.appwidget_detail, ingr);


        Intent intent = new Intent(context, RecipesAppWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.appwidget_btn, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, views);

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (isConnectedToInternet(context)) {

            Intent intent_auto = new Intent(context, UpdateWidgetService.class);
            intent_auto.setAction(ACTION_UPDATEWIDGET);
            context.startService(intent_auto);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        if (isConnectedToInternet(context)) {
            Intent intent_auto = new Intent(context, UpdateWidgetService.class);
            intent_auto.setAction(ACTION_UPDATEWIDGET);
            context.startService(intent_auto);
            // sendBroadcast(intent);


            Log.d("myTag", "onUpdate");
        }

    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {

    }


    public static void updateMyAppWidget(Context context, AppWidgetManager appWidgetManager, Recipe mRecipe, int[] widgetIDS) {

        for (int id : widgetIDS) {
            Log.d("myTag", "updateMyAppWidget");

            updateAppWidget(context, appWidgetManager, mRecipe, id);
        }
    }

    public boolean isConnectedToInternet(Context context) {
        boolean isConnected;
        try {

            ConnectivityManager cm =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            isConnected = activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();


        } catch (Exception e) {
            return false;
        }
        return isConnected;
    }

}

