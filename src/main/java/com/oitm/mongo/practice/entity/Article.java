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
@Document(collection = "article")
public class Article {

    public static final String TITLE = "title";
    public static final String URL = "url";
    public static final String AUTHOR = "author";
    public static final String TAGS = "tags";
    public static final String VISIT_COUNT = "visit_count";
    public static final String CTIME = "ctime";
    public static final String MTIME = "mtime";

    public static final String ARTICLE_COLLECTION = "article";


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
    @Field("ctime")
    private Date ctime;
    @Field("mtime")
    private Date mtime;

}
