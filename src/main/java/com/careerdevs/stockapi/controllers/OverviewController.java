package com.careerdevs.stockapi.controllers;

import com.careerdevs.stockapi.models.Overview;
import com.careerdevs.stockapi.repositories.OverviewRepository;
import com.careerdevs.stockapi.utils.ApiError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RestController
@RequestMapping("/api/overview")


public class OverviewController {
    @Autowired
    private Environment env;
    @Autowired
    private OverviewRepository overviewRepository;
    private final String BASE_URL = "https://www.alphavantage.co/query?function=OVERVIEW";

    @GetMapping("/test")
    public ResponseEntity<?> testOverview(RestTemplate restTemplate) {
        try {
            String APIKEY = env.getProperty("api_key");
            String url = BASE_URL + "&symbol=IBM&apikey=demo" ;
            Overview responseBody = restTemplate.getForObject(url, Overview.class);
            return ResponseEntity.ok(responseBody);
        }catch(HttpClientErrorException e) {
            return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());
        }catch(IllegalArgumentException e) {
            return ApiError.customApiError("Uri is not absolute. check url",500);
        }catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/{symbol}")
    public ResponseEntity<?> dynamicOverview(RestTemplate restTemplate,
                                             @PathVariable("symbol") String symbol) {
        try {
            String url = BASE_URL + "&symbol=" + symbol + "&apikey=" + env.getProperty("api_key");
            System.out.println(url);

            Overview responseBody = restTemplate.getForObject(url, Overview.class);
            if(responseBody == null){
                ApiError.throwErr(500 , "did not receive a response" );
            }else if(responseBody.getSymbol() == null){
                return ApiError.customApiError("Invalid stock symbol " + symbol, 404);
            }
            return ResponseEntity.ok(responseBody);
        } catch(HttpClientErrorException e) {
            return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());
        }catch(DataIntegrityViolationException e) {
            return ApiError.customApiError("can not upload duplicate Stock data", 400);
        }catch(IllegalArgumentException e)
        {
            return ApiError.customApiError("uri is not absolute. Check url", 500);
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
                return ApiError.customApiError("did not receive a response" , 500);
            }else if(responseBody.getSymbol() == null){
                return ApiError.customApiError("no data retrieved", 404);//HttpStatus.NOT_FOUND );
            }

            Overview savedOverview = overviewRepository.save(responseBody);

            return ResponseEntity.ok(savedOverview);
        }catch(HttpClientErrorException e) {
            return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());
        }catch(DataIntegrityViolationException e) {
            return ApiError.customApiError("can not upload duplicate Stock data", 400);
        }catch(IllegalArgumentException e) {
            return ApiError.customApiError("Uri is not absolute. check url", 500);
        }catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
    @PostMapping("/{symbol}")
    public ResponseEntity<?> uploadOverview(RestTemplate restTemplate,
                                             @PathVariable("symbol") String symbol) {
        try {
            String url = BASE_URL + "&symbol=" + symbol + "&apikey=" + env.getProperty("api_key");
            System.out.println(url);
            Overview responseBody = restTemplate.getForObject(url, Overview.class);
            if(responseBody == null){
                return ApiError.customApiError("did not receive a response" , 500);//HttpStatus.INTERNAL_SERVER_ERROR);
            }else if(responseBody.getSymbol() == null){
                return ApiError.customApiError("Invalid stock symbol " + symbol, 404 );
            }
            Overview savedOverview = overviewRepository.save(responseBody);

            return ResponseEntity.ok(savedOverview);
        } catch(HttpClientErrorException e) {
            return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());
        }catch(DataIntegrityViolationException e) {
            return ApiError.customApiError("can not upload duplicate Stock data", 400);
        }catch(IllegalArgumentException e) {
            return ApiError.customApiError("uri is not absolute. Check url", 500);//HttpStatus.INTERNAL_SERVER_ERROR
        }catch (Exception e) {
            return ApiError.genericApiError(e);
        }
    }
            @GetMapping("/all")
            private ResponseEntity<?>getAlOverviews(){
                try{
                    Iterable<Overview>allOverviews = overviewRepository.findAll();
                    return ResponseEntity.ok(allOverviews);

                }catch(HttpClientErrorException e) {
                    return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());
                }catch(Exception e){
                    return ApiError.genericApiError(e);
                }
    }
    @GetMapping("/id/{id}")
    private ResponseEntity<?>getOverviewById(@PathVariable String id, String userId) {
        try {
            //datatype that may or may not contain a certain type
            Optional<Overview> foundOverview = overviewRepository.findById(Long.parseLong(id));
            if (foundOverview.isEmpty()) {
                return ApiError.customApiError(id + " did not match any overview", 404);
            }
            //Long userIdLong = Long.parseLong((id));
            return ResponseEntity.ok(foundOverview);
        } catch(HttpClientErrorException e) {
                return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());
        }catch (NumberFormatException e) {
            return ApiError.customApiError("Invalid id must be a number", 400);//HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ApiError.genericApiError(e);
        }
    }
    @DeleteMapping("/all")
    private ResponseEntity<?>deleteAllOverviews(){
        try{
            long allOverviewCount = overviewRepository.count();
            if(allOverviewCount == 0)
                return ResponseEntity.ok("No overview to delete");
            overviewRepository.deleteAll();
            return ResponseEntity.ok("Deleted Overviews" + allOverviewCount);

        }catch(Exception e){
            return ApiError.genericApiError(e);
        }
    }
    @DeleteMapping("/id/{id}")
    private ResponseEntity<?> deleteAllOverviews(@PathVariable String id){
        try {
            long overviewId = Long.parseLong(id);
            //datatype that may or may not contain a certain type
            Optional<Overview> foundOverview = overviewRepository.findById(Long.parseLong(id));
            if(foundOverview.isEmpty()){
                return ApiError.customApiError(id + "did not match any overview" ,404);
            }
            overviewRepository.deleteById(overviewId);
            return ResponseEntity.ok(foundOverview);
        } catch(HttpClientErrorException e) {
            return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());
        }catch (NumberFormatException e) {
            return ApiError.customApiError("Invalid id must be a number", 400);//HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ApiError.genericApiError(e);
        }
    }

}
