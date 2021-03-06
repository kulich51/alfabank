package ru.exercise.alfabank;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.exercise.alfabank.client.CurrencyClient;
import ru.exercise.alfabank.client.GifClient;
import ru.exercise.alfabank.exception.CurrencyNotFoundException;
import ru.exercise.alfabank.model.GifObject;
import ru.exercise.alfabank.model.ImageProperties;
import ru.exercise.alfabank.model.ImagesObject;
import ru.exercise.alfabank.model.Rates;
import ru.exercise.alfabank.service.impl.CurrencyRateServiceImpl;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static ru.exercise.alfabank.constant.AlfabankApplicationTestConstants.*;


@SpringBootTest
class AlfabankApplicationTests {

    @Autowired
    private CurrencyRateServiceImpl currencyRateService;

    @Mock
    private CurrencyClient currencyClient;

    @Mock
    private GifClient gifClient;

    @Mock
    private GifObject gifObject;

    @Mock
    private ImagesObject imagesObject;

    @Mock
    private ImageProperties imageProperties;

    private Map<String, Double> currentRatesData = new HashMap<>();
    private Map<String, Double> yesterdayRatesData = new HashMap<>();

    private Rates currentRates;
    private Rates yesterdayRates;

    @BeforeEach
    void init() {

        currentRatesData.put(TESTED_CURRENCY, TESTED_RATE + 10);
        yesterdayRatesData.put(TESTED_CURRENCY, TESTED_RATE);

        currentRates = new Rates(BASE_CURRENCY, currentRatesData);
        yesterdayRates = new Rates(BASE_CURRENCY, yesterdayRatesData);

        when(currencyClient
                .getCurrentCurrencyRates(any(String.class), any(String.class))
        ).thenReturn(currentRates);

        when(currencyClient
                .getYesterdayCurrencyRates(any(String.class), any(String.class), any(String.class))
        ).thenReturn(yesterdayRates);


        when(gifClient.getGif(any(String.class), any(String.class))).thenReturn(gifObject);
        when(gifObject.getRandomGif()).thenReturn(imagesObject);
        when(imagesObject.getImages()).thenReturn(new HashMap<>());
        when(imageProperties.getUrl()).thenReturn(TESTED_GIF_URL);
    }

    @ParameterizedTest
    @ValueSource(strings = {INVALID_CURRENCY, BLANK_CURRENCY})
    void throwCurrencyNotFoundExceptionWhenInvalidCurrencyPassed(String invalidCurrency) {

        Assertions.assertThrows(
                CurrencyNotFoundException.class,
                () -> currencyRateService.compareCurrencies(invalidCurrency)
        );
    }

    @Test
    void testCompareCurrenciesWhenTodayAndYesterdayRatesEquals() {
        currentRates.getRates().put(TESTED_CURRENCY, TESTED_RATE);
        Assertions.assertTrue(isGifSizeGreaterThanZero());
    }

    @Test
    void testCompareCurrenciesWhenTodayRateGreater() {
        Assertions.assertTrue(isGifSizeGreaterThanZero());
    }

    @Test
    void testCompareCurrenciesWhenYesterdayRateGreater() {
        currentRates.getRates().put(TESTED_CURRENCY, TESTED_RATE);
        yesterdayRates.getRates().put(TESTED_CURRENCY, TESTED_RATE + 10);
        Assertions.assertTrue(isGifSizeGreaterThanZero());
    }

    private boolean isGifSizeGreaterThanZero() {
        return currencyRateService.compareCurrencies(TESTED_CURRENCY).getBody().length > 0;
    }
}
