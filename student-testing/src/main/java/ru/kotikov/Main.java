package ru.kotikov;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.kotikov.services.StudentTestingService;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("/spring-context.xml");
        StudentTestingService studentTestingService = context.getBean(StudentTestingService.class);
        studentTestingService.startTesting();
    }
}