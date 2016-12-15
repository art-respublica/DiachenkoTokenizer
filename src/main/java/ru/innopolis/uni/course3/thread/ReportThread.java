package ru.innopolis.uni.course3.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 *
 */
public class ReportThread extends Thread {

    private static Logger logger = LoggerFactory.getLogger(ReportThread.class);

    private final Map<String, Integer> map;

    public ReportThread(Map<String, Integer> map) {
        setDaemon(true);
        this.map = map;
    }

    @Override
    public void run() {
        logger.info("Report thread have started");

        while(!Thread.currentThread().isInterrupted()) {

            StringBuilder result = new StringBuilder();
            synchronized (map) {
                if (map.size() == 0) {
                    continue;
                }
                for(Map.Entry<String, Integer> entry: map.entrySet()) {
                    result.append(entry.getKey())
                            .append("   ")
                            .append(entry.getValue())
                            .append("\n");
                }
            }
            if(!"".equals(result)) {
                System.out.println(result);
            }
        }
    }
}
