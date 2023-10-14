package ru.kotikov.blog.Utils;

import lombok.extern.slf4j.Slf4j;
import ru.kotikov.blog.Exceptions.DataNotFoundException;

@Slf4j
public class ErrorUtil {

    public static void notExistsBy(
            String entityName, String fieldName, String value) {
        String error = String.format("%s with %s = \"%s\" is not exists!",
                entityName, fieldName, value);
        log.error(error);
        throw new DataNotFoundException(error);
    }

}
