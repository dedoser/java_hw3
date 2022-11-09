package ru.fignigno.filechecker.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Directory implements File {
    private final String name;
    private final List<File> files;

    public Directory(String name) {
        this.name = name;
        files = new ArrayList<>();
    }

    @Override
    public void printName(int tab) {
        File.super.printName(tab);
        files.forEach(file -> file.printName(tab + 1));
    }

    @Override
    public String getName() {
        return name;
    }

    public void addFiles(Collection<? extends File> newFiles) {
        files.addAll(newFiles);
    }
}
