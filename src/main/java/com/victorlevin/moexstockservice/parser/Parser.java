package com.victorlevin.moexstockservice.parser;


import com.victorlevin.moexstockservice.dto.BondDto;

import java.util.List;

/**
 * Используем интерфейс для того чтобы можно было легко менять реализации парсера.
 */
public interface Parser {
    /**
     * Возвращает список полученных облигаций.
     * @param ratesAsString строковое представление xml данных
     * @return список объектов {@link BondDto}
     */
    List<BondDto> parse(String ratesAsString);
}
