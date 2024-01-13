package com.example.taskmanage.config.elasticsearch;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.example.taskmanage.elasticrepository")
@EnableJpaRepositories(basePackages = "com.example.taskmanage.repository")
public class ElasticsearchConfig extends ElasticsearchConfiguration {

    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo("localhost:9200")
                .build();
    }

//    muon su dung ca JPA va Elasticsearch repo can chi ra basePackage

}