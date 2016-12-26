package ru.innopolis.uni.course3;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 *  Используется для разделения текста на токены, используя стандартные разделители
 */
public class Tokenizer {

    private String excludedCharactersRegExp;
    private boolean isCleanedUp;

    public Tokenizer() {
//        this.excludedCharactersRegExp = null;
//        this.isCleanedUp = false;
        this.excludedCharactersRegExp = "[" + DTData.EXCLUDED_CHARACTERS + "]*";
        this.isCleanedUp = true;
    }

    public Tokenizer(String excludedCharacters) {
        this.excludedCharactersRegExp = "[" + (excludedCharacters != null ? excludedCharacters : "") + "]*";
        this.isCleanedUp = excludedCharacters != null && !excludedCharacters.trim().isEmpty();
    }

    /**
     *  Разделяет текст на токены, используя стандартные разделители
     *  @param  text            раздяляемый текст
     *  @return List<String>    список токенов
     */
    public List<String> tokenizeText(String text) {

        List<String> tokensList = new ArrayList<>();

        // split text into tokens using standard delimiters, which are \f, \t, \n, \r
        StringTokenizer tokenizer = new StringTokenizer(text);

        while(tokenizer.hasMoreElements()) {
            String word = tokenizer.nextToken();
            if(isCleanedUp) {
                word = word.replaceAll(excludedCharactersRegExp, "");
            }
            if(word.isEmpty()) continue;
            tokensList.add(word);
        }
        return tokensList;
   }
}
