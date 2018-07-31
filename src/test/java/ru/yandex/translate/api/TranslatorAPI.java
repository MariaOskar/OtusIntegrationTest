package ru.yandex.translate.api;

import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;


import static io.restassured.RestAssured.given;

public class TranslatorAPI extends API {

    public static final String API_KEY = "trnsl.1.1.20180724T172236Z.a8a8de313ae6605a.2085a32312bd7d837cc0b79cb48343a5dc9b3eae";
    public static final String API_URL = "https://translate.yandex.net/api/v1.5/tr.json/translate";

    public static JsonPath getTranslatorJson(String word, String wordlang, String neededlang){
        String key = word+":"+neededlang+":"+wordlang;

        RequestSpecification request = given()
                    .queryParam("key", API_KEY)
                    .queryParam("lang", wordlang+"-"+neededlang)
                    .queryParam("text", word);

        return getJsonPathCache(key,API_URL, request);
    }

    public static String getLangPair(String word, String wordlang, String neededlang){
        return (String)getTranslatorJson(word, wordlang, neededlang).get("lang");
    }

    public static String getTranslatedText(String word, String wordlang, String neededlang){
        return  getTranslatorJson(word, wordlang, neededlang).get("text[0]");
    }
}
