package ru.kotikov.springintegrations.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannelSpec;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.PollerSpec;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;
import ru.kotikov.springintegrations.services.BaggageSecurityService;

@Configuration
public class IntegrationConfig {

    @Bean
    public MessageChannelSpec<?, ?> itemChannel() {
        return MessageChannels.queue(10);
    }

    @Bean
    public MessageChannelSpec<?, ?> baggageChannel() {
        return MessageChannels.publishSubscribe();
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerSpec poller() {
        return Pollers.fixedRate(100).maxMessagesPerPoll(2);
    }

    @Bean
    public IntegrationFlow conveyorFlow(BaggageSecurityService baggageSecurityService) {
        return IntegrationFlow.from(itemChannel())
                .split()
                .handle(baggageSecurityService, "inspection")
                .aggregate()
                .channel(baggageChannel())
                .get();
    }
}
