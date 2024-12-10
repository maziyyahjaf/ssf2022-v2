package com.example.ssf2022.ssf2022_v2.service;

import java.io.StringReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.ssf2022.ssf2022_v2.constant.Constant;
import com.example.ssf2022.ssf2022_v2.model.Article;
import com.example.ssf2022.ssf2022_v2.model.ArticleDTO;
import com.example.ssf2022.ssf2022_v2.repo.MapRepo;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class NewsService {

    RestTemplate restTemplate = new RestTemplate();

    @Value("${name.service.url}")
    private String articlesServiceUrl;

    @Value("${api.key}")
    private String apiKey;

    private final MapRepo mapRepo;

    public NewsService(MapRepo mapRepo) {
        this.mapRepo = mapRepo;
    }

    public List<Article> getArticles() {

        List<Article> articles = new ArrayList<>();

        // Create the HttpHeaders object
        HttpHeaders headers = new HttpHeaders();
        headers.set("authorization", "Apikey " + apiKey);

        // build the query to call the API endpoint
        RequestEntity<Void> req = RequestEntity
                .get(URI.create(articlesServiceUrl))
                .headers(headers)
                .build();

        ResponseEntity<String> response = restTemplate.exchange(req, String.class);
        String responseBody = response.getBody();

        // parse the json response and convert to a Article object
        JsonReader jsonReader = Json.createReader(new StringReader(responseBody));
        JsonObject root = jsonReader.readObject();
        JsonArray data = root.getJsonArray("Data");

        // need to iterate over all the JsonObjects in the list
        for (JsonObject dataObj : data.getValuesAs(JsonObject.class)) {

            Article article = toArticle(dataObj);
            articles.add(article);
        }

        return articles;

    }

    public Article getArticleById(String articleId) {
        List<Article> articles = getArticles();

        Optional<Article> article = articles.stream()
                                    .filter(a -> a.getId().equals(articleId))
                                    .findFirst();
        if (article == null) {
            // need to fix this
            return null;
        }

        return article.get();
    }

    public void saveArticles(Article article) {
        JsonObject articleJson = toJsonObject(article);
        mapRepo.add(Constant.rediskey, article.getId(), articleJson.toString());

    }

    public Optional<Article> getSavedArticleById(String articleId) {
        Object savedArticle = mapRepo.getSavedArticleById(Constant.rediskey, articleId);

        if (savedArticle == null) {
            return Optional.empty();
        }

        String savedArticleString = savedArticle.toString();
        JsonReader jsonReader = Json.createReader(new StringReader(savedArticleString));
        JsonObject jsonObject = jsonReader.readObject();
        Article article = toArticle(jsonObject);

        return Optional.of(article);
    }

    public List<ArticleDTO> getArticleDTOs() {
        
        List<Article> articles = getArticles();
        List<ArticleDTO> articleDTOs = articles.stream()
                                                .map(this::toArticleDTO)
                                                .collect(Collectors.toList());
        return articleDTOs;
    }

    public ArticleDTO toArticleDTO(Article article) {

        String id = article.getId();
        Long publishedOnLong = article.getPublished_on() * 1000L;
        Date publishedDate = new Date(publishedOnLong);
        String title = article.getTitle();
        String url = article.getUrl();
        String imageurl = article.getImageurl();
        String body = article.getBody();
        String tags = article.getTags();
        String categories = article.getCategories();

        ArticleDTO aDto = new ArticleDTO(id, publishedDate, title, url, imageurl, body, tags, categories);
        return aDto;
    }


    public JsonObject toJsonObject(Article article) {

        JsonObject jsonObject = Json.createObjectBuilder()
                                    .add("id", article.getId())
                                    .add("published_on", article.getPublished_on())
                                    .add("title", article.getTitle())
                                    .add("url", article.getUrl())
                                    .add("imageurl", article.getImageurl())
                                    .add("body", article.getBody())
                                    .add("tags", article.getTags())
                                    .add("categories", article.getCategories())
                                    .build();
        return jsonObject;
    }

    public Article toArticle(JsonObject jsonObject) {

        String id = jsonObject.getString("id", "defaultId");
        Long published_on = jsonObject.getJsonNumber("published_on").longValue();
        String title = jsonObject.getString("title");
        String url = jsonObject.getString("url");
        String imageurl = jsonObject.getString("imageurl");
        String body = jsonObject.getString("body");
        String tags = jsonObject.getString("tags");
        String categories = jsonObject.getString("categories");

        Article article = new Article(id, published_on, title, url, imageurl, body, tags, categories);

        return article;
    }

}
