package ru.kotikov.springintegrations.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kotikov.springintegrations.domain.Baggage;
import ru.kotikov.springintegrations.domain.Item;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
//    private static final String[] MENU = {"coffee", "tea", "smoothie", "whiskey", "beer", "cola", "water"};

    private final BaggageSecurityGateway baggageSecurity;

    @Override
    public void startGenerateItemsLoop() {
        ForkJoinPool pool = ForkJoinPool.commonPool();
        for (int i = 0; i < 10; i++) {
            delay();
            int num = i + 1;
            pool.execute(() -> {
                Collection<Baggage> baggages = baggageSecurity.process(generateItems());
                log.info("{}, baggages checked: {}", num, baggages.stream()
                        .map(Baggage::isSafe)
                        .collect(Collectors.toList()));
            });
        }
    }

    private Collection<Item> generateItems() {
        return List.of(new Item(), new Item(), new Item(), new Item(), new Item());
    }

    private void delay() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

