package com.sparkdev.foodapp.mainscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.sparkdev.foodapp.R;
import com.sparkdev.foodapp.models.MenuCategory;
import com.sparkdev.foodapp.models.firebase.FirebaseAdapter;
import com.sparkdev.foodapp.models.firebase.foodMenuInterface.GetMenuCategoriesCompletionListener;
import com.sparkdev.foodapp.profileSettings.ProfileSettings;
import com.sparkdev.foodapp.shoppingcartscreen.OrderScreen.OrderScreenActivity;

import java.util.ArrayList;
import java.util.List;


public class TabLayoutActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private static FirebaseAdapter firebaseAPI;
    private List<MenuCategory> categories = new ArrayList<>();
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_draw);
        firebaseAPI = FirebaseAdapter.getInstance(this);
        firebaseAPI.getMenuCategories(new GetMenuCategoriesCompletionListener() {
            @Override
            public void onSuccess(List<MenuCategory> menuCategories) {

                for(int i = 0; i < menuCategories.size(); i++) {
                    categories.add(menuCategories.get(i));
                }

                MainscreenFragmentPagerAdapter pagerAdapter = new MainscreenFragmentPagerAdapter(getSupportFragmentManager(),
                        TabLayoutActivity.this, categories);
                viewPager = (ViewPager) findViewById(R.id.viewpager);
                viewPager.setAdapter(pagerAdapter);
                pagerAdapter.notifyDataSetChanged();

                // Give the TabLayout the ViewPager
                TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
                tabLayout.setupWithViewPager(viewPager);
            }

            @Override
            public void onFailure() {

            }
        });

        drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        drawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here
                        switch(menuItem.getItemId()){
                            case R.id.nav_settings:
                                Intent intent = new Intent(TabLayoutActivity.this, ProfileSettings.class);
                                startActivity(intent);
                                return true;
                        }

                        return true;
                    }
                });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);


       ActionBar actionbar = getSupportActionBar();
       actionbar.setDisplayHomeAsUpEnabled(true);
       actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

       //To go to shopping cart Screen
        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.fab);
        myFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TabLayoutActivity.this, OrderScreenActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

}