package com.sparkdev.foodapp.shoppingcartscreen.OrderScreen;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;

import com.sparkdev.foodapp.R;
import com.sparkdev.foodapp.models.SingleMenuItem;
import com.sparkdev.foodapp.shoppingcartscreen.confirmationscreen.ConfirmationActivity;

import java.util.ArrayList;

public class OrderScreenActivity extends AppCompatActivity {

    private LinearLayoutManager llm;
    private RecyclerView recyclerView;
    private OrderScreenAdapter adapter;
    private ArrayList<String> foodItemName = new ArrayList<>();
    private ArrayList<String> sizeOfItem = new ArrayList<>();
    private ArrayList<Integer> quantity = new ArrayList<>();
    private ArrayList<String> prices = new ArrayList<>();
    private ArrayList<Integer> images = new ArrayList<>();
    private DividerItemDecoration itemDecoration;

    private ArrayList<SingleMenuItem> mItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_screen);

        //Set action bar title
        getSupportActionBar().setTitle("Your Order");

        populateList();



        llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);


        // Get access to the RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setNestedScrollingEnabled(false);
        // Create the adapter and supply the adapter with the data (i.e from an arraylist or database)
        adapter = new OrderScreenAdapter(this, quantity, prices, images, sizeOfItem,foodItemName);
        // Connect the adapter to the RecyclerView
        recyclerView.setAdapter(adapter);
        // Define the RecyclerView's default layout manager
        recyclerView.setLayoutManager(llm);

        // Swipe gestures
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        // Add the line divider between each row
        itemDecoration = new DividerItemDecoration(recyclerView.getContext()
                , llm.getOrientation());
        recyclerView.addItemDecoration(itemDecoration);

        Button button = findViewById(R.id.reviewButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ConfirmationActivity.class);
                startActivity(intent);
            }
        });
    }

    public void populateList()
    {
        String [] foodNameArray = {"Rice and Beans", "Tiramisu", "Pizza","Cake", "Arroz con leche"};
        for(int i = 0; i < foodNameArray.length; i++)
        {
            foodItemName.add(foodNameArray[i]);
        }



        //Filling quantity ArrayList
        int [] quantityArray = {4, 2, 1, 1, 2};
        for(int i = 0; i < quantityArray.length; i++)
        {
            quantity.add(quantityArray[i]);
        }

        // Filling images ArrayList
        int [] imageArray = {R.drawable.riceandbeans, R.drawable.tiramisu, R.drawable.pizza,
                R.drawable.cake, R.drawable.arrozconleche};
        for(int i = 0; i < imageArray.length; i++)
        {
            images.add(imageArray[i]);
        }

        //Fill prices ArrayList
        String [] pricesArray = {"$4.55", "$8.65", "$2.34", "$3.87", "$3.00"};
        for(int i = 0; i < pricesArray.length; i++)
        {
            prices.add(pricesArray[i]);
        }

        String [] sizeArray = {"small", "large", "medium", "small", "medium"};
        for(int i = 0; i < pricesArray.length; i++)
        {
            sizeOfItem.add(sizeArray[i]);
        }
    }

}