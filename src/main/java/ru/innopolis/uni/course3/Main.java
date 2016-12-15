package ru.innopolis.uni.course3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import ru.innopolis.uni.course3.exception.InvalidResourceException;
import ru.innopolis.uni.course3.resource.Resource;
import ru.innopolis.uni.course3.thread.ManagerThread;
import ru.innopolis.uni.course3.thread.ProcessThread;
import ru.innopolis.uni.course3.thread.ReportThread;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;

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

    //"\\p{InCYRILLIC}*\\p{IsDigit}*\\p{Punct}*"
    public final static String CYRILLIC_LETTERS = "А-Яа-яЁё";
    public final static String DIGITS = "0-9";
    public final static String PUNCTUATION_MARKS = "!\"#$%&'()\\*\\+,-\\./:;<=>\\?@\\[\\]\\^_`{|}~„“«»“”‘’‹›‒–—―‐-";
    public final static String VALID_CHARACTERS = CYRILLIC_LETTERS + DIGITS + PUNCTUATION_MARKS;
    public final static String EXCLUDED_CHARACTERS = DIGITS + PUNCTUATION_MARKS;

    public final static Map<String, Integer> MAP = new TreeMap<>(); //new ConcurrentSkipListMap<>();
    private static volatile Boolean isShutDown = false;

    public static void main(String[] args) {

        if (args.length == 0) {
            logger.warn("The list of resources are empty");
            throw new IndexOutOfBoundsException("Not enough parameters");
        }

        Parser parser = new Parser();

        try {
            // process thread
            List<Thread> threads = new ArrayList<>();
            for (String addressLine : args) {
                Resource resource = parser.getResourceByAddressLine(addressLine);
                if (resource == null) {
                    MDC.put("addressLine", addressLine);
                    throw new InvalidResourceException();
                }
                Thread thread = new ProcessThread(resource, MAP, isShutDown, VALID_CHARACTERS, EXCLUDED_CHARACTERS);
                threads.add(thread);
                thread.start();
            }
            // report thread
            Thread reportThread = new ReportThread(MAP);
            reportThread.start();

            // manager thread
            Thread managerThread = new ManagerThread(isShutDown, threads);
            managerThread.start();

        } catch (InvalidResourceException exception) {
            logger.warn("It's impossible to determine resource type or there are another validation problems");
        }

    }
}
