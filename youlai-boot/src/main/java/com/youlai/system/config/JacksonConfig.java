package com.youlai.system.config;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

/**
 * @author liuph
 */
@Configuration
public class JacksonConfig {

    @Value("${spring.jackson.date-format:yyyy-MM-dd HH:mm:ss}")
    private String pattern;

/*    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json()
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS) // 禁用日期时间戳
                .modules(new JavaTimeModule()) // 注册Java 8日期时间模块
                .build();

        // 设置全局的日期格式
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        return objectMapper;
    }*/

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilder() {

        return builder -> {
            TimeZone tz = TimeZone.getTimeZone("GMT+8");
            DateFormat df = new SimpleDateFormat(pattern);
            df.setTimeZone(tz);
            builder.failOnEmptyBeans(false).failOnUnknownProperties(false).featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).dateFormat(df);
        };
    }

    @Bean
    public LocalDateTimeSerializer localDateTimeDeserializer() {
        return new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(pattern));
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> builder.serializerByType(LocalDateTime.class, localDateTimeDeserializer());
    }
}
