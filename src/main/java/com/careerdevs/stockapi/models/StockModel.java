package com.careerdevs.stockapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StockModel {
    // @JsonProperty("Symbol")
    private String symbol;

    // @JsonProperty("AssetType")
    @JsonProperty("Description")
    private String description;

    @JsonProperty ("Exchange")
    private String exchange;

    @JsonProperty ("Sector")
    private String sector;

    @JsonProperty ("Address")
    private String address;

    @JsonProperty ("MarketCapitalization")
    private long marketCap;

    private String assetType;
    private String name;
    // private String description;
    // private String exchange;
    private String currency;
    private String country;

    public StockModel(){

    }

}
