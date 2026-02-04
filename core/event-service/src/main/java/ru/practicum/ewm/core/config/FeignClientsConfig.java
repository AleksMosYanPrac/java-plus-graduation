package ru.practicum.ewm.core.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.practicum.ewm.core.api.contracts.requests.RequestsFeignClient;
import ru.practicum.ewm.core.api.contracts.users.UsersFeignClient;
import ru.practicum.ewm.stats.client.StatsFeignClient;

@Configuration
@ComponentScan(basePackages = {"ru.practicum.ewm.core.api", "ru.practicum.ewm.stats.client"})
@EnableFeignClients(clients = {RequestsFeignClient.class, UsersFeignClient.class, StatsFeignClient.class})
public class FeignClientsConfig {
}