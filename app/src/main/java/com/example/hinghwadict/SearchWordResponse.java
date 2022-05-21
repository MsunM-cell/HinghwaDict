package com.example.hinghwadict;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchWordResponse {
    @SerializedName("result")
    List<word> words;

    public static class word {
        @SerializedName("id")
        public int id;
        @SerializedName("word")
        public String word;
        @SerializedName("definition")
        public String definition;
        @SerializedName("standard_ipa")
        public String standard_ipa;
        @SerializedName("standard_pinyin")
        public String standard_pinyin;

        @Override
        public String toString() {
            return "word{" +
                    "id=" + id +
                    ", word='" + word + '\'' +
                    ", definition='" + definition + '\'' +
                    ", standard_ipa='" + standard_ipa + '\'' +
                    ", standard_pinyin='" + standard_pinyin + '\'' +
                    '}';
        }
    }
}
