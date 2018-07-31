package ru.yandex.translate.tests;

import com.codeborne.selenide.Configuration;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.translate.pages.TranslatorPage;
import ru.yandex.translate.api.DictionaryAPI;
import ru.yandex.translate.api.TranslatorAPI;


public class IntegrationTests {

    public static final String WORD = "лес";
    public static final String WORD_LANG = "ru";
    public static final String NEEDED_LANG = "en";
    public static TranslatorPage page;

    @BeforeEach
    public void beforeTests(){
        Configuration.browser = "chrome";
        WebDriverManager.chromedriver().setup();
        page = TranslatorPage.enter(WORD,WORD_LANG,NEEDED_LANG);
    }

    @Test
    public void langPairTest(){
        page
            .checkLanPairs(TranslatorAPI.getLangPair(WORD,WORD_LANG,NEEDED_LANG))
            .checkNeededLanguage(NEEDED_LANG)
            .checkWordLanguage(WORD_LANG);
    }

    @Test
    public void textForTranslationTest(){
        page
                .checkTextForTranslation(DictionaryAPI.lookupTextForTranslation(WORD,WORD_LANG,NEEDED_LANG));

    }

    @Test
    public void translatedTextTest(){
        page
            .checkTranslation(TranslatorAPI.getTranslatedText(WORD,WORD_LANG,NEEDED_LANG));
    }

    @Test
    public void meaningsTest(){
        page
            .checkAllMeanings(DictionaryAPI.lookupMeanings(WORD,WORD_LANG,NEEDED_LANG));
    }

    @Test
    public void examplesTest(){
        page
            .checkAllExamples(DictionaryAPI.lookupExamples(WORD,WORD_LANG,NEEDED_LANG));
    }

    @Test
    public void sentencesTest() {
        page
            .checkSentences(
                    DictionaryAPI.lookupSourceSentences(WORD,WORD_LANG,NEEDED_LANG),
                    DictionaryAPI.lookupTargetSentences(WORD,WORD_LANG,NEEDED_LANG)
            );
    }

    @Test
    public void synonymsTest(){
        page
            .checkSynonyms(DictionaryAPI.lookupSynonyms(WORD,WORD_LANG));

    }


}
