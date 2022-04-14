package ru.exercise.alfabank.model;

import java.util.Map;

public class Rates {

    private String base;
    private Map<String, Double> rates;

    public Rates() {
    }

    public Rates(String base, Map<String, Double> rates) {
        this.base = base;
        this.rates = rates;
    }

    public String getBase() {
        return base;
    }

    public Map<String, Double> getRates() {
        return rates;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public void setRates(Map<String, Double> rates) {
        this.rates = rates;
    }

    @Override
    public String toString() {
        return "Rates{" +
                "base='" + base + '\'' +
                ", rates=" + rates +
                '}';
    }
}
