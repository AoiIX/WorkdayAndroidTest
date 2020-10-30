package com.ews.workdaytest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;

import com.ews.workdaytest.model.TrendingDataModel;
import com.ews.workdaytest.model.TrendingModel;
import com.ews.workdaytest.network.Methods;
import com.ews.workdaytest.network.ServiceGenerator;
import com.ews.workdaytest.network.TrendingListAdapter;
import com.ews.workdaytest.utils.Constants;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    private static final String TAG = "MAIN_ACTIVITY";
    private TrendingModel trendingModel;
    private TrendingListAdapter adapter;
    RecyclerView recyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    Methods service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        service = ServiceGenerator.createService(Methods.class);
        trendingModel = new TrendingModel();
        //setAdapterData(trendingModel.data);

        mSwipeRefreshLayout = findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mSwipeRefreshLayout.post(() -> {
            mSwipeRefreshLayout.setRefreshing(true);
            getImageList();
        });
    }

    private void getImageList() {
        mSwipeRefreshLayout.setRefreshing(true);
        service.getImageList(Constants.NETWORK.API_KEY).enqueue(new Callback<TrendingModel>() {
            @Override
            public void onResponse(Call<TrendingModel> call, Response<TrendingModel> response) {
                if (null != response.body()) {
                    trendingModel = response.body();

                    setAdapterData(trendingModel.data);
                }

                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<TrendingModel> call, Throwable t) {
                Log.e(TAG, "ERROR = " + t.getLocalizedMessage());
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void setAdapterData(ArrayList<TrendingDataModel> imageList) {
        adapter = new TrendingListAdapter(getApplicationContext(), imageList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putSerializable("data", trendingModel);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        trendingModel = (TrendingModel) savedInstanceState.getSerializable("data");
    }

    @Override
    public void onRefresh() {
        getImageList();
    }
}
