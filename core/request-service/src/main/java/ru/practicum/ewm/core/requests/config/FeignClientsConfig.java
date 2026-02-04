package ru.practicum.ewm.core.requests.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.practicum.ewm.core.api.contracts.events.EventsFeignClient;
import ru.practicum.ewm.core.api.contracts.users.UsersFeignClient;

@Configuration
@ComponentScan(basePackages = {"ru.practicum.ewm.core.api"})
@EnableFeignClients(clients = {EventsFeignClient.class, UsersFeignClient.class})
public class FeignClientsConfig {
}