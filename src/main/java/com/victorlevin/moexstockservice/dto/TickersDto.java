package com.victorlevin.moexstockservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Хранит  в себе массив строк тикеров, по которым находим в Бирже данные и
 * перекладываем их в объекты {@link com.victorlevin.moexstockservice.model.Stock}.
 */
@NoArgsConstructor
@Getter
@Setter
public class TickersDto {
    private List<String> tickers;
}
