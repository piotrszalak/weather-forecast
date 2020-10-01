package com.weatherforecast.weatherforecast;


import com.weatherforecast.weatherforecast.OpenWeatherMapModel.ApiResponse;
import com.weatherforecast.weatherforecast.WeatherStackModel.WeatherStackResponse;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class WeatherController {

    private WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/getForecasts/{cityName}")
    public ResponseEntity<?> getAllForecasts(@PathVariable String cityName) {
        Map<ForecastProvider, Object> forecastsMap = new HashMap<>();

        forecastsMap.put(ForecastProvider.WeatherStack, weatherService.getCurrentForecastFromWeatherStack(cityName));
        forecastsMap.put(ForecastProvider.OpenWeatherMap, weatherService.getCurrentForecastFromOpenWeatherMap(cityName));

        return new ResponseEntity<>(forecastsMap, HttpStatus.OK);
    }

    @GetMapping("/getFromSpecifiedProvider/{forecastProvider}/{cityName}")
    public ResponseEntity<?> getForecast(@PathVariable ForecastProvider forecastProvider, @PathVariable String cityName) {
        if (forecastProvider.equals(ForecastProvider.OpenWeatherMap)) {
            return getForecastFromOpenWeatherMap(cityName);
        } else if (forecastProvider.equals(ForecastProvider.WeatherStack)) {
            return getForecastFromWeatherStack(cityName);
        }
        return new ResponseEntity<>("Required valid city and provider", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/OpenWeatherMap/{cityName}")
    public ResponseEntity<ApiResponse> getForecastFromOpenWeatherMap(@PathVariable String cityName) {
        ApiResponse apiResponse = weatherService.getCurrentForecastFromOpenWeatherMap(cityName);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/forecastFromWeatherStack/{cityName}")
    public ResponseEntity<WeatherStackResponse> getForecastFromWeatherStack(@PathVariable String cityName) {
        WeatherStackResponse weatherStackResponse = weatherService.getCurrentForecastFromWeatherStack(cityName);
        return new ResponseEntity<>(weatherStackResponse, HttpStatus.OK);
    }

    @GetMapping("/futureForecast/{cityName}/{days}")
    public ResponseEntity<?> getFutureForecastFromOpenWeatherMap(@PathVariable String cityName, @PathVariable int days) {
        if (days <= 16) {
            return new ResponseEntity<>(weatherService.getFutureForecastFromOpenWeatherMap(cityName, days), HttpStatus.OK);
        }
        return new ResponseEntity<>("Please provide number of days within 0-16 range", HttpStatus.BAD_REQUEST);
    }
}
