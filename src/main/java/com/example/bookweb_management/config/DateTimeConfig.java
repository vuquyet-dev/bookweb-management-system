package com.example.bookweb_management.config;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;

@Configuration
public class DateTimeConfig {

    public static final String DATETIME_FORMAT = "dd-MM-yyyy HH:mm:ss";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATETIME_FORMAT);

    @Bean
    public LocalDateTimeSerializer localDateTimeSerializer()//format cho JSON Response
    {
        return new LocalDateTimeSerializer(FORMATTER);
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer(LocalDateTimeSerializer localDateTimeSerializer) // apply format cho tất cả JSON
    {
        return builder -> builder.serializers(localDateTimeSerializer);
    }
}
