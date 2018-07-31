package ru.yandex.translate.api;

import io.restassured.response.Response;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;


public class API {

    static Map<String,JsonPath> cache = new HashMap<>();

    public static final String URL = "https://translate.yandex.ru";


    public static JsonPath getJsonPath(Response response){
        return response.then().extract().body().jsonPath();
    }

    public static JsonPath getJsonPathCache(String key, String url, RequestSpecification request){
        if(cache.containsKey(key)){
            return cache.get(key);
        } else {
            cache.put(key, getJsonPath(request.get(url)));
            return cache.get(key);
        }
    }
}
