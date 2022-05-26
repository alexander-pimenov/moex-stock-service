package com.victorlevin.moexstockservice.service;

import com.victorlevin.moexstockservice.dto.BondDto;
import com.victorlevin.moexstockservice.exception.LimitRequestsException;
import com.victorlevin.moexstockservice.moexclient.CorporateBondsClient;
import com.victorlevin.moexstockservice.moexclient.GovBondsClient;
import com.victorlevin.moexstockservice.parser.Parser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Класс, в котором мы получаем двумя методами облигациии с МосБиржи.
 * В этих методах парсим эти облигации. И сохраняем их в коллекции.
 * Эти методы кэшируют данные.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BondRepository {
    private final CorporateBondsClient corporateBondsClient;
    private final GovBondsClient govBondsClient;

    private final Parser bondsParser;


    /**
     * Получает корпоративные облигации.
     * Полученные данные кэшируем, благодаря аннотациии @Cacheable.
     * value = "corps" - это даем название кэшируемой коллекции.
     *
     * @return список облигаций
     */
    @Cacheable(value = "corps")
    public List<BondDto> getCorporateBonds() {
        log.info("Getting corporate bonds from Moex");
        String xmlFromMoex = corporateBondsClient.getBondsFromMoex();

        List<BondDto> bonds = bondsParser.parse(xmlFromMoex);
        /*Если превысили лимит запросов к Бирже, то нам вернется пустой список.
         * Делаем проверку на это.*/
        if (bonds.isEmpty()) {
            log.error("Moex isn't answering for getting corporate bonds.");
            throw new LimitRequestsException("Moex isn't answering for getting corporate bonds.");
        }
        return bonds;
    }

    /**
     * Получает государственные облигации
     * Полученные данные кэшируем, благодаря аннотациии @Cacheable.
     * value = "govs" - это даем название кэшируемой коллекции.
     *
     * @return список облигаций
     */
    @Cacheable(value = "govs")
    public List<BondDto> getGovBonds() {
        log.info("Getting government bonds from Moex");
        String xmlFromMoex = govBondsClient.getBondsFromMoex();

        List<BondDto> bonds = bondsParser.parse(xmlFromMoex);
        /*Если превысили лимит запросов к Бирже, то нам вернется пустой список.
         * Делаем проверку на это.*/
        if (bonds.isEmpty()) {
            log.error("Moex isn't answering for getting government bonds.");
            throw new LimitRequestsException("Moex isn't answering for getting government bonds.");
        }
        return bonds;
    }
}
