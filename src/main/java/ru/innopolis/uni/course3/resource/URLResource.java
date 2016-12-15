package ru.innopolis.uni.course3.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 */
public class URLResource extends Resource{

    private static Logger logger = LoggerFactory.getLogger(URLResource.class);

    public URLResource(String resourceLine) {
        this.resourceLine = resourceLine;
    }

    @Override
    public InputStream getInputStream() {
        InputStream is = null;
        URL url = null;
        try {
            url = new URL(resourceLine);
            is = url.openStream();
        } catch (MalformedURLException e) {
            logger.warn("MalformedURLException " + resourceLine);
        } catch (IOException e) {
            logger.warn("It's impossible to get input stream for " + resourceLine);
        }
        return is;
    }
}