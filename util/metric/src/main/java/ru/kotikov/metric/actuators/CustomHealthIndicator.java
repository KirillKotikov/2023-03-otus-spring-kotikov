package ru.kotikov.metric.actuators;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CustomHealthIndicator implements HealthIndicator {


    @Override
    public Health health() {
        Date five = new Date();
        five.setHours(5);
        five.setMinutes(0);
        five.setSeconds(0);
        Date zero = new Date();
        zero.setHours(0);
        zero.setMinutes(0);
        zero.setSeconds(0);
        Date eleven = new Date();
        eleven.setHours(11);
        eleven.setMinutes(0);
        eleven.setSeconds(0);
        Date sixteen = new Date();
        sixteen.setHours(16);
        sixteen.setMinutes(0);
        sixteen.setSeconds(0);
        Date beforeMidnight = new Date();
        beforeMidnight.setHours(23);
        beforeMidnight.setMinutes(59);
        beforeMidnight.setSeconds(59);

        String greetings;
        Date now = new Date();
        if (now.before(five) && now.after(zero)) {
            greetings = "Доброй ночи! ";
        } else if (now.before(eleven) && now.after(five)) {
            greetings = "Доброе утро! ";
        } else if (now.before(sixteen) && now.after(eleven)) {
            greetings = "Добрый день! ";
        } else if (now.before(beforeMidnight) && now.after(sixteen)) {
            greetings = "Добрый вечер! ";
        } else {
            greetings = "Приветствую! ";
        }

        return Health.up().
                withDetail("message", greetings + "Всё в порядке! :)").
                build();
    }

}
