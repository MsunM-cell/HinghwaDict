package com.example.hinghwadict;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    // 获取每日一词
    @GET("website/word_of_the_day")
    Call<WordDayResponse> getHomeWord();

    // 获取符合条件的字词列表
    @GET("words")
    Call<SearchWordResponse> getSearchWord(@Query("search") String search);

    // 多汉字搜索
    @GET("characters/words/v2")
    Call<SearchPinyinResponse> getSearchCharacter(@Query("search") String search);

    // 获取字词的内容
    @GET("words/{id}")
    Call<WordDetailResponse> getWordDetail(@Path("id") String id);

    // 获取文章内容
    @GET("articles/{id}")
    Call<ArticleResponse> getArticle(@Path("id") String id, @Header("token") String token);

    // 获取热门文章列表
    @GET("website/hot_articles")
    Call<HotArticlesResponse> getHotArticles();
}
