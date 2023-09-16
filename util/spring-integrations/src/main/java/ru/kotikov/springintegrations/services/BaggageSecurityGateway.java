package ru.kotikov.springintegrations.services;


import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.kotikov.springintegrations.domain.Baggage;
import ru.kotikov.springintegrations.domain.Item;

import java.util.Collection;

@MessagingGateway
public interface BaggageSecurityGateway {

    @Gateway(requestChannel = "itemChannel", replyChannel = "baggageChannel")
    Collection<Baggage> process(Collection<Item> item);
}
