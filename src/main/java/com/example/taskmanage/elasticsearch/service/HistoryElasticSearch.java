package com.example.taskmanage.elasticsearch.service;

import com.example.taskmanage.elasticsearch.elasticrepository.HistoryElasticRepository;
import com.example.taskmanage.entity.HistoryEntity;
import com.example.taskmanage.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryElasticSearch {

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private HistoryElasticRepository historyElasticRepository;

    public void reindexAllHistory(){

        historyElasticRepository.deleteAll();

        List<HistoryEntity> list = historyRepository.findAll();
        list.forEach(historyElasticRepository::save);
    }
}
