package org.newcih.service.agent;

import org.newcih.service.loader.ReloadClassLoader;
import org.newcih.service.watch.ApacheFileWatchService;
import org.newcih.util.GaloisLog;
import org.newcih.util.SystemUtils;

import java.io.File;
import java.lang.instrument.Instrumentation;
import java.util.Collections;
import java.util.Objects;
import java.util.function.Consumer;

public class PremainService {

    public static final GaloisLog LOGGER = GaloisLog.getLogger(PremainService.class);

    public static final String GRADLE_OUTPUT = "build";

    public static final String MAVEN_OUTPUT = "target";

    public static final boolean useMaven = true;

    public static void premain(String agentArgs, Instrumentation inst) {
        LOGGER.info("premain was called");

        int loadedClassLength = inst.getAllLoadedClasses().length;
        Class<?> clazz = inst.getAllLoadedClasses()[loadedClassLength - 1];
        String classPath = Objects.requireNonNull(clazz.getResource("")).getPath();
        String outputPath;

        if (useMaven) {
            classPath = classPath.substring(1).replace("/", "\\");
            outputPath = classPath;
        } else {
            outputPath = classPath;
        }

        LOGGER.info("output path is %s", outputPath);

        ApacheFileWatchService targetWatch = new ApacheFileWatchService(outputPath);
        targetWatch.setIncludeFileTypes(Collections.singletonList("class"));

        Consumer<File> handler = file -> {
            String path = file.getAbsolutePath();
            String className = path.replace(outputPath, "").replaceAll(SystemUtils.isWindowOS() ? "\\\\" : "/", ".").replace(".class", "");

            try {
                ReloadClassLoader reloadClassLoader = new ReloadClassLoader(Collections.singletonList(outputPath));
                Class<?> bean = reloadClassLoader.loadClass(className);
                LOGGER.info("使用 %s 加载 %s", reloadClassLoader, className);
            } catch (Throwable e) {
                LOGGER.error("reload class file throw exception", e);
            }
        };

        targetWatch.setModiferHandler(handler);
        targetWatch.setCreateHandler(handler);
        targetWatch.start();
    }

}