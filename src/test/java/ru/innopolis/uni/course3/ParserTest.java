package ru.innopolis.uni.course3;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class ParserTest {

    private static Logger logger = LoggerFactory.getLogger(ParserTest.class);

    private Parser parser;

    @Before
    public void initialization() {
        this.parser = new Parser();
    }

    @Test
    public void testGettingResourceText()  {

    }
}
