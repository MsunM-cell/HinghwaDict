package com.example.hinghwadict;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WordDayResponse {
    @SerializedName("errorCode")
    public int errorCode;
    @SerializedName("errorMsg")
    public String errorMsg;
    @SerializedName("word_of_the_day")
    Word word;

    public static class Word {
        @SerializedName("id")
        public int id;
        @SerializedName("word")
        public String word;
        @SerializedName("definition")
        public String definition;

        @Override
        public String toString() {
            return "Word{" +
                    "id=" + id +
                    ", word='" + word + '\'' +
                    ", definition='" + definition + '\'' +
                    '}';
        }
    }
}
