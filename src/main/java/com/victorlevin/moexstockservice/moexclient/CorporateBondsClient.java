package com.victorlevin.moexstockservice.moexclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * FeignClient, который дергает корпоративные облигации.
 * Мы объявляем клиента Feign с помощью аннотации @FeignClient
 */
@FeignClient(name = "corporatebonds", url = "${moex.bonds.corporate.url}", configuration = FeignConfig.class)
public interface CorporateBondsClient {
    /**
     * GET запрос на МосБиржу
     *
     * @return строковое представление xml данных
     */
    @GetMapping
    String getBondsFromMoex();
}
