package com.oitm.mongo.practice;

import com.oitm.mongo.practice.entity.Article;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Description: 测试类
 * @Author: song_shu_ran
 * @Date: 2019-03-15 15:28
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoDBTest {

    @Autowired
    MongoTemplate mongoTemplate;

    @Test
    public void testInsert() {
        for (int i = 0; i < 10; i++) {
            Article article = new Article()
                    .setAuthor("Oitm"+i)
                    .setUrl("url"+i)
                    .setTags(Arrays.asList("java","mongo"))
                    .setVisitCount(0L)
                    .setAddTime(new Date());
            mongoTemplate.save(article);
        }

        List<Article> all = mongoTemplate.findAll(Article.class);
        System.out.println(all);
    }

    @Test
    public void testSelect(){
        List<Article> all = mongoTemplate.findAll(Article.class);
        System.out.println(all);


        Query query = Query.query(Criteria.where("author").is("Oitm6"));
        Article article = mongoTemplate.findOne(query, Article.class);
        System.out.println(article);
//        5c8b561b6beac33583048e35

        //根据id查询
        Article byId = mongoTemplate.findById("5c8b561b6beac33583048e35", Article.class);
        System.out.println("byId"+byId);

    }

    @Test
    public void testRegex(){
        Query query = Query.query(Criteria.where("author").regex("mo"));
        List<Article> articles = mongoTemplate.find(query, Article.class);

        System.out.println(articles.size());

    }

    @Test
    public void testAddList(){
        List<Article> articles = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Article article = new Article()
                    .setAuthor("mo"+i)
                    .setUrl("url"+i)
                    .setTags(Arrays.asList("java","mongo"))
                    .setVisitCount(0L)
                    .setAddTime(new Date());
            articles.add(article);
        }
        mongoTemplate.insert(articles,Article.class);
        List<Article> all = mongoTemplate.findAll(Article.class);
        System.out.println(all.size());
//        assert all.size()==20;
//        System.out.println(all);
    }

    @Test
    public void testDelete(){
        Query query = Query.query(Criteria.where("author").is("mo0"));
        mongoTemplate.remove(query,Article.class);

        List<Article> all = mongoTemplate.findAll(Article.class);
        System.out.println(all.size());


        Query query2 = Query.query(Criteria.where("author").is("mo1"));
        mongoTemplate.remove(query2,"article_info");
        List<Article> all2 = mongoTemplate.findAll(Article.class);
        System.out.println(all2.size());

//        mongoTemplate.dropCollection(Article.class);
//        mongoTemplate.dropCollection("article_info");
    }

    @Test
    public void testUpdate(){
        Query query = Query.query(Criteria.where("author").is("Oitm0"));
        Update update = Update.update("title", "test update").set("visitCount", 10);
        mongoTemplate.updateFirst(query,update,Article.class);

        List<Article> articles = mongoTemplate.find(query, Article.class);
        System.out.println(articles);

        //修改所有符合条件的
//        mongoTemplate.updateMulti(query, update, Article.class);
    }


}
