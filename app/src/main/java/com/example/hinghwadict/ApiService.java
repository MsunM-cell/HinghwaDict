package com.example.hinghwadict;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @GET("website/word_of_the_day")
    Call<WordDayResponse> getHomeWord();
}
