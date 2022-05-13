package com.careerdevs.stockapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/overview")
public class RootController {
    @Autowired
    private Environment env;
//    @GetMapping("/overview/{symbol}")
//    public ResponseEntity<?> getStock(RestTemplate restTemplate,
//                                   @PathVariable("symbol") String symbol){
//        try{
//            String apikey = env.getProperty("api_key");
//            String url = "https://www.alphavantage.co/query?=function=Overview&symbol=" + symbol + "&apikey=" + apikey;
//            //ResponseEntity response = restTemplate.getForObject(url, HttpMethod.GET, StockModel.class);
//        }
//    }

    @GetMapping("/")
    public ResponseEntity<String> rootRoute(){
        return ResponseEntity.ok("Root Route");
    }
}
