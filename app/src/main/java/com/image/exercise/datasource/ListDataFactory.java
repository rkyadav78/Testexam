package com.image.exercise.datasource;


import androidx.lifecycle.MutableLiveData;

import com.image.exercise.AppController;

public class ListDataFactory extends androidx.paging.DataSource.Factory {

    private MutableLiveData<DataSource> mutableLiveData;
    private DataSource dataSource;
    private AppController appController;

    public ListDataFactory(AppController appController) {
        this.appController = appController;
        this.mutableLiveData = new MutableLiveData<DataSource>();
    }

    @Override
    public androidx.paging.DataSource create() {
        dataSource = new DataSource(appController);
        mutableLiveData.postValue(dataSource);
        return dataSource;
    }


    public MutableLiveData<DataSource> getMutableLiveData() {
        return mutableLiveData;
    }
}
