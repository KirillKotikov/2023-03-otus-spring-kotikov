package ru.kotikov.springintegrations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.kotikov.springintegrations.services.ItemService;


@SpringBootApplication
public class App {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(App.class, args);
		ItemService orderService = ctx.getBean(ItemService.class);
		orderService.startGenerateItemsLoop();
	}
}
