package com.example.hinghwadict;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HotArticlesResponse {
    @SerializedName("hot_articles")
    List<HotArticles> hotArticles;

    public static class HotArticles {
        @SerializedName("article")
        Article article;

        @Override
        public String toString() {
            return "HotArticles{" +
                    "article=" + article +
                    '}';
        }
    }

    public static class Article {
        @SerializedName("id")
        int id;
        @SerializedName("cover")
        String cover;

        @Override
        public String toString() {
            return "Article{" +
                    "id=" + id +
                    ", cover='" + cover + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "HotArticlesResponse{" +
                "hotArticles=" + hotArticles +
                '}';
    }
}
