package com.example.bipolarfactoryassisgnment.ui.main;

import android.util.Log;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.bipolarfactoryassisgnment.ApiInterface;
import com.example.bipolarfactoryassisgnment.ListItem;

import org.json.JSONArray;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class PageViewModel extends ViewModel {

    private MutableLiveData<String> stringresponse = new MutableLiveData<>();
    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ApiInterface.URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build();

    private ApiInterface api = retrofit.create(ApiInterface.class);

    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    //we will call this method to get the data
    public LiveData<String> getString() {
        stringresponse = new MutableLiveData<String>();
        Log.d("photos", mIndex.getValue()+"");

        if (mIndex.getValue()==1) {
            //we will load it asynchronously from server in this method
            loadPetsString(1);
        }else {
            loadNatureString(1);
        }
//        finally we will return the list
        return stringresponse;
    }

    //This method is using Retrofit to get the JSON data from URL
    void loadPetsString(int page) {
        Call<String> call= api.getPets(page);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.d("onSuccess", response.body());
                        stringresponse.setValue(response.body());
                    } else {
                        Log.d("onEmptyResponse", "Returned empty response");
                    }
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("onFailure",t.getMessage());
            }
        });
    }

    void loadNatureString(int page) {
        Call<String> call= api.getNature(page);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.d("onSuccess", response.body());
                        stringresponse.setValue(response.body());
                    } else {
                        Log.d("onEmptyResponse", "Returned empty response");
                    }
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("onFailure",t.getMessage());
            }
        });
    }
}