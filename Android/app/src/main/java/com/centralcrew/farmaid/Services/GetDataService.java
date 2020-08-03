package com.centralcrew.farmaid.Services;

import com.centralcrew.farmaid.APIObject;
import com.centralcrew.farmaid.Data.APIResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface GetDataService {

    @POST("/up/wheat/")
    Call<APIResponse> getUpWheat(@Body APIObject object);

    @POST("/up/rice/")
    Call<APIResponse> getUpRice(@Body APIObject object);

    @POST("/up/sugarcane/")
    Call<APIResponse> getUpSugarcane(@Body APIObject object);

    @POST("/mh/arhar/")
    Call<APIResponse> getMhArhar(@Body APIObject object);

    @POST("/mh/cotton/")
    Call<APIResponse> getMhCotton(@Body APIObject object);

    @POST("/mh/rice/")
    Call<APIResponse> getMhRice(@Body APIObject object);

    @POST("/mh/soyabean/")
    Call<APIResponse> getMhSoyabean(@Body APIObject object);

    @POST("/hr/wheat/")
    Call<APIResponse> getHrWheat(@Body APIObject object);

    @POST("/hr/rice/")
    Call<APIResponse> getHrRice(@Body APIObject object);

    @POST("/br/maize/")
    Call<APIResponse> getBrMaize(@Body APIObject object);

    @POST("/br/rice/")
    Call<APIResponse> getBrRice(@Body APIObject object);

    @POST("/br/wheat/")
    Call<APIResponse> getBrWheat(@Body APIObject object);

    @POST("/pb/rice/")
    Call<APIResponse> getPbRice(@Body APIObject object);

    @POST("/pb/wheat/")
    Call<APIResponse> getPbWheat(@Body APIObject object);

    @POST("/pb/maize/")
    Call<APIResponse> getPbMaize(@Body APIObject object);
}
