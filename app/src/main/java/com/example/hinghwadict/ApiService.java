package com.example.hinghwadict;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("website/word_of_the_day")
    Call<WordDayResponse> getHomeWord();

    @GET("words")
    Call<SearchWordResponse> getSearchWord(@Query("search") String search);

    @GET("characters/words/v2")
    Call<SearchPinyinResponse> getSearchCharacter(@Query("search") String search);

    @GET("words/{id}")
    Call<WordDetailResponse> getWordDetail(@Path("id") String id);

    @GET("articles/{id}")
    Call<ArticleResponse> getArticle(@Path("id") String id, @Header("token") String token);

    @GET("website/hot_articles")
    Call<HotArticlesResponse> getHotArticles();
}
