package ru.innopolis.uni.course3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.innopolis.uni.course3.exception.InvalidResourceException;
import ru.innopolis.uni.course3.resource.Resource;
import ru.innopolis.uni.course3.thread.ProcessThread;
import ru.innopolis.uni.course3.thread.ReportThread;

/**
 *  Основной класс запуска приложения для решения следующей задачи:
 *
 *  Необходимо разработать программу, которая получает на вход список ресурсов, содержащих текст,
 *  и считает общее количество вхождений (для всех ресурсов) каждого слова.
 *  Каждый ресурс должен быть обработан в отдельном потоке, текст не должен содержать инностранных символов,
 *  только кириллица, знаки препинания и цифры. Отчет должен строиться в режиме реального времени,
 *  знаки препинания и цифры в отчет не входят. Все ошибки должны быть корректно обработаны,
 *  все API покрыто модульными тестами
 *
 *  Расширение:
 *  Необходимо разработать программу, которая получает на вход список ресурсов,
 *  содержащих набор чисел и считает сумму всех положительных четных.
 *  Каждый ресурс должен быть обработан в отдельном потоке, набор должен содержать лишь числа,
 *  унарный оператор "-" и пробелы.
 *  Общая сумма должна отображаться на экране и изменяться в режиме реального времени.
 *  Запуск потоков реализовать через ссылки на методы, итоговый подсчет суммы через stream API
 *
 *  Входящий аргумент args содержит массив адресных строк ресурсов, например
 *  {"D:\\Temp\\Examples\\TextForTokenizer1.txt",
 *   "D:\\Temp\\Examples\\TextForTokenizer2.txt",
 *   "http://vjanetta.narod.ru/text.html"};
 *
 *   Для каждой адресной строки ресурса создается и запускается отдельный поток для обработки подсчета вхождений
 *      входящих слов. Также запускается отдельный поток для отображения итоговой информации.
 *
 */
public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        if (args.length == 0) {
            logger.warn("The list of resources are empty");
            throw new IndexOutOfBoundsException("The list of resources are empty");
        }

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[]{"appContext.xml"});

        try {
            Parser parser = (Parser) applicationContext.getBean("parser");

            // process threads
            for (String addressLine : args) {
                Resource resource = parser.getResourceByAddressLine(addressLine);
                if (resource == null) {
                    MDC.put("addressLine", addressLine);
                    throw new InvalidResourceException();
                }
                ProcessThread thread = (ProcessThread) applicationContext.getBean("processThread");
                thread.setResource(resource);
                thread.start();
            }

            // report thread
            Thread reportThread = new ReportThread();
            reportThread.start();

        } catch (InvalidResourceException exception) {
            logger.warn("The application have interrupted." + "\n" + "It's impossible to determine resource type.");
            DTData.isShutDown = true;
        }

    }
}
