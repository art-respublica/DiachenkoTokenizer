package ru.innopolis.uni.course3;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.innopolis.uni.course3.exception.InvalidResourceException;
import ru.innopolis.uni.course3.resource.FileResource;
import ru.innopolis.uni.course3.resource.Resource;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

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

    @Test(expected = InvalidResourceException.class)
    public void testGettingResourceText() throws InvalidResourceException  {

        String invalidResourceText = "позволить себе системы с HD или FullHD-картинкой и не экономить копейки";

        File tempFile = null;
        BufferedWriter bufferedWriter = null;
        try {
            tempFile = File.createTempFile("DiachenkoTokenizer", ".temp");
            bufferedWriter = new BufferedWriter(new FileWriter(tempFile));
            bufferedWriter.write(invalidResourceText);
            bufferedWriter.close();
            parser.getResourceText(new FileResource(tempFile.getAbsolutePath()), new Validator(DTData.VALID_CHARACTERS));
        } catch (IOException e) {
            logger.warn("Test of parser.getResourceText() haven't passed. IOException have thrown. ");
        } finally {
            if (tempFile != null) {
                tempFile.deleteOnExit();
            }
        }
    }

    @Test
    public void testGettingFileResourceByAddressLine() {

        String resourceText = "Тест, незначимый для тестирования";

        File tempFile = null;
        BufferedWriter bufferedWriter = null;
        try {
            tempFile = File.createTempFile("DiachenkoTokenizer", ".temp");
            bufferedWriter = new BufferedWriter(new FileWriter(tempFile));
            bufferedWriter.write(resourceText);
            bufferedWriter.close();
            Resource resource = parser.getResourceByAddressLine(tempFile.getAbsolutePath());
            boolean result = resource instanceof FileResource;
            logger.info("Test of parser.getResourceByAddressLine() have passed");
            assertTrue("Test of parser.getResourceText() haven't passed", result);
        } catch (InvalidResourceException exception) {
            logger.warn("Test of parser.getResourceText() haven't passed. InvalidResourceException have thrown. ");
        } catch (IOException exception) {
            logger.warn("Test of parser.getResourceText() haven't passed. IOException have thrown. ");
        } finally {
            if (tempFile != null) {
                tempFile.deleteOnExit();
            }
        }

    }

}
