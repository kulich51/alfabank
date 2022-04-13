package ru.exercise.alphabank.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.exercise.alphabank.model.Rates;

@FeignClient(name = "currency", url = "${currency.url}")
public interface CurrencyClient {

    @GetMapping(value = "/latest.json", params = {"app_id", "symbols"})
    Rates getCurrentCurrencyRates(@RequestParam String app_id,
                                  @RequestParam String symbols);

    @GetMapping(value = "/historical/{yesterday}.json" , params = {"app_id", "symbols"})
    Rates getYesterdayCurrencyRates(@PathVariable String yesterday,
                                    @RequestParam String app_id,
                                    @RequestParam String symbols);
}
