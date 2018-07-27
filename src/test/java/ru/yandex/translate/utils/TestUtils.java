package ru.yandex.translate.utils;

import java.util.ArrayList;
import java.util.List;

public class TestUtils {

    public static List<String> formatSentences(List<String> list){
        List<String> newList = new ArrayList<String>();
        for(String s: list){
            s = s.replaceAll("<|>","");
            newList.add(s);
        }
        return newList;
    }

    public static List<String> createDictionaryExamplesFromJson(List<String> first, List<ArrayList> second){
        List<String> examples = new ArrayList<String>();
        int count = 0;
        for(String s: first){
            ArrayList eng = second.get(count);
            String newStr = s+" – "+eng.get(0);
            count++;
            examples.add(newStr);
        }
        return examples;
    }


    public static List<String> getListStringFromListOfArrays(List<ArrayList> list){
        List<String> newlist = new ArrayList<String>();
        for( ArrayList<String> ar: list){
            for(String str: ar){
                newlist.add(str);
            }
        }
        return newlist;
    }

    public static List<String> mergeLists(List<String> list1, List<String> list2){
        List<String> newList = new ArrayList<String>();
        newList.addAll(list1);
        newList.addAll(list2);
        return newList;
    }

}
