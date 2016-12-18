package ru.innopolis.uni.course3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import ru.innopolis.uni.course3.exception.InvalidResourceException;
import ru.innopolis.uni.course3.resource.Resource;
import ru.innopolis.uni.course3.thread.ProcessThread;
import ru.innopolis.uni.course3.thread.ReportThread;

import java.util.*;

/**
 *
 * Необходимо разработать программу, которая получает на вход список ресурсов, содержащих текст,
 * и считает общее количество вхождений (для всех ресурсов) каждого слова.
 * Каждый ресурс должен быть обработан в отдельном потоке, текст не должен содержать инностранных символов,
 * только кириллица, знаки препинания и цифры. Отчет должен строиться в режиме реального времени,
 * знаки препинания и цифры в отчет не входят. Все ошибки должны быть корректно обработаны,
 * все API покрыто модульными тестами
 * Входящий аргумент args содержит массив адресных строк ресурсов, например
 *  {"D:\\Temp\\Examples\\TextForTokenizer1.txt",
 *   "D:\\Temp\\Examples\\TextForTokenizer2.txt",
 *   "http://vjanetta.narod.ru/text.html"};
 */
public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        if (args.length == 0) {
            logger.warn("The list of resources are empty");
            throw new IndexOutOfBoundsException("Not enough parameters");
        }

        Parser parser = new Parser();

        try {
            // process thread
            for (String addressLine : args) {
                Resource resource = parser.getResourceByAddressLine(addressLine);
                if (resource == null) {
                    MDC.put("addressLine", addressLine);
                    throw new InvalidResourceException();
                }
                Thread thread = new ProcessThread(resource,
                        new Validator(DTData.VALID_CHARACTERS),
                        new Tokenizer(DTData.EXCLUDED_CHARACTERS));
                thread.start();
            }
            // report thread
            Thread reportThread = new ReportThread();
            reportThread.start();

        } catch (InvalidResourceException exception) {
            logger.warn("The application have interrupted." + "\n" + "It's impossible to determine resource type.");
            DTData.isShutDown = true;
            synchronized (DTData.MAP) {
                DTData.MAP.clear();
            }
        }

    }
}
