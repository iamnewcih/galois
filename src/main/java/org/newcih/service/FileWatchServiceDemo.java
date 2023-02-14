package org.newcih.service;

import org.newcih.service.watch.JdkFileWatchService;
import org.newcih.util.SystemUtils;

import java.io.IOException;

public class FileWatchServiceDemo {


    public static void main(String[] args) throws IOException, InterruptedException {

        JdkFileWatchService fileWatchService;
        if (SystemUtils.isWindowOS()) {
            fileWatchService = new JdkFileWatchService(new String[]{
                    "C:\\Users\\liuguangsheng.SZSYY\\IdeaProjects\\galois\\target"
            });
        } else {
            fileWatchService = new JdkFileWatchService(new String[]{"/Users/cihnew"});
        }

//        fileWatchService.setConsumer(watchEvent -> {
//            if (StandardWatchEventKinds.ENTRY_MODIFY.equals(watchEvent.kind())) {
//                LOGGER.info("检测到修改了 <" + watchEvent.context() + ">");
//            }
//        });
        fileWatchService.start();

        Thread.currentThread().join();
    }
}