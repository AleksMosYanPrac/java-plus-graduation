package ru.practicum.ewm.core.clients;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.practicum.ewm.core.api.contracts.requests.RequestsFeignClient;

@Configuration
@ComponentScan(basePackages = {"ru.practicum.ewm.core.api"})
@EnableFeignClients(clients = {RequestsFeignClient.class})
public class RequestsFeignClientConfig {
}