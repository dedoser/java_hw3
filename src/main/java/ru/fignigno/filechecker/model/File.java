package ru.fignigno.filechecker.model;

public interface File {
    default void printName(int tab) {
        for (int i = 0; i < tab; ++i) {
            System.out.print("\t");
        }
        int lastSlash = getName().lastIndexOf('\\');
        System.out.println(getName().substring(lastSlash + 1));
    }

    String getName();
}
