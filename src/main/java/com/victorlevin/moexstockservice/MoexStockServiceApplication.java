package com.victorlevin.moexstockservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

/*
 * С помощью этой аннотации @EnableFeignClients мы включаем сканирование компонентов для интерфейсов,
 * которые заявляют, что они являются клиентами Feign.
 * */
@SpringBootApplication
@EnableFeignClients //включаем фейнклиенты
@EnableCaching //включаем кэширование
public class MoexStockServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoexStockServiceApplication.class, args);
    }

}
