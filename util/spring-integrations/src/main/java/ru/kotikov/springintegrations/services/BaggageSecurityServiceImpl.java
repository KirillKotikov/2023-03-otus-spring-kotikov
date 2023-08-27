package ru.kotikov.springintegrations.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kotikov.springintegrations.domain.Baggage;
import ru.kotikov.springintegrations.domain.Item;

import java.util.Random;

@Service
@Slf4j
public class BaggageSecurityServiceImpl implements BaggageSecurityService {

    @Override
    public Baggage inspection(Item item) {
        log.info("Inspection started ...");
        delay();
        Random random = new Random();
        Baggage baggage = new Baggage(random.nextBoolean());
        log.info("Inspection done, safe = {}", baggage.isSafe());
        return baggage;
    }

    private static void delay() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
