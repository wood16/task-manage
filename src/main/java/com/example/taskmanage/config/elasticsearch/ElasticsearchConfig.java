package com.example.taskmanage.config.elasticsearch;

import com.example.taskmanage.elasticsearch.convert.LongToLocalDateTimeConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchCustomConversions;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.example.taskmanage.elasticsearch.elasticrepository")
@EnableJpaRepositories(basePackages = "com.example.taskmanage.repository")
public class ElasticsearchConfig extends ElasticsearchConfiguration {

    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo("localhost:9200")
                .build();
    }

//    convert date config
//    can use both read and write in list.of
    @Bean
    public ElasticsearchCustomConversions elasticsearchCustomConversions() {
        return new ElasticsearchCustomConversions(List.of(
                new LongToLocalDateTimeConverter()
        ));
    }

//    muon su dung ca JPA va Elasticsearch repo can chi ra basePackage

}