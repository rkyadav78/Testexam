package com.image.exercise.datasource;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.image.exercise.AppController;
import com.image.exercise.BaseConstants;
import com.image.exercise.model.Rows;
import com.image.exercise.model.ImageList;
import com.image.exercise.utils.NetworkState;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataSource extends PageKeyedDataSource<Long, Rows> implements BaseConstants {

    private static final String TAG = DataSource.class.getSimpleName();

    private AppController appController;

    private MutableLiveData networkState;
    private MutableLiveData initialLoading;

    public DataSource(AppController appController) {
        this.appController = appController;

        networkState = new MutableLiveData();
        initialLoading = new MutableLiveData();
    }


    public MutableLiveData getNetworkState() {
        return networkState;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params,
                            @NonNull LoadInitialCallback<Long, Rows> callback) {

        initialLoading.postValue(NetworkState.LOADING);
        networkState.postValue(NetworkState.LOADING);

        appController.getRestApi().fetchFeed(1)
                .enqueue(new Callback<ImageList>() {
                    @Override
                    public void onResponse(Call<ImageList> call, Response<ImageList> response) {
                        if(response.isSuccessful()) {
                            callback.onResult(response.body().getRows(), null, 2l);
                            initialLoading.postValue(NetworkState.LOADED);
                            networkState.postValue(NetworkState.LOADED);

                        } else {
                            initialLoading.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                            networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                        }
                    }

                    @Override
                    public void onFailure(Call<ImageList> call, Throwable t) {
                        String errorMessage = t == null ? "unknown error" : t.getMessage();
                        networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
                    }
                });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params,
                           @NonNull LoadCallback<Long, Rows> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Long> params,
                          @NonNull LoadCallback<Long, Rows> callback) {

        Log.i(TAG, "Loading Rang " + params.key + " Count " + params.requestedLoadSize);

        networkState.postValue(NetworkState.LOADING);

        appController.getRestApi().fetchFeed(params.requestedLoadSize).enqueue(new Callback<ImageList>() {
            @Override
            public void onResponse(Call<ImageList> call, Response<ImageList> response) {
                if(response.isSuccessful()) {
                    //long nextKey = (params.key == response.body().getTotalResults()) ? null : params.key+1;
                    callback.onResult(response.body().getRows(), 0L);
                    networkState.postValue(NetworkState.LOADED);

                } else networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
            }

            @Override
            public void onFailure(Call<ImageList> call, Throwable t) {
                String errorMessage = t == null ? "unknown error" : t.getMessage();
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
            }
        });
    }
}
