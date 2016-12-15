package ru.innopolis.uni.course3.resource;

import java.io.InputStream;

/**
 *
 */
abstract public class Resource {

    protected String resourceLine;

    public String getResourceLine() {
        return resourceLine;
    }

    public abstract InputStream getInputStream();
}
