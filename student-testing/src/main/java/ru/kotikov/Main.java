package ru.kotikov;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.kotikov.services.StudentTestingService;

@ComponentScan
@Configuration
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        StudentTestingService studentTestingService = context.getBean(StudentTestingService.class);
        studentTestingService.startTesting();
    }
}