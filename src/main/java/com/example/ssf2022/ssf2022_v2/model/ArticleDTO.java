package com.example.ssf2022.ssf2022_v2.model;

import java.util.Date;

public class ArticleDTO {

    private String id;
    private Date publishedDate;
    private String title;
    private String url;
    private String imageurl;
    private String body;
    private String tags;
    private String categories;


    

    public ArticleDTO() {
    }

    public ArticleDTO(String id, Date publishedDate, String title, String url, String imageurl, String body,
            String tags, String categories) {
        this.id = id;
        this.publishedDate = publishedDate;
        this.title = title;
        this.url = url;
        this.imageurl = imageurl;
        this.body = body;
        this.tags = tags;
        this.categories = categories;
    }



    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public Date getPublishedDate() {
        return publishedDate;
    }
    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getImageurl() {
        return imageurl;
    }
    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public String getTags() {
        return tags;
    }
    public void setTags(String tags) {
        this.tags = tags;
    }
    public String getCategories() {
        return categories;
    }
    public void setCategories(String categories) {
        this.categories = categories;
    }

    
    
}
