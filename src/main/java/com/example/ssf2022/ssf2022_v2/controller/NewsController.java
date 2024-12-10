package com.example.ssf2022.ssf2022_v2.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.ssf2022.ssf2022_v2.model.Article;
import com.example.ssf2022.ssf2022_v2.model.ArticleDTO;
import com.example.ssf2022.ssf2022_v2.service.NewsService;

@Controller
@RequestMapping("/articles")
public class NewsController {

    private final NewsService newsService;
    

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }


    @GetMapping("") 
    public String getArticles(Model model) {

        List<ArticleDTO> articles = newsService.getArticleDTOs();
        model.addAttribute("articles", articles);

        return "articles";
        
    }

    @PostMapping("")
    public String saveArticles(@RequestParam(name = "saveArticleId", required = false) List<String> articleIds) {

        for (String id : articleIds) {
            Article article = newsService.getArticleById(id);
            newsService.saveArticles(article);
        }


        return "redirect:/articles";
    }
    
}
