package com.centralcrew.farmaid.Data;

import com.google.gson.annotations.SerializedName;

public class APIResponse {

    @SerializedName("results")
    private String results;

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }
}
