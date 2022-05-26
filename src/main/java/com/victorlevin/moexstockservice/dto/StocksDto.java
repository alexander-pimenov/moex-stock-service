package com.victorlevin.moexstockservice.dto;

import com.victorlevin.moexstockservice.model.Stock;
import lombok.*;

import java.util.List;

/**
 * Обертка над списком {@link com.victorlevin.moexstockservice.model.Stock}
 */
@Value
public class StocksDto {
    List<Stock> stocks;
}
