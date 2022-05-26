package com.victorlevin.moexstockservice.moexclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * FeignClient, который дергает государственные облигации.
 * Важно понимать, что каждый клиент Feign состоит из набора настраиваемых компонентов.
 * Мы объявляем клиента Feign с помощью аннотации @FeignClient
 * Аргумент value , передаваемый в аннотации @FeignClient ,
 * является обязательным произвольным именем клиента, а с
 * аргументом url мы указываем базовый URL-адрес API.
 * поскольку этот интерфейс является клиентом Feign, мы можем
 * использовать аннотации Spring Web для объявления API, к
 * которым мы хотим обратиться.
 */
@FeignClient(name = "govbonds", url = "${moex.bonds.government.url}", configuration = FeignConfig.class)
public interface GovBondsClient {
    /**
     * GET запрос на МосБиржу
     *
     * @return строковое представление xml данных
     */
    @GetMapping
    String getBondsFromMoex();
}
