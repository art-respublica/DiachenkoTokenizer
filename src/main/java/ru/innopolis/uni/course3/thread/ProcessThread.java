package ru.innopolis.uni.course3.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.innopolis.uni.course3.Parser;
import ru.innopolis.uni.course3.Tokenizer;
import ru.innopolis.uni.course3.Validator;
import ru.innopolis.uni.course3.exception.InvalidResourceException;
import ru.innopolis.uni.course3.resource.Resource;

import java.util.List;
import java.util.Map;

/**
 *
 */
public class ProcessThread extends Thread {

    private static Logger logger = LoggerFactory.getLogger(ProcessThread.class);

    private Resource resource;
    private final Map<String, Integer> map;
    private Boolean isShutDown;
    private String correctChars;
    private String excludedChars;

    public ProcessThread(Resource resource, Map<String, Integer> map, boolean isShutDown,
                         String correctChars, String excludedChars) {
        this.resource = resource;
        this.map = map;
        this.isShutDown = isShutDown;
        this.correctChars = correctChars;
        this.excludedChars = excludedChars;
    }

    @Override
    public void run() {
        logger.info("Thread for resource " + resource.getResourceLine() + " have started");
        try {
            String resourceText = new Parser().getResourceText(resource);
            if(!Thread.currentThread().isInterrupted()
                    && new Validator(correctChars).validateText(resourceText)) {
                List<String> wordList = new Tokenizer(excludedChars).tokenizeText(resourceText);
                for (String word : wordList) {
                    if (Thread.currentThread().isInterrupted()) {
                        break;
                    }
                    synchronized (map) {
                        Integer quantity = map.containsKey(word) ? map.get(word) + 1 : 1;
                        map.put(word, quantity);
                    }
                }
            }
        } catch (InvalidResourceException exception) {
            logger.warn("The text of resource " + this.resource.getResourceLine() + " text contains invalid characters");
            isShutDown = true;
        }
    }

}
