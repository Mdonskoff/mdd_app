package com.openclassrooms.mddapi.back.repository;

import com.openclassrooms.mddapi.back.model.Articles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticlesRepository extends JpaRepository<Articles, Integer> {

    List<Articles> getArticlesByTopicsId(int idTopic);

}
