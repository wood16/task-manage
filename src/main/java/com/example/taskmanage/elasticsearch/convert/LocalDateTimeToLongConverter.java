package com.example.taskmanage.elasticsearch.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

import java.time.LocalDateTime;
import java.time.ZoneId;

@WritingConverter
public class LocalDateTimeToLongConverter implements Converter<LocalDateTime, Long> {
    @Override
    public Long convert(LocalDateTime source) {
        return source.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
}
