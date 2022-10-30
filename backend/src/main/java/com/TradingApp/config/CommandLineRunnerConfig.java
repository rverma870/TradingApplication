package com.TradingApp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.TradingApp.service.DataLoader;

@Configuration
public class CommandLineRunnerConfig {

    @Autowired
    DataLoader dataLoader;

    @Bean
    public CommandLineRunner CommandLineRunnerBean() {

        return (args) -> {
            dataLoader.publishLivePrices();
        };
    }
}
