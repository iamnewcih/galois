/*
 * MIT License
 *
 * Copyright (c) [2023] [liuguangsheng]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.liuguangsheng.galois.service.runners;

import org.liuguangsheng.galois.service.FileWatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * file watch runner
 *
 * @author liuguangsheng
 * @see FileWatchService
 */
public class FileWatchRunner extends AbstractRunner {

    private static final Logger logger = LoggerFactory.getLogger(FileWatchRunner.class);
    public static final int RANK = 1;
    private static final FileWatchService fileWatchService = FileWatchService.getInstance();

    /**
     * Instantiates a new File watch runner.
     */
    public FileWatchRunner() {
        setRank(RANK);
    }

    @Override
    public void started(ConfigurableApplicationContext context) {
        logger.info("{} with context {} is {}", getClass().getSimpleName(), context.getId(), "started");

        if (!isApplicationContext(context)) {
            return;
        }

        logger.info("{} is Running.", getClass().getSimpleName());
        fileWatchService.start();
    }

}