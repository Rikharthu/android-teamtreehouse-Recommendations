package com.example.uberv.recommendations;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.example.uberv.recommendations.api.Etsy;
import com.example.uberv.recommendations.model.ActiveListings;

public class MainActivity extends AppCompatActivity {

    private static final String STATE_ACTIVE_LISTINGS="StateActiveListings";

    private RecyclerView recyclerView;
    private View progressBar;
    private TextView errorView;
    private ListingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView= (RecyclerView) findViewById(R.id.recycler_view);
        progressBar=findViewById(R.id.progress_bar);
        errorView= (TextView) findViewById(R.id.error_view);

        // setup recyclerview
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        adapter=new ListingAdapter(this);
        recyclerView.setAdapter(adapter);

        if(savedInstanceState==null){
            showLoading();
            Etsy.getActiveListing(adapter);
        }else{
            if(savedInstanceState.containsKey(STATE_ACTIVE_LISTINGS)) {
                adapter.success((ActiveListings) savedInstanceState.getParcelable(STATE_ACTIVE_LISTINGS), null);
                showList();
            }else{
                showLoading();
                Etsy.getActiveListing(adapter);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ActiveListings activeListings=adapter.getActiveListings();
        if(activeListings!=null){
            outState.putParcelable(STATE_ACTIVE_LISTINGS,activeListings);
        }
    }

    public void showLoading(){
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
    }

    public void showList(){
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);
    }

    public void showError(String error){
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        errorView.setText(error);
        errorView.setVisibility(View.VISIBLE);
    }

}
