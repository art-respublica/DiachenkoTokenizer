package ru.innopolis.uni.course3;

import java.util.regex.Pattern;

/**
 *  Исользуется для проверки текстов на наличие некорректных символов
 */
public class Validator {

    private String validCharactersRegExp;

    public Validator(String validCharacters) {
        this.validCharactersRegExp = "[" + validCharacters + "]*";
    }

    /**
     *  Проверяет текст на соответствие недопустимыхх символов. Если таки
     *  @param  text       проверяемый текст
     *  @return boolean    список токенов
     */
    public boolean validateText(String text)  {
        Pattern pattern = Pattern.compile(validCharactersRegExp, Pattern.CASE_INSENSITIVE);
        return pattern.matcher(text).matches();
    }
}
