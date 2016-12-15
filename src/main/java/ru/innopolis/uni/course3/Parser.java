package ru.innopolis.uni.course3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.innopolis.uni.course3.exception.InvalidResourceException;
import ru.innopolis.uni.course3.resource.FileResource;
import ru.innopolis.uni.course3.resource.Resource;
import ru.innopolis.uni.course3.resource.URLResource;

import java.io.*;

/**
 *
 */
public class Parser {

    private static Logger logger = LoggerFactory.getLogger(Parser.class);

    public String getResourceText(Resource resource) throws InvalidResourceException {

        String resourceText = "";
        String resourceLine = "";
        Validator validator = new Validator(Main.VALID_CHARACTERS);
        InputStream is      = resource.getInputStream();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(is));
            while (reader.ready()) {
                resourceLine = reader.readLine();
                if(!validator.validateText(resourceLine)) {
                    throw new InvalidResourceException();
                }
                resourceText += resourceLine + "\n";
            }
        } catch (IOException e) {
            logger.warn("Some errors during stream processing");
        } finally {
            try {
                is.close();
                reader.close();
            } catch (IOException exception) {
                logger.warn("InputStream and Reader didn't close");
            }
        }
        return resourceText;
    }

    public Resource getResourceByAddressLine(String addressLine ) throws InvalidResourceException {

        Resource resource = null;
        //protocol://host:port/resource
        if (addressLine.contains("http")) {
            resource = new URLResource(addressLine);
        } else if (addressLine.contains("ftp")) {

        } else {
            File file = new File(addressLine);
            if(!file.exists()) {
                logger.warn("File not found");
                throw new InvalidResourceException();
            }
            resource = new FileResource(addressLine);
        }
        return resource;
    }
}
