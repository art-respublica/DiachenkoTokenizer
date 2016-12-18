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
        TokenizerTest.excludedCharacters = DTData.EXCLUDED_CHARACTERS;
    }

    @Before
    public void initialization() {
        this.tokenizer = new Tokenizer(excludedCharacters);
    }

    @Test
    public void testTokenizeText()  {
        String resourceText = "Компанией «Сбербанк-Технологии» проводится конкурсный отбор";
        List<String> result = tokenizer.tokenizeText(resourceText);
        Object[] example = {"Компанией", "СбербанкТехнологии", "проводится", "конкурсный", "отбор"};
        logger.info("Tokenizer test have passed. Size of result list is  " + result.size() + " ; expected 5");
        assertArrayEquals(result.toArray(), example);
    }

}
