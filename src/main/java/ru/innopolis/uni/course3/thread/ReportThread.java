package ru.innopolis.uni.course3.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.innopolis.uni.course3.DTData;

import java.util.Map;

/**
 *  Используется для создания потока вывода итоговой информации. Является потоком-демоном.
 */
public class ReportThread extends Thread {

    private static Logger logger = LoggerFactory.getLogger(ReportThread.class);

    public ReportThread() {
        setDaemon(true);
    }

    @Override
    public void run() {
        logger.info("Report thread have started");

        while(!DTData.isShutDown) {

            StringBuilder result = new StringBuilder();
            synchronized (DTData.MAP) {
                if (DTData.MAP.size() == 0) {
                    continue;
                }
                for(Map.Entry<String, Integer> entry: DTData.MAP.entrySet()) {
                    result.append(entry.getKey())
                            .append("   ")
                            .append(entry.getValue())
                            .append("\n");
                }
            }
            if(!result.toString().isEmpty()) {
                System.out.println(result);
            }
            if(DTData.TotalSum != 0) {
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!! Total sum is: " + DTData.TotalSum);
            }
        }
    }
}
