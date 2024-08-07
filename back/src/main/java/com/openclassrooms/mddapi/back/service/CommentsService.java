package com.openclassrooms.mddapi.back.service;

import com.openclassrooms.mddapi.back.dto.CommentsDto;
import com.openclassrooms.mddapi.back.model.Articles;
import com.openclassrooms.mddapi.back.model.Comments;
import com.openclassrooms.mddapi.back.model.Users;
import com.openclassrooms.mddapi.back.repository.ArticlesRepository;
import com.openclassrooms.mddapi.back.repository.CommentsRepository;
import com.openclassrooms.mddapi.back.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    UsersRepository usersRepository;

    /**
     * Creates a new comment based on the provided CommentsDto.
     *
     * @param commentDto Data transfer object containing comment details.
     * @return CommentsDto object representing the created comment, or null if validation fails.
     */
    public CommentsDto createComment(CommentsDto commentDto) {
        if(commentDto.getComments() == null || commentDto.getIdArticle() == 0) {
            return null;
        }
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Users> user = usersRepository.findByEmail(email);

        Comments comment = new Comments();
        comment.setComments(commentDto.getComments());
        comment.setDate(new Date());
        comment.setUsers(user.get());
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

    /**
     * Converts a Comments entity to a CommentsDto.
     *
     * @param comments The Comments entity to be converted.
     * @param idArticle The ID of the article associated with the comment.
     * @return CommentsDto object containing the mapped properties.
     */
    private CommentsDto convertCommentsToCommentsDto(Comments comments, int idArticle) {
        CommentsDto commentsDto = new CommentsDto();
        commentsDto.setId(comments.getId());
        commentsDto.setDate(comments.getDate());
        commentsDto.setComments(comments.getComments());
        commentsDto.setIdArticle(idArticle);
        commentsDto.setPseudo(comments.getUsers().getPseudo());
        return commentsDto;
    }

    /**
     * Converts a list of Comments entities to a list of CommentsDto.
     *
     * @param comments The list of Comments entities to be converted.
     * @param idArticle The ID of the article associated with the comments.
     * @return List of CommentsDto objects containing the mapped properties.
     */
    public List<CommentsDto> convertListCommentsToListCommentsDto(List<Comments> comments, int idArticle) {
        List<CommentsDto> commentsDtoList = new ArrayList<>();
        for(Comments comment : comments) {
            commentsDtoList.add(convertCommentsToCommentsDto(comment, idArticle));
        }
        return commentsDtoList;
    }
}
