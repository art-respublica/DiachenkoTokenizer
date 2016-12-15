package ru.innopolis.uni.course3;

import java.util.StringTokenizer;
import java.util.regex.Pattern;

/**
 *
 */
public class Validator {

    private String correctChars;

    public Validator(String correctChars) {
        this.correctChars = correctChars;
    }

    public boolean validateText(String text)  {

        String regexp = "[" + correctChars + "]*";
        Pattern pattern = Pattern.compile(regexp, Pattern.CASE_INSENSITIVE);
        StringTokenizer tokenizer = new StringTokenizer(text);
        while(tokenizer.hasMoreElements()) {
            String word = tokenizer.nextToken();
            if (!pattern.matcher(word).matches()) {
                return false;
            }
        }
        return true;
    }
}
