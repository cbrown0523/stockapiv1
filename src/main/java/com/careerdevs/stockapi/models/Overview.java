package com.careerdevs.stockapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
public class Overview {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false , unique = true)
    private long Id;

     @JsonProperty("Symbol")
     @Column(name = "symbol", nullable = false)
     private String symbol;

    @JsonProperty("AssetType")
    @Column(name = "asset_type", nullable = false)
    private String assetType;

    @JsonProperty("Name")
    @Column(name = "name", nullable = false , unique = true)
    private String name;

    @JsonProperty ("Exchange")
    @Column(name = "exchange", nullable = false)
    private String exchange;

    @JsonProperty ("Address")
    private String address;

    @JsonProperty ("Sector")
    @Column(name = "sector", nullable = false)
    private String sector;

    public Overview(){

    }

    public long getId() {
        return Id;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getAssetType() {
        return assetType;
    }

    public String getName() {
        return name;
    }

    public String getExchange() {
        return exchange;
    }

    public String getAddress() {
        return address;
    }

    public String getSector() {
        return sector;
    }

    @Override
    public String toString() {
        return "{" +
                "\"Id\":" + Id +
                ", \"symbol\":\"" + symbol + '"' +
                ", \"assetType\":\"" + assetType + '"' +
                ", \"name\":\"" + name + '"' +
                ", \"exchange\":\"" + exchange + '"' +
                ", \"address\":\"" + address + '"' +
                ", \"sector\":\"" + sector + '"' +
                '}';
    }

//    public String toString() {
//        return "Overview{" +
//                "Id=" + Id +
//                ", symbol='" + symbol + '\'' +
//                ", assetType='" + assetType + '\'' +
//                ", name='" + name + '\'' +
//                ", exchange='" + exchange + '\'' +
//                ", address='" + address + '\'' +
//                ", sector='" + sector + '\'' +
//                '}';
//    }
}
