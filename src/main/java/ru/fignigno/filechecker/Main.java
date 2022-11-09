package ru.fignigno.filechecker;

import ru.fignigno.filechecker.service.FileCheckerService;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Main {

    private static final int THREAD_SIZE = 8;

    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(THREAD_SIZE);;
        try (Scanner scanner = new Scanner(System.in)) {
            String startPath = scanner.nextLine();
            FileCheckerService fileCheckerService = new FileCheckerService(startPath, threadPoolExecutor);
            fileCheckerService.run();
            fileCheckerService.print();
        }
        finally {
            threadPoolExecutor.shutdown();
        }
    }
}
