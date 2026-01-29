package ru.practicum.ewm.client;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.practicum.ewm.stats.client.StatsClient;

@Configuration
@ComponentScan(basePackages = "ru.practicum.ewm.stats.client")
@EnableFeignClients(clients = StatsClient.class)
public class StatsClientConfig {

}