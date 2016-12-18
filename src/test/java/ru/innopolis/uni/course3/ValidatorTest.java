package ru.innopolis.uni.course3;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

/**
 *
 */
public class ValidatorTest {

    private static Logger logger = LoggerFactory.getLogger(ValidatorTest.class);

    private Validator validator;
    private static String validCharacters;

    @BeforeClass
    public static void beforeTests() {
        ValidatorTest.validCharacters = DTData.VALID_CHARACTERS;
    }

    @Before
    public void initialization() {
        this.validator = new Validator(validCharacters);
    }

    @Test
    public void testCyrillicLetters()  {
        boolean result = validator.validateText("То, что в магазине используются охранные камеры");
        logger.info("Test of cyrillic letters have passed. Result is " + result + " ; expected is true");
        assertTrue("Cyrillic text isn't validated", result);
    }

    @Test
    public void testCyrillicLettersAndDigitsAndPunctuationMarks()  {
        boolean result = validator.validateText("Ёкатер1нб'ргЪ");
        logger.info("Test of cyrillic letters with digits and punctuation mark have passed. Result is " + result + " ; expected is true");
        assertTrue("Text with valid characters isn't validated", result);
    }

    @Test
    public void testLatinLetters()  {
        boolean result = validator.validateText("позволить себе системы с HD или FullHD-картинкой и не экономить копейки");
        logger.info("Test of latin letters have passed. Result is " + result + "; expected is false");
        assertFalse("It's mistake that latin text is passed test", result);
    }

}
