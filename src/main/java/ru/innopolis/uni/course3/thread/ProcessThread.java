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
 *  Используется для создания потока подсчета количество вхождений слов в текст ресурса
 */
public class ProcessThread extends Thread {

    private static Logger logger = LoggerFactory.getLogger(ProcessThread.class);

    private Resource resource;
    private Validator validator;
    private Tokenizer tokenizer;
    private Parser parser;

    public ProcessThread() {
    }

    public ProcessThread(Resource resource, Parser parser, Validator validator, Tokenizer tokenizer) {
        this.resource  = resource;
        this.parser    = parser;
        this.validator = validator;
        this.tokenizer = tokenizer;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    @Override
    public void run() {
        logger.info("Thread for resource " + resource.getResourceLine() + " have started");
        try {
            String resourceText = parser.getResourceText(resource, validator);
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
            logger.warn("The application have interrupted." + "\n" + "The text of resource " + this.resource.getResourceLine() + " contains invalid characters");
            DTData.isShutDown = true;
        }
    }

}
