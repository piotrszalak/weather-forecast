package com.weatherforecast.weatherforecast;

import com.weatherforecast.weatherforecast.OpenWeatherMapModel.ApiResponse;
import com.weatherforecast.weatherforecast.WeatherStackModel.WeatherStackResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    @Value("${api.key.openweathermap}")
    private String apiKeyOpenWeatherMap;
    @Value("${api.key.weatherstack}")
    private String apiKeyWeatherStack;
    private RestTemplate restTemplate = new RestTemplate();


    public WeatherStackResponse getCurrentForecastFromWeatherStack(String cityName) {
        return restTemplate.getForObject("http://api.weatherstack.com/current?access_key="
                        + apiKeyWeatherStack
                        + "&query="
                        + cityName
                , WeatherStackResponse.class);
    }

    public ApiResponse getCurrentForecastFromOpenWeatherMap(String cityName) {
        return restTemplate.getForObject("http://api.openweathermap.org/data/2.5/weather?q="
                        + cityName
                        + "&appid="
                        + apiKeyOpenWeatherMap
                , ApiResponse.class);
    }

    public ApiResponse getFutureForecastFromOpenWeatherMap(String cityName, int days) {
        return restTemplate.getForObject("http://api.openweathermap.org/data/2.5/forecast/daily?q="
                        + cityName
                        + "&cnt="
                        + days
                        + "&appid="
                        + apiKeyOpenWeatherMap
                , ApiResponse.class);
    }
}
