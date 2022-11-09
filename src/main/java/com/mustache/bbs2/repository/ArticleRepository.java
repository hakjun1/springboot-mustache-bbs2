package com.mustache.bbs2.repository;

import com.mustache.bbs2.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article,Long> {
}
