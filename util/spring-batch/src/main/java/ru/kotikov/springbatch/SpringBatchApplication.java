package ru.kotikov.springbatch;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.sql.SQLException;

@EnableMongock
@EnableMongoRepositories
@SpringBootApplication
public class SpringBatchApplication {

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(SpringBatchApplication.class, args);
        Console.main(args);
    }

}
