package ru.innopolis.uni.course3;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.Assert.assertArrayEquals;

/**
 *
 */
public class TokenizerTest {

    private static Logger logger = LoggerFactory.getLogger(TokenizerTest.class);

    private Tokenizer tokenizer;
    private static String excludedCharacters;

    @BeforeClass
    public static void beforeTests() {
        TokenizerTest.excludedCharacters = Main.DIGITS + Main.PUNCTUATION_MARKS;
    }

    @Before
    public void initialization() {
        this.tokenizer = new Tokenizer(excludedCharacters);
    }

    @Test
    public void testTokenizeText()  {
        List<String> result = tokenizer.tokenizeText("Компанией «Сбербанк-Технологии» проводится конкурсный отбор");
        Object[] example = {"Компанией", "СбербанкТехнологии", "проводится", "конкурсный", "отбор"};
        logger.info("Tokenizer test have passed. Size of result list is  " + result.size() + " ; expected 5");
        assertArrayEquals(result.toArray(), example);
    }

}
