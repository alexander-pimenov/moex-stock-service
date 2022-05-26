package com.victorlevin.moexstockservice.moexclient;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Создали конфиг для фейн клиента, чтобы можно было его залогировать,
 * и видеть в стеке запросы к нему.
 *
 */
@Configuration
public class FeignConfig {

    /**
     * Создадим бин/объект типа Logger.Level
     *
     * На выбор предлагается четыре уровня ведения журнала:
     *
     * NONE — без ведения журнала, значение по умолчанию.
     * BASIC — протоколировать только метод запроса, URL-адрес и статус ответа.
     * HEADERS — регистрируйте основную информацию вместе с заголовками запросов и ответов.
     * FULL — протоколировать тело, заголовки и метаданные как для запроса, так и для ответа
     *
     * @return логируем всю информацию FULL
     */
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}