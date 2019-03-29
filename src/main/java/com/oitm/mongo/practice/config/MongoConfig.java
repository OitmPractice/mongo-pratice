package com.oitm.mongo.practice.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.oitm.mongo.practice.entity.Article;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoConfig {

    @Value("${mongodb.url}")
    public String mongoUrl;
    @Value("${mongodb.database}")
    public String mongoDatabase;

    @Bean
    public MongoClient mongoClient() {
        return new MongoClient();
    }

    // 远程配置方法
//    @Bean
//    public MongoClient mongoClient() {
//        MongoClientURI uri = new MongoClientURI(mongoUrl);
//        return new MongoClient(uri);
//    }

    @Bean
    public MongoDatabase mongoDatabase(MongoClient mongoClient) {
        return mongoClient.getDatabase(mongoDatabase);
    }

    @Bean
    public MongoCollection<Document> articleCollection(MongoDatabase d) {
        return d.getCollection(Article.ARTICLE_COLLECTION);
    }
}
