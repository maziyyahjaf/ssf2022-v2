package com.example.ssf2022.ssf2022_v2.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ssf2022.ssf2022_v2.model.Article;
import com.example.ssf2022.ssf2022_v2.model.ErrorResponse;
import com.example.ssf2022.ssf2022_v2.service.NewsService;

@RestController
@RequestMapping("/news")
public class NewsRestController {


    private final NewsService newsService;

    public NewsRestController(NewsService newsService) {
        this.newsService = newsService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getSavedArticleById(@PathVariable("id") String articleId) {
        
        Optional<Article> foundArticle = newsService.getSavedArticleById(articleId);

        if (foundArticle.isEmpty()) {
            ErrorResponse errorMessage = new ErrorResponse("error", 
                                        "Cannot find news article " + articleId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }

        return ResponseEntity.status(HttpStatus.OK).body(foundArticle.get());
       

    }
    
}
