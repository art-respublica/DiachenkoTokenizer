package ru.innopolis.uni.course3;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 *
 */
public class Tokenizer {

    private String excludedCharactersRegExp;
    private boolean isCleanedUp;

    public Tokenizer() {
        this.excludedCharactersRegExp = null;
        this.isCleanedUp = false;
    }

    public Tokenizer(String excludedCharacters) {
        this.excludedCharactersRegExp = "[" + excludedCharacters + "]*";
        this.isCleanedUp = !"".equals(this.excludedCharactersRegExp);
    }

    public List<String> tokenizeText(String text) {

        List<String> tokensList = new ArrayList<>();

        // split text into tokens, standard delimiters is: white space, \t, \n, \r
        StringTokenizer tokenizer = new StringTokenizer(text);

        while(tokenizer.hasMoreElements()) {
            String word = tokenizer.nextToken();
            if(isCleanedUp) {
                word = word.replaceAll(excludedCharactersRegExp, "");
            }
            if("".equals(word)) continue;
            tokensList.add(word);
        }
        return tokensList;
   }
}
