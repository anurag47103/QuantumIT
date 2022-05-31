package com.learningandroid.quantumit.model;

public class News {
    private String title;
    private String description;
    private String source;
    private String url;
    private String urlImage;
    private String publishedAt;
    private String content;

    public News(String title, String description, String source, String url, String urlImage, String publishedAt, String content) {
        this.title = title;
        this.description = description;
        this.source = source;
        this.url = url;
        this.urlImage = urlImage;
        this.publishedAt = publishedAt;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}