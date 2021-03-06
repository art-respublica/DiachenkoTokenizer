package ru.innopolis.uni.course3;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 *  Исользуется для проверки текстов на наличие некорректных символов
 */
@Component
public class Validator {

    private String validCharactersRegExp;
    private String numbersRegExp;

    public Validator() {
        this.validCharactersRegExp = "[" + DTData.VALID_CHARACTERS + "]*";
        this.numbersRegExp = "[" + DTData.NUMBERS + "]*";
    }

    public Validator(String validCharacters) {
        this.validCharactersRegExp = "[" + validCharacters + "]*";
    }

    /**
     *  Проверяет текст на соответствие заданному шаблону и отсутствие недопустимых символов.
     *  @param  text       проверяемый текст
     *  @return boolean    истина - если недопустимые символы отсутствуют,
     *                     ложь в противоположном случае
     */
    public boolean validateText(String text)  {
        Pattern pattern = Pattern.compile(validCharactersRegExp, Pattern.CASE_INSENSITIVE);
        return pattern.matcher(text).matches();
    }

    /**
     *  Проверяет текст на соответствие заданному шаблону и отсутствие недопустимых символов.
     *  @param  text       проверяемый текст
     *  @return boolean    истина - если недопустимые символы отсутствуют,
     *                     ложь в противоположном случае
     */
    public boolean validateTextForNumbers(String text) {
        Pattern pattern = Pattern.compile(numbersRegExp, Pattern.CASE_INSENSITIVE);
        return pattern.matcher(text).matches();
    }

}
