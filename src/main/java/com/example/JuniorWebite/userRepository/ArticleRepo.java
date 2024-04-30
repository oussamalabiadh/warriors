package com.example.JuniorWebite.userRepository;

import com.example.JuniorWebite.Entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface ArticleRepo extends JpaRepository<Article,Long> {


}
