package com.example.hinghwadict;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchPinyinResponse {
    @SerializedName("characters")
    List<character> characters;

    public static class character {
        @SerializedName("label")
        public String label;
        @SerializedName("traditional")
        public String traditional;
        @SerializedName("characters")
        public List<pronunciation> pronunciations;

        @Override
        public String toString() {
            return "character{" +
                    "label='" + label + '\'' +
                    ", traditional='" + traditional + '\'' +
                    ", pronunciations=" + pronunciations +
                    '}';
        }
    }

    public static class pronunciation {
        @SerializedName("county")
        public String county;
        @SerializedName("town")
        public String town;
        @SerializedName("characters")
        public List<word> words;

        @Override
        public String toString() {
            return "pronunciation{" +
                    "county='" + county + '\'' +
                    ", town='" + town + '\'' +
                    ", words=" + words +
                    '}';
        }
    }

    public static class word {
        @SerializedName("word")
        public int word;
        @SerializedName("pinyin")
        public String pinyin;
        @SerializedName("ipa")
        public String ipa;

        @Override
        public String toString() {
            return "word{" +
                    "word=" + word +
                    ", pinyin='" + pinyin + '\'' +
                    ", ipa='" + ipa + '\'' +
                    '}';
        }
    }
}










