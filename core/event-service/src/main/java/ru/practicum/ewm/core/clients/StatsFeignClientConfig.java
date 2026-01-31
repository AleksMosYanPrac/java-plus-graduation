package ru.practicum.ewm.core.clients;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.practicum.ewm.stats.client.StatsFeignClient;

@Configuration
@ComponentScan(basePackages = {"ru.practicum.ewm.stats.client"})
@EnableFeignClients(clients = {StatsFeignClient.class})
public class StatsFeignClientConfig {
}