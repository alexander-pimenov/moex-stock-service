package com.victorlevin.moexstockservice.dto;

import lombok.Value;

/**
 * Класс для хранения данных по облигации
 * айдишник, имя, цена
 */
@Value
public class BondDto {
    String ticker;
    String name;
    Double price;
}
