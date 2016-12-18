package ru.innopolis.uni.course3.resource;

import java.io.InputStream;

/**
 *
 */
abstract public class Resource {

    protected String resourceLine;

    public Resource(String resourceLine) {
        this.resourceLine = resourceLine;
    }

    public String getResourceLine() {
        return resourceLine;
    }

    public void setResourceLine(String resourceLine) {
        this.resourceLine = resourceLine;
    }

    public abstract InputStream getInputStream();
}
