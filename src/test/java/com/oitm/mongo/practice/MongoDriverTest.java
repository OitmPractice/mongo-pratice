package com.oitm.mongo.practice;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.oitm.mongo.practice.entity.Article;
import org.bson.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;

import static com.mongodb.client.model.Filters.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoDriverTest {

    /**
     *     private String title;
     *     private String url;
     *     private String author;
     *     private List<String> tags;
     *     private Long visitCount;
     *     private Date ctime;
     *     private Date mtime;
     */

    public static final String ARTICLE_CLASS = "com.oitm.mongo.practice.entity.Article";

    @Autowired
    private MongoCollection<Document> articleCollection;

    @Test
    public void testInsert(){
        ArrayList<String> tags = new ArrayList<>();
        tags.add("java");
        tags.add("mongo");
        Document document = new Document(Article.TITLE, "Oitm")
                .append(Article.URL, "test_url")
                .append(Article.AUTHOR, "oitm")
                .append(Article.TAGS, tags)
                .append(Article.VISIT_COUNT, 10000)
                .append(Article.CTIME, new Date())
                .append(Article.MTIME, new Date());
        articleCollection.insertOne(document);
    }

    @Test
    public void testQuery(){
        // 查询所有
//        FindIterable<Document> documents = articleCollection.find();
//        MongoCursor<Document> cursor = documents.iterator();
//        while (cursor.hasNext()){
//            Document document = cursor.next();
//            System.out.println(document);
//        }

        //根据_id查询
        String id = "5c9ddd2f3d428cc67aed0598";
//        Document document = articleCollection.find(eq(MongoConstants.MONGO_ID, new ObjectId(id))).first();
//        System.out.println(document);
//        Document document1 = articleCollection.find(new Document(MongoConstants.MONGO_ID, new ObjectId(id))).first();
//        System.out.println(document1);

        //AND查询
        // 先创建  db.article.insert({"title":"Oitm2","url":"test_url2","author":"oitm","tags":["java","mongo"],"visit_count":10000,"ctime":Date(),"mtime":Date()})
//        Document document2 = new Document("title", "Oitm2").append("url", "test_url2");
//        Document document3 = articleCollection.find(document2).first();
//        System.out.println(document3);

        //IN查询 （先创建Oitm1、Oitm2）
//        ArrayList titles = new ArrayList<>();
//        titles.add("Oitm1");
//        titles.add("Oitm2");
//        MongoCursor<Document> cursor = articleCollection.find().filter(in(Article.TITLE, titles)).iterator();
//        while (cursor.hasNext()) {
//            Document document4 = cursor.next();
//            System.out.println(document4);
//        }

        //OR查询
        MongoCursor<Document> cursor = articleCollection.find().filter(or(eq(Article.TITLE, "Oitm1"), eq(Article.TITLE, "Oitm2"))).iterator();
        while (cursor.hasNext()) {
            Document document5 = cursor.next();
            System.out.println(document5);
        }

        //关联查询   此处只是举例子
//        Document document6 = new Document("fieldName", new DBRef("collection", "id"));
//        Document document7 = articleCollection.find(document6).first();
//        System.out.println(document7);

    }

}
