package ru.yandex.translate.pages;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.translate.api.API;

import java.util.List;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.assertj.core.api.Assertions.assertThat;

public class TranslatorPage {

    private String wordLanguage = $("#textbox").attr("lang");
    private String langOfTranslation = $("#textbox2").attr("lang");
    private String langPair = wordLanguage+"-"+langOfTranslation;
    private SelenideElement textForTranslation = $(".textbox #fakeArea");
    private SelenideElement textTranslation = $("#translation");
    private ElementsCollection meanings = $$("span.dictionary-meaning");
    private ElementsCollection sentencesWithoutTranslation = $$(".dictionary-example_source");
    private ElementsCollection sentencesTranslation = $$(".dictionary-example_target");
    private ElementsCollection synonyms = $$(".synonyms-value[data-dict-type=\"syn\"]");
    private ElementsCollection dictionaryExamples = $$(".dictionary-examples li span");

    public TranslatorPage() {
        WebDriverWait wait = new WebDriverWait(getWebDriver(),15);
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("#translation .translation-chunk"),Integer.valueOf(0)));
    }

    private String getTextForTranslation(){
        return textForTranslation.getText();
    }

    private String getTextTranslation(){
        return textTranslation.getText();
    }

    private List<String> getListOfMeanings(){
        return meanings.texts();
    }

    private List<String> getListOfDictExamples(){
        return dictionaryExamples.texts();
    }

    private List<String> getListOfSynonyms(){
        return synonyms.texts();
    }

    private List<String> getListOfsentencesWithoutTranslation(){
        return sentencesWithoutTranslation.texts();
    }

    private List<String> getListOfsentencesTranslation(){
        return sentencesTranslation.texts();
    }


    public TranslatorPage checkLanPairs(String str){
        assertThat(langPair).contains(str);
        return this;
    }

    public TranslatorPage checkWordLanguage(String str){
        assertThat(wordLanguage).contains(str);
        return this;
    }

    public TranslatorPage checkNeededLanguage(String str){
        assertThat(langOfTranslation).contains(str);
        return this;
    }

    public static TranslatorPage enter(String word, String wordLang, String neededLang){
        open(API.URL + "/?lang="+wordLang+"-"+neededLang+"&text="+word);
        return page(TranslatorPage.class);
    }

    public TranslatorPage checkTranslation(String str){
        assertThat(getTextTranslation()).contains(str);
        return this;
    }

    public TranslatorPage checkAllMeanings(List<String> list){
        assertThat(getListOfMeanings()).containsAnyElementsOf(list);
        return this;
    }

    private TranslatorPage turnOnExamples(){
        $("#dictionaryContent span.toggler").click();
        return this;

    }

    public TranslatorPage checkAllExamples(List<String> list){
        turnOnExamples();
        assertThat(getListOfDictExamples()).containsAnyElementsOf(list);
        return this;
    }

    public TranslatorPage turnOnSentences(){
        $("#dictionaryTabs > div:nth-child(2)").click();
        return this;
    }


    public TranslatorPage checkSentences(List<String> source, List<String> target){
        turnOnSentences();
        assertThat(getListOfsentencesWithoutTranslation()).containsAnyElementsOf(source);
        assertThat(getListOfsentencesTranslation()).containsAnyElementsOf(target);
        return this;
    }


    public TranslatorPage checkSynonyms(List<String> list){
        assertThat(getListOfSynonyms()).containsAnyElementsOf(list);
        return this;
    }

    public TranslatorPage checkTextForTranslation(String str){
        assertThat(getTextForTranslation()).contains(str);
        return this;
    }






}
