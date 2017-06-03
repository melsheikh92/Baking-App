package com.example.mahmoud.bakingapp;

import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.mahmoud.bakingapp.models.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ButterKnife.bind(this);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        fragmentManager = getSupportFragmentManager();
        installMain();

    }

    public static void installMain() {

        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.mainview, new MainFragment());
        ft.commit();


    }

    public static void loadDetailedFragment(Recipe recipe) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        DetailFragment fragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("recipe", recipe);
        fragment.setArguments(bundle);
        ft.replace(R.id.mainview, fragment);
        ft.commit();
    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {

        super.onSaveInstanceState(outState, outPersistentState);
    }

   public static int backCounter = 0;

    @Override
    public void onBackPressed() {
        if (backCounter < 2) {
            backCounter++;
            installMain();
        } else {

            finish();
        }


    }
}
