package com.mustache.bbs2.domain.dto;

import com.mustache.bbs2.domain.Article;
import lombok.Getter;

@Getter
public class ArticleDto {
    private  String title;
    private String content;

    public ArticleDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Article toEntity() {
        return new Article(this.title,this.content);
    }
}

