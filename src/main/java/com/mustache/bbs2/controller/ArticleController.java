package com.mustache.bbs2.controller;

import com.mustache.bbs2.domain.Article;
import com.mustache.bbs2.domain.dto.ArticleDto;
import com.mustache.bbs2.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/articles")
@Slf4j // 로거 추가
public class ArticleController {

    private final ArticleRepository articleRepository;

    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping("/list")//findall한 결과를 list로 넘김
    public String list(Model model) {
        List<Article> articles = articleRepository.findAll();
        model.addAttribute("articles", articles);
        return "list";
    }

    @GetMapping("")
    public String index() {
//        return "index";
        return "redirect:/articles/list";
    }

    @GetMapping("/new")
    public String createPage() {
        return "new";
    }

    @GetMapping("/{id}")
    public String selectSingle(@PathVariable Long id, Model model) {
        Optional<Article> optArticle = articleRepository.findById(id);
        if (!optArticle.isEmpty()) {
            //Optional.get() --- > Article
            model.addAttribute("article", optArticle.get());
            return "show";
        } else {
            return "error";
        }
    }

    @GetMapping("/{id}edit")
    public String edit(@PathVariable Long id, Model model) {
        Optional<Article> optionalArticle = articleRepository.findById(id);
        if (!optionalArticle.isEmpty()) {
            model.addAttribute("article", optionalArticle.get());
            return "edit";
        } else {
            model.addAttribute("message", String.format("%d가 없습니다.", id));
            return "error";
        }
    }

    @PostMapping("") // /articles
    public String articles(ArticleDto articleDto) {
        log.info(articleDto.getTitle());
        Article savedArticle = articleRepository.save(articleDto.toEntity());
        log.info("generatedId:{}", savedArticle.getId());

        return String.format("redirect:/articles/%d", savedArticle.getId());
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable Long id, ArticleDto articleDto,Model model) {
        log.info("title:{} content{}", articleDto.getTitle(), articleDto.getContent());
        Article article = articleRepository.save(articleDto.toEntity());
        model.addAttribute("article", article);
        return String.format("redirect:/articles/%d",article.getId());
    }
}