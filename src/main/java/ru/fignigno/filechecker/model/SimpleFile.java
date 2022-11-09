package ru.fignigno.filechecker.model;

public class SimpleFile implements File {
    private final String name;

    public SimpleFile(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
