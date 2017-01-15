package ru.innopolis.uni.course3.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.innopolis.uni.course3.DTData;
import ru.innopolis.uni.course3.Parser;
import ru.innopolis.uni.course3.Tokenizer;
import ru.innopolis.uni.course3.Validator;
import ru.innopolis.uni.course3.exception.InvalidResourceException;
import ru.innopolis.uni.course3.resource.Resource;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 *  Используется для создания потока подсчета количество вхождений слов в текст ресурса
 */
@Component
@Scope("prototype")
public class ProcessThread extends Thread {

    private static Logger logger = LoggerFactory.getLogger(ProcessThread.class);

    @Autowired
    private Resource resource;
    @Autowired
    private Validator validator;
    @Autowired
    private Tokenizer tokenizer;
    @Autowired
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
                if(validator.validateTextForNumbers(resourceText))  {
                    // в тексте ресурса только числа, считаем их сумму и добавляем к общей сумме
                    List<String> list = Arrays.asList(resourceText.split(DTData.WHITESPACES));
                    Optional<Integer> result = list.stream()
                            .map(s -> Integer.parseInt(s))
                            .filter(i -> i > 0 && i % 2 == 0)
                            .reduce((i1, i2) -> i1 + i2);
                    DTData.TotalSum += result.get();
                } else {  // разбиваем текст на слова и добавляем их в карту
                    List<String> wordList = tokenizer.tokenizeText(resourceText);
                    for (String word : wordList) {
                        if (DTData.isShutDown) {
                            logger.warn("Process thread of resource " + resource.getResourceLine() + "have broken off");
                            break;
                        }
                        synchronized (DTData.MAP) {
                            Integer quantity = DTData.MAP.containsKey(word) ? DTData.MAP.get(word) + 1 : 1;
                            DTData.MAP.put(word, quantity);
                        }
                    }
                }
            }
        } catch (InvalidResourceException exception) {
            logger.warn("The application have interrupted." + "\n" + "The text of resource " + this.resource.getResourceLine() + " contains invalid characters");
            DTData.isShutDown = true;
        }
    }

}
