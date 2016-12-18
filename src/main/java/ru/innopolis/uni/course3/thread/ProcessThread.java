package ru.innopolis.uni.course3.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.innopolis.uni.course3.DTData;
import ru.innopolis.uni.course3.Parser;
import ru.innopolis.uni.course3.Tokenizer;
import ru.innopolis.uni.course3.Validator;
import ru.innopolis.uni.course3.exception.InvalidResourceException;
import ru.innopolis.uni.course3.resource.Resource;

import java.util.List;

/**
 *
 */
public class ProcessThread extends Thread {

    private static Logger logger = LoggerFactory.getLogger(ProcessThread.class);

    private Resource resource;
    private Validator validator;
    private Tokenizer tokenizer;

    public ProcessThread(Resource resource, Validator validator, Tokenizer tokenizer) {
        this.resource  = resource;
        this.validator = validator;
        this.tokenizer = tokenizer;
    }

    @Override
    public void run() {
        logger.info("Thread for resource " + resource.getResourceLine() + " have started");
        try {
            String resourceText = new Parser().getResourceText(resource, validator);
            if(DTData.isShutDown) {
                logger.warn("Process thread of resource " + resource.getResourceLine() + "have broken off");
                return;
            }
            if(validator.validateText(resourceText)) {
                List<String> wordList = tokenizer.tokenizeText(resourceText);
                for (String word : wordList) {
                    if (DTData.isShutDown) {
                        logger.warn("Process thread of resource " + resource.getResourceLine() +  "have broken off");
                        break;
                    }
                    synchronized (DTData.MAP) {
                        Integer quantity = DTData.MAP.containsKey(word) ? DTData.MAP.get(word) + 1 : 1;
                        DTData.MAP.put(word, quantity);
                    }
                }
            }
        } catch (InvalidResourceException exception) {
            logger.warn("The application have interrupted." + "\n" + "The text of resource " + this.resource.getResourceLine() + " text contains invalid characters");
            DTData.isShutDown = true;
            synchronized (DTData.MAP) {
                DTData.MAP.clear();
            }
        }
    }

}
