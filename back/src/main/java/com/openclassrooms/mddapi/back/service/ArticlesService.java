package com.openclassrooms.mddapi.back.service;

import com.openclassrooms.mddapi.back.dto.ArticlesDto;
import com.openclassrooms.mddapi.back.model.Articles;
import com.openclassrooms.mddapi.back.model.Topics;
import com.openclassrooms.mddapi.back.model.Users;
import com.openclassrooms.mddapi.back.repository.ArticlesRepository;
import com.openclassrooms.mddapi.back.repository.TopicsRepository;
import com.openclassrooms.mddapi.back.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ArticlesService {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    ArticlesRepository articlesRepository;

    @Autowired
    TopicsRepository topicsRepository;

    @Autowired
    CommentsService commentsService;

    /**
     * Creates a new article.
     *
     * @param articleDto the article data transfer object containing article details.
     * @return the created article as a DTO, or null if validation or persistence fails.
     */
    public ArticlesDto createArticle(ArticlesDto articleDto) {
        // Validate the input DTO
        if (articleDto.getTitle() == null || articleDto.getContents() == null || articleDto.getIdTopic() == 0) {
            return null;
        }
        try {
            // Check if the topic exists
            Optional<Topics> topicOptional = topicsRepository.findById(articleDto.getIdTopic());
            if (topicOptional.isEmpty()) {
                return null;
            }
            // Create and populate a new article entity
            Topics topic = topicOptional.get();
            Articles article = new Articles();
            article.setTitle(articleDto.getTitle());
            article.setContents(articleDto.getContents());
            article.setTopics(topic);
            article.setDate(new Date());

            // Set the user from the security context
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            Users user = usersRepository.findByEmail(email).get();
            article.setUser(user);

            // Save the article and return its DTO representation
            return convertArticlesToArticlesDto(articlesRepository.save(article));
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * Converts an article entity to a DTO.
     *
     * @param articles the article entity to convert.
     * @return the converted article DTO.
     */
    private ArticlesDto convertArticlesToArticlesDto(Articles articles) {
        ArticlesDto articlesDto = new ArticlesDto();
        articlesDto.setId(articles.getId());
        articlesDto.setTitle(articles.getTitle());
        articlesDto.setContents(articles.getContents());
        articlesDto.setUsername(articles.getUser().getPseudo());
        articlesDto.setIdTopic(articles.getTopics().getId());
        articlesDto.setLabelTopic(articles.getTopics().getLabel());
        articlesDto.setDate(articles.getDate());
        articlesDto.setCommentsList(commentsService.convertListCommentsToListCommentsDto(articles.getCommentsList(),
                articles.getId()));

        return articlesDto;
    }

    /**
     * Deletes an article by its ID.
     *
     * @param idArticle the ID of the article to delete.
     */
    public void delete(int idArticle) {
        articlesRepository.deleteById(idArticle);
    }

    /**
     * Retrieves all articles.
     *
     * @return a list of all article DTOs sorted by date in descending order.
     */
    public List<ArticlesDto> getAllArticles() {
        List<ArticlesDto> allArticlesList = new ArrayList<>();
        List<Articles> articles = articlesRepository.findAll();
        for (Articles article : articles) {
            allArticlesList.add(convertArticlesToArticlesDto(article));
        }
        //Collections.reverse(allArticlesList);
        allArticlesList.sort(Comparator.comparing(ArticlesDto::getDate).reversed());
        return allArticlesList;
    }

    /**
     * Retrieves an article by its ID.
     *
     * @param idArticle the ID of the article to retrieve.
     * @return the article DTO, or null if not found.
     */
    public ArticlesDto getArticleById(int idArticle) {
        Optional<Articles> articleOptional = articlesRepository.findById(idArticle);
        if (articleOptional.isEmpty()) {
            return null;
        }
        return convertArticlesToArticlesDto(articleOptional.get());
    }

    /**
     * Retrieves articles by the ID of their topic.
     *
     * @param idTopic the ID of the topic.
     * @return a list of article DTOs for the given topic sorted by date in descending order.
     */
    public List<ArticlesDto> getArticlesByIdTopic(int idTopic) {
        List<Articles> articlesList = articlesRepository.getArticlesByTopicsId(idTopic);
        List<ArticlesDto> allArticlesList = new ArrayList<>();
        if (articlesList == null) {
            return null;
        }
        for (Articles article : articlesList) {
            allArticlesList.add(convertArticlesToArticlesDto(article));
        }
        allArticlesList.sort(Comparator.comparing(ArticlesDto::getDate).reversed());
        return allArticlesList;
    }

    /**
     * Retrieves all articles for the given topic DTO.
     *
     * @param topicsList the topic DTO containing an array of topic IDs.
     * @return a list of all article DTOs for the given topics sorted by date in descending order.
     */
    public List<ArticlesDto> getAllArticlesByTopics(List<Topics> topicsList) {
        if (topicsList.isEmpty()) {
            return null;
        }
        List<ArticlesDto> articlesDtoList = new ArrayList<>();
        for (Topics topics : topicsList) {
            articlesDtoList.addAll(getArticlesByIdTopic(topics.getId()));
        }
        articlesDtoList.sort(Comparator.comparing(ArticlesDto::getDate).reversed());
        return articlesDtoList;
    }
}
