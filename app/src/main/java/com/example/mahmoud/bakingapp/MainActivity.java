package com.example.mahmoud.bakingapp;

import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.mahmoud.bakingapp.models.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    static FragmentManager fragmentManager;
    public static boolean twoPaneFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ButterKnife.bind(this);


        if (savedInstanceState != null) {
            backCounter = savedInstanceState.getInt("counter");

        }

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        fragmentManager = getSupportFragmentManager();
        if (backCounter == 0 || backCounter == 2) {
            installMain();
        } else {

            FragmentTransaction ft = fragmentManager.beginTransaction();
            DetailFragment fragment = new DetailFragment();
            fragment.getTag();
            ft.replace(R.id.mainview, fragment);
            ft.commit();
        }
        if (findViewById(R.id.detailview) != null) {
            twoPaneFlag = true;

        }

    }

    static MainFragment mfragment;


    public static void installMain() {

        mfragment = new MainFragment();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.mainview, new MainFragment());
        ft.commit();


    }

    public static void loadDetailedFragment(View view, Recipe recipe) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        DetailFragment fragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("recipe", recipe);
        fragment.setArguments(bundle);
        if (twoPaneFlag) {
            ft.replace(R.id.detailview, fragment);

        } else {
            ft.replace(R.id.mainview, fragment);
        }
        if (view != null)
            ft.addSharedElement(view, "element").commit();
        else
            ft.commit();
    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putInt("counter", backCounter);
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
