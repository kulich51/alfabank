package ru.exercise.alfabank.service;

import org.springframework.http.ResponseEntity;

public interface CurrencyRateService {

    ResponseEntity<byte[]> compareCurrencies(String comparedCurrency);
}
