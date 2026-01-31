package ru.practicum.ewm.core.requests.clients;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.practicum.ewm.core.api.contracts.users.UsersFeignClient;

@Configuration
@ComponentScan(basePackages = {"ru.practicum.ewm.core.api"})
@EnableFeignClients(clients = {UsersFeignClient.class})
public class UsersFeignClientConfig {
}
