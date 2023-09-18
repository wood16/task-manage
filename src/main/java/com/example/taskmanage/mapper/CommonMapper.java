package com.example.taskmanage.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommonMapper {

    @Autowired
    private ModelMapper modelMapper;

    public <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {

        return source.stream()
                .map(element -> modelMapper.map(element, targetClass))
                .toList();
    }
}
