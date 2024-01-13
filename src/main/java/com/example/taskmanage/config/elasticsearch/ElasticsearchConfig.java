package com.example.taskmanage.config.elasticsearch;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@Configuration
//@EnableElasticsearchRepositories(basePackages = "com.example.taskmanage.elarepository")
//@EnableJpaRepositories(basePackages = "com.example.taskmanage.repository")
public class ElasticsearchConfig {
//        extends AbstractElasticsearchConfiguration {
//
//    @Bean
//    @Override
//    public RestHighLevelClient elasticsearchClient() {
//        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
//                .connectedTo("localhost:9200")
//                .build();
//
//        return RestClients.create(clientConfiguration).rest();
//    }
}