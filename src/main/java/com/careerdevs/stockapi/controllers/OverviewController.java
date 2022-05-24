package com.careerdevs.stockapi.controllers;

import com.careerdevs.stockapi.models.Overview;
import com.careerdevs.stockapi.repositories.OverviewRepository;
import com.careerdevs.stockapi.utils.ApiErrorHandling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/overview")

public class OverviewController {
    @Autowired
    private Environment env;
    private OverviewRepository overviewRepository;
    private final String BASE_URL = "https://www.alphavantage.co/query?=function=Overview";

    @GetMapping("/test")
    public ResponseEntity<?> testOverview(RestTemplate restTemplate) {
        try {
            String APIKEY = env.getProperty("api_key");
            String url = BASE_URL + "&symbol=IBM&apikey=demo" ;
            Overview responseBody = restTemplate.getForObject(url, Overview.class);
            return ResponseEntity.ok(responseBody);
        }catch(IllegalArgumentException e) {
            return ApiErrorHandling.customApiError("Uri is not absolute. check url", HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/{symbol}")
    public ResponseEntity<?> dynamicOverview(RestTemplate restTemplate,
                                             @PathVariable("symbol") String symbol) {
        try {
            String url = BASE_URL + "&symbol=" + symbol + "apikey=" + env.getProperty("api_key");
            System.out.println(url);
            Overview responseBody = restTemplate.getForObject(url, Overview.class);
            if(responseBody == null){
                return ApiErrorHandling.customApiError("did not receive a response" , HttpStatus.INTERNAL_SERVER_ERROR);
            }else if(responseBody.getSymbol() == null){
                return ApiErrorHandling.customApiError("Invalid stock symbol" + symbol, HttpStatus.NOT_FOUND );
            }
            return ResponseEntity.ok(responseBody);
        } catch(IllegalArgumentException e){
            return ApiErrorHandling.customApiError("uri is not absolute. Check url", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
    @PostMapping("/test")
    public ResponseEntity<?> testUpload(RestTemplate restTemplate) {
        try {
            String APIKEY = env.getProperty("api_key");
            String url = BASE_URL + "&symbol=IBM&apikey=demo" ;
            Overview responseBody = restTemplate.getForObject(url, Overview.class);
            if(responseBody == null){
                return ApiErrorHandling.customApiError("did not receive a response" , HttpStatus.INTERNAL_SERVER_ERROR);
            }else if(responseBody.getSymbol() == null){
                return ApiErrorHandling.customApiError("no data retrieved", HttpStatus.NOT_FOUND );
            }

            Overview savedOverview = overviewRepository.save(responseBody);

            return ResponseEntity.ok(savedOverview);
        }catch(IllegalArgumentException e) {
            return ApiErrorHandling.customApiError("Uri is not absolute. check url", HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
    @PostMapping("/{symbol}")
    public ResponseEntity<?> uploadOverview(RestTemplate restTemplate,
                                             @PathVariable("symbol") String symbol) {
        try {
            String url = BASE_URL + "&symbol=" + symbol + "apikey=" + env.getProperty("api_key");
            System.out.println(url);
            Overview responseBody = restTemplate.getForObject(url, Overview.class);
            if(responseBody == null){
                return ApiErrorHandling.customApiError("did not receive a response" , HttpStatus.INTERNAL_SERVER_ERROR);
            }else if(responseBody.getSymbol() == null){
                return ApiErrorHandling.customApiError("Invalid stock symbol" + symbol, HttpStatus.NOT_FOUND );
            }
            Overview savedOverview = overviewRepository.save(responseBody);

            return ResponseEntity.ok(savedOverview);
        } catch(IllegalArgumentException e){
            return ApiErrorHandling.customApiError("uri is not absolute. Check url", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
