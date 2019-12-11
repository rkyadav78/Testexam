package com.image.exercise.rest;

import com.image.exercise.model.ImageList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestApi {
    @GET("s/2iodh4vg0eortkl/facts.json")
    Call<ImageList> fetchFeed(@Query("page") int page);// page for pagination purpose use
}
