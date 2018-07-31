package ru.yandex.translate.api;

import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import ru.yandex.translate.utils.TestUtils;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class DictionaryAPI extends API {

    protected static final String DICTIONARY_URL = "https://dictionary.yandex.net";
    protected static final String LOOKUP_API_URL = DICTIONARY_URL + "/api/v1/dicservice.json/lookup";
    protected static final String DICT_API_KEY = "dict.1.1.20180718T152751Z.b148d97cf60bd06a.2a370f44d3c49000c6887d5aeb28a915485d162e";

    public static JsonPath getDictionaryTranslation(String word, String wordLang, String neededLang){
        String key = word+"-DictionaryTranslation";

        RequestSpecification request = given()
                .queryParam("key", DICT_API_KEY)
                .queryParam("lang", wordLang+ "-" +neededLang)
                .queryParam("text", word)
                .queryParam("flags", 103);

        return getJsonPathCache(key, LOOKUP_API_URL, request);
    }

    public static JsonPath getDictionarySynonyms(String word, String wordLang){
        String key = word+"-DictionarySynonyms";

        RequestSpecification request = given()
                    .queryParam("key", DICT_API_KEY)
                    .queryParam("lang", wordLang+ "-" +wordLang)
                    .queryParam("text", word);

        return getJsonPathCache(key, LOOKUP_API_URL, request);
    }

    public static String lookupTextForTranslation(String word, String wordlang, String neededLang){
       return getDictionaryTranslation(word,wordlang,neededLang).get("def[0].text");
    }

    public static List<String> lookupMeanings(String word, String wordLang, String neededLang){
        JsonPath path = getDictionaryTranslation(word, wordLang, neededLang);
        return TestUtils.mergeLists(
                path.getList("def[0].tr[0].syn.text"),
                path.getList("def[0].tr.text")
        );
    }

    public static List<String> lookupExamples(String word, String wordLang, String neededLang){
        JsonPath path = getDictionaryTranslation(word, wordLang, neededLang);
        return TestUtils.createDictionaryExamplesFromJson(
                path.getList("def[0].tr[0].ex.text"),
                path.getList("def[0].tr[0].ex.tr.text"));
    }

    public static List<String> lookupSourceSentences(String word, String wordLang, String neededLang){
        return TestUtils.formatSentences(
                getDictionaryTranslation(word, wordLang, neededLang).getList("def[0].tr[0].exl.text"));
    }

    public static List<String> lookupTargetSentences(String word, String wordLang, String neededLang){
        JsonPath path = getDictionaryTranslation(word, wordLang, neededLang);
        List<ArrayList> sentences2 = path.getList("def[0].tr[0].exl.tr.text");
        return TestUtils.formatSentences(TestUtils.getListStringFromListOfArrays(sentences2));
    }

    public static List<String> lookupSynonyms(String word, String wordLang){
        JsonPath path = getDictionarySynonyms(word,wordLang);
        return TestUtils.mergeLists(
                path.get("def[0].tr.text"),
                TestUtils.getListStringFromListOfArrays(path.get("def[0].tr.syn.text")));
    }


}
