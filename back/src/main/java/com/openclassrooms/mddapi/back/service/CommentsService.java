package com.openclassrooms.mddapi.back.service;

import com.openclassrooms.mddapi.back.dto.CommentsDto;
import com.openclassrooms.mddapi.back.model.Articles;
import com.openclassrooms.mddapi.back.model.Comments;
import com.openclassrooms.mddapi.back.repository.ArticlesRepository;
import com.openclassrooms.mddapi.back.repository.CommentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommentsService {

    @Autowired
    CommentsRepository commentsRepository;

    @Autowired
    ArticlesRepository articlesRepository;

    public CommentsDto createComment(CommentsDto commentDto) {
        if(commentDto.getComments() == null || commentDto.getIdArticle() == 0) {
            return null;
        }
        Comments comment = new Comments();
        comment.setComments(commentDto.getComments());
        comment.setDate(new Date());
        commentsRepository.save(comment);
        Optional<Articles> article = articlesRepository.findById(commentDto.getIdArticle());
        if(article.isEmpty()) {
            return null;
        }
        List<Comments> commentsList = article.get().getCommentsList();
        if(commentsList == null) {
            commentsList = new ArrayList<>();
        }
        commentsList.add(comment);
        articlesRepository.save(article.get());
        return convertCommentsToCommentsDto(comment, article.get().getId());
    }

    public CommentsDto convertCommentsToCommentsDto(Comments comments, int idArticle) {
        CommentsDto commentsDto = new CommentsDto();
        commentsDto.setId(comments.getId());
        commentsDto.setDate(comments.getDate());
        commentsDto.setComments(comments.getComments());
        commentsDto.setIdArticle(idArticle);
        return commentsDto;
    }
}
