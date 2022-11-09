package ru.fignigno.filechecker.model;

public class SimpleFile implements File {
    private final String name;
    private final Long size;

    public SimpleFile(String name, Long size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Long getSize() {
        return size;
    }
}
