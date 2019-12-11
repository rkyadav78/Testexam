package com.image.exercise.activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.image.exercise.AppController;
import com.image.exercise.R;
import com.image.exercise.adapters.PaginationListAdapter;
import com.image.exercise.databinding.FeedActivityBinding;
import com.image.exercise.viewmodel.FeedViewModel;


public class MainActivity extends AppCompatActivity {

    private PaginationListAdapter adapter;
    private FeedViewModel feedViewModel;
    private FeedActivityBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        feedViewModel = new FeedViewModel(AppController.create(this));
        
        binding.listFeed.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new PaginationListAdapter(getApplicationContext());
        
        feedViewModel.getArticleLiveData().observe(this, pagedList -> {
            adapter.submitList(pagedList);
        });
        
        feedViewModel.getNetworkState().observe(this, networkState -> {
            adapter.setNetworkState(networkState);
        });

        binding.listFeed.setAdapter(adapter);
    }
}
