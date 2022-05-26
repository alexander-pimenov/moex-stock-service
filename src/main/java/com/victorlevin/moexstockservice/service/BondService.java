package com.victorlevin.moexstockservice.service;

import com.victorlevin.moexstockservice.dto.BondDto;
import com.victorlevin.moexstockservice.dto.FigiesDto;
import com.victorlevin.moexstockservice.dto.StockPrice;
import com.victorlevin.moexstockservice.dto.StocksDto;
import com.victorlevin.moexstockservice.dto.StocksPricesDto;
import com.victorlevin.moexstockservice.dto.TickersDto;
import com.victorlevin.moexstockservice.exception.BondNotFoundException;
import com.victorlevin.moexstockservice.model.Currency;
import com.victorlevin.moexstockservice.model.Stock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BondService {
    private final BondRepository bondRepository;

    /* Кэшменеджер отвечает за создание кэша. Он создается с помощью зависимости из pom.xml spring-boot-starter-cache
     * Если всё отрабатывает нормально то в в нем создаются две коллекции из методов bondRepository.getCorporateBonds
     * и bondRepository.getGovBonds
     * С его помощью можно проверить создание в нем кэшируемых коллекций.
     * Если нужно, то можно его раскомментировать, запустить в бебаге и на точке останова увидим, что в нем действительно
     * создаются две коллекции с данными с МосБиржи.*/
    private final CacheManager cacheManager;

    /**
     * Возращает список облигаций согласно тикерам.
     *
     * @param tickersDto массив строковых названий тикеров, обернутых в TickersDto
     * @return массив объектов Stock обернутых в StocksDto
     */
    public StocksDto getBondsFromMoex(TickersDto tickersDto) {
        log.info("BondService#getBondsFromMoex - start: Request for tickers {}", tickersDto.getTickers());
        List<String> tickers = new ArrayList<>(tickersDto.getTickers());

        List<BondDto> allBonds = new ArrayList<>();
        allBonds.addAll(bondRepository.getGovBonds());
        allBonds.addAll(bondRepository.getCorporateBonds());
        List<BondDto> resultBonds = allBonds.stream()
                .filter(b -> tickers.contains(b.getTicker())) //отфильтруем по нужному тикеру
                .collect(Collectors.toList());

        List<Stock> stocks = resultBonds.stream().map(b -> {
            return Stock.builder() //создадим согласно тикеру новый Stock
                    .ticker(b.getTicker())
                    .name(b.getName())
                    .figi(b.getTicker())
                    .type("Bond")
                    .currency(Currency.RUB)
                    .source("MOEX")
                    .build();
        }).collect(Collectors.toList());
        return new StocksDto(stocks); //вернем найденные Stock в обертке StocksDto
    }

    public StocksPricesDto getPricesByFigies(FigiesDto figiesDto) {
        log.info("Request for figies {}", figiesDto.getFigies());
        List<String> figies = new ArrayList<>(figiesDto.getFigies());
        List<BondDto> allBonds = new ArrayList<>();
        allBonds.addAll(bondRepository.getGovBonds());
        allBonds.addAll(bondRepository.getCorporateBonds());
        figies.removeAll(allBonds.stream().map(b -> b.getTicker()).collect(Collectors.toList()));
        if (!figies.isEmpty()) {
            throw new BondNotFoundException(String.format("Bonds %s not found.", figies));
        }
        List<StockPrice> prices = allBonds.stream()
                .filter(b -> figiesDto.getFigies().contains(b.getTicker()))
                .map(b -> new StockPrice(b.getTicker(), b.getPrice() * 10))
                .collect(Collectors.toList());
        return new StocksPricesDto(prices);
    }

    public List<BondDto> getAllBonds() {
        List<BondDto> result = new ArrayList<>();
        List<BondDto> corporateBonds = bondRepository.getCorporateBonds();
        List<BondDto> govBonds = bondRepository.getGovBonds();
        result.addAll(corporateBonds);
        result.addAll(govBonds);
        return result;
    }
}
