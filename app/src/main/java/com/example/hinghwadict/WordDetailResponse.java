package com.example.hinghwadict;

import com.google.gson.annotations.SerializedName;

public class WordDetailResponse {
    @SerializedName("word")
    Word word;

    public static class Word {
        @SerializedName("word")
        public String word;
        @SerializedName("views")
        public int views;
        @SerializedName("standard_pinyin")
        public String standard_pinyin;
        @SerializedName("standard_ipa")
        public String standard_ipa;
        @SerializedName("definition")
        public String definition;
        @SerializedName("annotation")
        public String annotation;

        @Override
        public String toString() {
            return "Word{" +
                    "word='" + word + '\'' +
                    ", views=" + views +
                    ", standard_pinyin='" + standard_pinyin + '\'' +
                    ", standard_ipa='" + standard_ipa + '\'' +
                    ", definition='" + definition + '\'' +
                    ", annotation='" + annotation + '\'' +
                    '}';
        }
    }
}
