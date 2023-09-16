package ru.kotikov.springintegrations.services;

import ru.kotikov.springintegrations.domain.Baggage;
import ru.kotikov.springintegrations.domain.Item;

public interface BaggageSecurityService {

    Baggage inspection(Item item);
}
