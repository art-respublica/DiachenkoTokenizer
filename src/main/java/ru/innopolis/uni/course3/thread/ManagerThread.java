package ru.innopolis.uni.course3.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 *
 */
public class ManagerThread extends Thread {

    private static Logger logger = LoggerFactory.getLogger(ManagerThread.class);

    private Boolean isShutDown;
    private List<Thread> threads;

    public ManagerThread(Boolean isShutDown, List<Thread> threads) {
        setDaemon(true);
        this.isShutDown = isShutDown;
        this.threads = threads;
    }

    @Override
    public void run() {
        logger.info("Manager thread have started");
        while(!Thread.currentThread().isInterrupted()) {
            if(isShutDown) {
                logger.warn("Threads is interrupted");
                for(Thread thread: threads) {
                    thread.interrupt();
                }
            }
        }
    }
}
