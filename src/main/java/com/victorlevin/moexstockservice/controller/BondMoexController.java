package com.victorlevin.moexstockservice.controller;

import com.victorlevin.moexstockservice.dto.BondDto;
import com.victorlevin.moexstockservice.dto.FigiesDto;
import com.victorlevin.moexstockservice.dto.StocksDto;
import com.victorlevin.moexstockservice.dto.StocksPricesDto;
import com.victorlevin.moexstockservice.dto.TickersDto;
import com.victorlevin.moexstockservice.service.BondService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bonds")
@RequiredArgsConstructor
public class BondMoexController {
    private final BondService bondService;

    /**
     *
     * @param tickersDto массив тикеров обернутых в TickersDto
     * @return объект StocksDto (массив Stock)
     */
    @PostMapping("/getBondsByTickers")
    public StocksDto getBondsFromMoex(@RequestBody TickersDto tickersDto) {
        return bondService.getBondsFromMoex(tickersDto);
    }

    @PostMapping("/prices")
    public StocksPricesDto getPricesByFigies(@RequestBody FigiesDto figiesDto) {
        return bondService.getPricesByFigies(figiesDto);
    }

    @GetMapping("/allBonds")
    public List<BondDto> getBondDto(){
        return bondService.getAllBonds();
    }

    @GetMapping("/greeting")
    public String greeting(){
        return "Hello!!! ";
    }
}
