package com.example.demo;

import com.example.demo.jpa.Article;
import com.example.demo.jpa.ArticleRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
@Slf4j
public class BBSController {
    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping("/")
    public String showArticles(@AuthenticationPrincipal OAuth2User oauth2User, Model model) {
        // Spring Boot Auto-configuration によってOAuth2User は自動でインジェクトされる
        List<Article> articles = articleRepository.findAll();
        model.addAttribute("userName", oauth2User.getAttribute("name"));
        model.addAttribute("articles", articles);
        model.addAttribute("articleForm", new ArticleForm());
        return "index";
    }

    @PostMapping("/post")
    public String postArticle(@AuthenticationPrincipal OAuth2User oauth2User, @ModelAttribute ArticleForm articleForm) {
        Article article = new Article();
        article.setName(oauth2User.getAttribute("name"));
        article.setEntry(articleForm.getEntry());
        article.setDate(LocalDate.now());
        articleRepository.save(article);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ArticleForm {
        private Integer id;
        private String name;
        private String entry;
        private LocalDate date;
    }
}
