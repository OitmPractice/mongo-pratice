package com.oitm.mongo.practice.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

/**
 * @Description: 文章实体类
 * @Author: song_shu_ran
 * @Date: 2019-03-15 15:25
 */


@Data
@Accessors(chain = true)
@Document(collection = "article_info")
public class Article {

    @Id
    private String id;
    @Field("title")
    private String title;
    @Field("url")
    private String url;
    @Field("author")
    private String author;
    @Field("tags")
    private List<String> tags;
    @Field("visit_count")
    private Long visitCount;
    @Field("add_time")
    private Date addTime;

}
