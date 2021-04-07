package com.bankaccount.api.config;

import com.bankaccount.api.dao.AccountDao;
import com.bankaccount.api.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public Mapper mapper() {
        return new Mapper();
    }

}
