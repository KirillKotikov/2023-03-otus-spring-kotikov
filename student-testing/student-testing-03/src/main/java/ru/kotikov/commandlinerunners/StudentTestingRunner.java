package ru.kotikov.commandlinerunners;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.kotikov.services.StudentTestingServiceImpl;


@Component
public class StudentTestingRunner implements CommandLineRunner {

    private final StudentTestingServiceImpl studentTestingService;

    public StudentTestingRunner(StudentTestingServiceImpl studentTestingService) {
        this.studentTestingService = studentTestingService;
    }

    @Override
    public void run(String... args) {
        studentTestingService.startTesting();
    }

}
