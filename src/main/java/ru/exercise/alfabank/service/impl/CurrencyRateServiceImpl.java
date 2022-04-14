package ru.exercise.alfabank.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.exercise.alfabank.client.CurrencyClient;
import ru.exercise.alfabank.client.GifClient;
import ru.exercise.alfabank.exception.CurrencyNotFoundException;
import ru.exercise.alfabank.model.Rates;
import ru.exercise.alfabank.service.CurrencyRateService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Map;

@Service
public class CurrencyRateServiceImpl implements CurrencyRateService {

    @Value("${application.id}")
    private String applicationId;

    @Value("${giphy.api.key}")
    private String gifApiKey;

    @Value("${rich.query}")
    private String richQuery;

    @Value("${broke.query}")
    private String brokeQuery;

    private final CurrencyClient currencyClient;
    private final GifClient gifClient;

    public CurrencyRateServiceImpl(CurrencyClient currencyClient, GifClient gifClient) {

        this.currencyClient = currencyClient;
        this.gifClient = gifClient;
    }

    @Override
    public ResponseEntity<byte[]> compareCurrencies(String comparedCurrency) {

        Rates currentRates = currencyClient.getCurrentCurrencyRates(applicationId, comparedCurrency);
        Rates yesterdayRates = currencyClient.getYesterdayCurrencyRates(
                getYesterdayDate(),
                applicationId,
                comparedCurrency
        );

        checkComparedCurrencyExisting(comparedCurrency, currentRates.getRates());

        double delta = currentRates.getRates().get(comparedCurrency) - yesterdayRates.getRates().get(comparedCurrency);
        String gifUrl = getRandomGifUrl(delta);
        return getGif(gifUrl);
    }

    ResponseEntity<byte[]> getGif(String url) {

        // Смотри https://developers.giphy.com/docs/optional-settings/#rendition-guide
        // Для получения картинки в бинарном файле в заголовке Accept необходимо указать формат данных, отличный от значения по умолчанию
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.IMAGE_GIF));
        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);
        return restTemplate.exchange(url, HttpMethod.GET, httpEntity, byte[].class);
    }

    private String getRandomGifUrl(double delta) {

        String query = getSearchQuery(delta);
        final String gifVersion = "original";
        return gifClient
                .getGif(gifApiKey, query)
                .getRandomGif()
                .getImages()
                .get(gifVersion)
                .getUrl();
    }

    private String getSearchQuery(double delta) {

        if (delta > 0) {
            return richQuery;
        }
        return brokeQuery;
    }

    private void checkComparedCurrencyExisting(String comparedCurrency, Map<String, Double> rates) {

        if (!rates.containsKey(comparedCurrency)) {
            throw new CurrencyNotFoundException();
        }
    }

    private String getYesterdayDate() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return yesterday.format(formatter);
    }
}
