package ru.exercise.alfabank.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.exercise.alfabank.service.CurrencyRateService;

@RestController
@RequestMapping("/currency")
public class CurrencyRateController {

    private final CurrencyRateService currencyService;

    public CurrencyRateController(CurrencyRateService currencyService) {
        this.currencyService = currencyService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{comparedCurrency}")
    ResponseEntity<byte[]> compareCurrencies(@PathVariable String comparedCurrency) {

        return currencyService.compareCurrencies(comparedCurrency);
    }
}
