package ru.innopolis.uni.course3.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 *
 */
public class FileResource extends Resource {

    private static Logger logger = LoggerFactory.getLogger(FileResource.class);

    public FileResource(String resourceLine) {
        super(resourceLine);
    }

    @Override
    public InputStream getInputStream() {
        InputStream is = null;
        try {
            // учесть кодировку текста
            is = new FileInputStream(resourceLine);
        } catch (FileNotFoundException e) {
            logger.warn("File is not found at the address " + resourceLine);
        }
        return is;
    }
}
