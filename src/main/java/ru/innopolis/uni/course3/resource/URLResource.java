package ru.innopolis.uni.course3.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *  Класс-ресурс, соответствующий гиперссылкам
 */
public class URLResource extends Resource{

    private static Logger logger = LoggerFactory.getLogger(URLResource.class);

    public URLResource() {
    }

    public URLResource(String resourceLine) {
        super(resourceLine);
    }

    @Override
    public InputStream getInputStream() {
        InputStream is = null;
        try {
            URL url = new URL(resourceLine);
            is = url.openStream();
        } catch (MalformedURLException e) {
            logger.warn("MalformedURLException " + getResourceLine());
        } catch (IOException e) {
            logger.warn("It's impossible to get input stream for " + getResourceLine());
        }
        return is;
    }
}
