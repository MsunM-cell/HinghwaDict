package com.example.hinghwadict;

import com.google.gson.annotations.SerializedName;

public class ArticleResponse {
    @SerializedName("article")
    Article article;

    public static class Article {
        @SerializedName("title")
        String title;
        @SerializedName("publish_time")
        String publish_time;
        @SerializedName("views")
        int views;
        @SerializedName("description")
        String description;
        @SerializedName("content")
        String content;

        @Override
        public String toString() {
            return "Article{" +
                    "title='" + title + '\'' +
                    ", publish_time='" + publish_time + '\'' +
                    ", views=" + views +
                    ", description='" + description + '\'' +
                    ", content='" + content + '\'' +
                    '}';
        }
    }
}
