package com.oitm.mongo.practice;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.oitm.mongo.practice.constants.MongoConstants;
import com.oitm.mongo.practice.entity.Article;
import org.bson.Document;
import org.bson.types.ObjectId;
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
     * private String title;
     * private String url;
     * private String author;
     * private List<String> tags;
     * private Long visitCount;
     * private Date ctime;
     * private Date mtime;
     */

    public static final String ARTICLE_CLASS = "com.oitm.mongo.practice.entity.Article";

    @Autowired
    private MongoCollection<Document> articleCollection;

    @Test
    public void testInsert() {
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
    public void testQuery() {
        // 查询所有
        FindIterable<Document> documents = articleCollection.find();
        try (MongoCursor<Document> cursor = documents.iterator()) {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                System.out.println(document);
            }
        }


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
        ArrayList titles = new ArrayList<>();
        titles.add("Oitm1");
        titles.add("Oitm2");
        try (MongoCursor<Document> cursor = articleCollection.find().filter(in(Article.TITLE, titles)).iterator()) {
            while (cursor.hasNext()) {
                Document document4 = cursor.next();
                System.out.println(document4);
            }
        }


        //OR查询
        try (MongoCursor<Document> cursor = articleCollection.find().filter(or(eq(Article.TITLE, "Oitm1"), eq(Article.TITLE, "Oitm2"))).iterator()) {
            while (cursor.hasNext()) {
                Document document5 = cursor.next();
                System.out.println(document5);
            }
        }


        // sort
        try (MongoCursor<Document> iterator = articleCollection.find().sort(new Document(Article.TITLE, -1)).iterator()) {
            while (iterator.hasNext()) {
                Document document8 = iterator.next();
                System.out.println(document8);
            }
        }


        //分页
        try (MongoCursor<Document> iterator = articleCollection.find().skip(3).limit(2).iterator()){
            while (iterator.hasNext()) {
                Document document9 = iterator.next();
                System.out.println(document9);
            }
        }

        //关联查询   此处只是举例子
//        Document document6 = new Document("fieldName", new DBRef("collection", "id"));
//        Document document7 = articleCollection.find(document6).first();
//        System.out.println(document7);

    }


    @Test
    public void testUpdate() {
        //更新对应id的article的title内容, 注意 `MongoConstants.MONGO_SET` 设置
//        String articleID = "5c9ddd2f3d428cc67aed0598";
//        UpdateResult update = articleCollection.updateOne(eq(MongoConstants.MONGO_ID, new ObjectId(articleID)),
//                new Document(MongoConstants.MONGO_SET, new Document(Article.TITLE, "Oitm_Driver_Update")));
//        System.out.println(update.getModifiedCount());

        // 更新多个同时都满足条件的Document
        UpdateResult updateResult = articleCollection.updateMany(eq(Article.TITLE, "Oitm_Update_Third"),
                new Document(MongoConstants.MONGO_SET, new Document(Article.TITLE, "Oitm_Driver_Update_Third")));
        System.out.println(updateResult.getModifiedCount());

    }

    @Test
    public void testDelete() {
        //根据id进行删除
//        String articleID = "5c9f1782678228f290b64691";
//        DeleteResult deleteResult = articleCollection.deleteOne(eq(MongoConstants.MONGO_ID, new ObjectId(articleID)));
//        System.out.println(deleteResult.getDeletedCount());

        //删除所有满足条件的Document
        DeleteResult result = articleCollection.deleteMany(eq(Article.TITLE, "Oitm"));
        System.out.println(result.getDeletedCount());

    }

}
