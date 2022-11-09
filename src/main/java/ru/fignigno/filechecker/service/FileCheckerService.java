package ru.fignigno.filechecker.service;

import ru.fignigno.filechecker.model.Directory;
import ru.fignigno.filechecker.model.Result;
import ru.fignigno.filechecker.model.SimpleFile;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class FileCheckerService {

    private final LinkedBlockingQueue<Directory> queue;
    private final ThreadPoolExecutor threadPoolExecutor;

    private final Directory mainDirectory;

    public FileCheckerService(String startPath, ThreadPoolExecutor threadPoolExecutor) {
        mainDirectory = new Directory(startPath);
        this.threadPoolExecutor = threadPoolExecutor;
        this.queue = new LinkedBlockingQueue<>();
        queue.add(mainDirectory);
    }

    public void run() {
        Future<Result> result = null;
        while (true) {
            if (!queue.isEmpty()) {
                result = threadPoolExecutor.submit(new FileChecker(queue.poll()));
            } else {
                if (result.isDone() && queue.isEmpty() && threadPoolExecutor.getActiveCount() == 0) {
                    break;
                }
            }
        }
    }

    public void print() {
        mainDirectory.printName(0);
        threadPoolExecutor.shutdown();
    }

    class FileChecker implements Callable<Result> {

        private final Directory directory;

        public FileChecker(Directory directory) {
            this.directory = directory;
        }

        @Override
        public Result call() {
            File[] fileArray = new File(directory.getName()).listFiles();
            if (fileArray == null) {
                System.out.println("Cannot read directory " + directory.getName());
                return Result.STOP;
            }
            List<File> files = Arrays.stream(fileArray).toList();
            if (files.isEmpty()) {
                return Result.STOP;
            }
            List<Directory> dirs = files.stream().filter(File::isDirectory).map(dir -> new Directory(dir.getPath())).toList();
            List<SimpleFile> onlyFiles = files.stream().filter(File::isFile).map(file -> new SimpleFile(file.getName(), file.length())).toList();

            directory.addFiles(onlyFiles);
            directory.addFiles(dirs);
            queue.addAll(dirs);
            return Result.RUN;
        }
    }
}
