package ru.kotikov.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.kotikov.models.Student;
import ru.kotikov.services.StudentTestingServiceImpl;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommands {

    private Student student;

    private final StudentTestingServiceImpl studentTestingService;


    @ShellMethod(value = "Ввод Имени и Фамилии через тире (например Кирилл-Котиков)", key = {"fl"})
    public String getFirstAndLastName(@ShellOption(defaultValue = "Студент-Безимянный") String userName) {
        String[] fl = userName.split("-");
        student = new Student(fl[0], fl[1]);
        return String.format("Добро пожаловать: %s %s", fl[0], fl[1]);
    }

    @ShellMethod(value = "Start test", key = {"start"})
    @ShellMethodAvailability(value = "isStartAvailable")
    public void startTest() {
        studentTestingService.startTesting(student);
    }

    private Availability isStartAvailable() {
        return student == null ? Availability.unavailable("Сначала ведите Имя и Фамилию через тире (например: " +
                                                          "\"fl Кирилл-Котиков\")!") : Availability.available();
    }
}
