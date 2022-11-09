package ru.fignigno.filechecker.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Directory implements File {
    private final String name;
    private Long size;
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

    @Override
    public Long getSize() {
        if (size == null) {
            size = files.stream().mapToLong(File::getSize).sum();
        }
        return size;
    }

    public void addFiles(Collection<? extends File> newFiles) {
        files.addAll(newFiles);
    }
}
