package ru.innopolis.uni.course3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.innopolis.uni.course3.exception.InvalidResourceException;
import ru.innopolis.uni.course3.resource.FileResource;
import ru.innopolis.uni.course3.resource.Resource;
import ru.innopolis.uni.course3.resource.URLResource;

import java.io.*;

/**
 * Используется для разбора адресной строки ресурса и получения текста по ней.
 */
@Component
public class Parser {

    private static Logger logger = LoggerFactory.getLogger(Parser.class);

    /**
     * Получает текст ресурса
     * @param resource      объект-ресурс, текст которого читается
     * @param validator     валидатор, который проверяет отсутствие недопустимых символов в тексте ресурса
     * @return String       текст ресурса
     */
    public String getResourceText(Resource resource, Validator validator) throws InvalidResourceException {

        String resourceText = "";
        InputStream is = resource.getInputStream();
        BufferedReader reader = null;
        try {
            String resourceLine = "";
            reader = new BufferedReader(new InputStreamReader(is));
            while (reader.ready()) {
                resourceLine = reader.readLine();
                if(!validator.validateText(resourceLine)) {
                    throw new InvalidResourceException();
                }
                resourceText += resourceLine + "\n";
            }
        } catch (IOException e) {
            logger.warn("Some errors during stream processing");
        } finally {
            try {
                is.close();
                reader.close();
            } catch (Exception exception) {
                logger.warn("InputStream and Reader didn't close");
            }
        }
        return resourceText;
    }

    /**
     * Опеределяет тип ресурса по адресной строке и возвращает объект-ресурс соответствующего типа.
     * @param addressLine   адресная строка, соответствующая ресурсу. Ожидается в формате //protocol://host:port/resource,
     *                          тип ресурса определеяется по protocol. Если текст не соотвествет формату,
     *                          строка считается адресом к файлу и определяет тип ресурса - файл.
     * @return Resource     объект-ресурс, соответсвующий входящей строке
     */
    public Resource getResourceByAddressLine(String addressLine ) throws InvalidResourceException {

        Resource resource = null;
        //protocol://host:port/resource
        if (addressLine.contains("http")) {
            resource = new URLResource(addressLine);
        } else if (addressLine.contains("ftp")) {

        } else {
            File file = new File(addressLine);
            if(!file.exists()) {
                logger.warn("File not found");
                throw new InvalidResourceException();
            }
            resource = new FileResource(addressLine);
        }
        return resource;
    }
}
