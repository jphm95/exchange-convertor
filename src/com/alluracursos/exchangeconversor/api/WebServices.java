package com.alluracursos.exchangeconversor.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;


public class WebServices {

    private final Gson gson;
    private final HttpClient client;

    public WebServices() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.client = HttpClient.newHttpClient();
    }


    public RatesResponse fetchRates(String base) throws IOException, InterruptedException {
        String b = base.toUpperCase();
        String apiKey = System.getenv("API_KEY");
        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalStateException("API_KEY environment variable not set");
        }
        String url = "https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/" + b;
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String json = response.body();
        //System.out.println(json); // Uncomment for debugging
        return gson.fromJson(json, RatesResponse.class);
    }


    public BigDecimal convert(BigDecimal amount, String from, String to)
            throws IOException, InterruptedException {
        if (from.equalsIgnoreCase(to)) return amount.setScale(2, RoundingMode.HALF_UP);
        RatesResponse rates = fetchRates(from);
        if (rates == null || rates.conversion_rates == null) {
            throw new IllegalStateException("No se pudieron obtener las tasas de cambio");
        }
        Double rate = rates.conversion_rates.get(to.toUpperCase());
        if (rate == null) {
            throw new IllegalArgumentException("Código de moneda desconocido: " + to);
        }
        return amount.multiply(BigDecimal.valueOf(rate)).setScale(2, RoundingMode.HALF_UP);
    }

    public Gson getGson() {
        return gson;
    }


    public static class RatesResponse {
        public String base_code;
        public Map<String, Double> conversion_rates;

        @Override
        public String toString() {
            return "RatesResponse{" +
                    "base_code='" + base_code + '\'' +
                    ", conversion_rates=" + (conversion_rates != null ? conversion_rates.size() + " currencies" : null) +
                    '}';
        }
    }
}