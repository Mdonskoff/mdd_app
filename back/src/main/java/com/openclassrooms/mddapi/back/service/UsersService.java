package com.openclassrooms.mddapi.back.service;

import com.openclassrooms.mddapi.back.dto.ArticlesDto;
import com.openclassrooms.mddapi.back.dto.UsersDto;
import com.openclassrooms.mddapi.back.model.Topics;
import com.openclassrooms.mddapi.back.model.Users;
import com.openclassrooms.mddapi.back.repository.TopicsRepository;
import com.openclassrooms.mddapi.back.repository.UsersRepository;
import com.openclassrooms.mddapi.back.utils.functionsUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UsersService {

    @Autowired
    private ArticlesService articlesService;

    @Autowired
    private AuthService authService;

    @Autowired
    private TopicsRepository topicsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private functionsUtils funUtils;

    /**
     * Updates the current user's details.
     *
     * @param userDto  the user DTO containing the new details.
     * @param password the new password to set, if not null.
     * @return a string message or a new JWT token if the update is successful.
     */
    public String updateUser(UsersDto userDto, String password) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Users> userOptional = usersRepository.findByEmail(email);
        Users user = userOptional.get();
        if (!user.getPseudo().equals(userDto.getPseudo())) {
            Optional<Users> checkUser = usersRepository.findByPseudo(userDto.getPseudo());
            if (checkUser.isPresent()) {
                return "Username already exist";
            }
            user.setPseudo(userDto.getPseudo());
        }
        if (!user.getEmail().equals(userDto.getEmail())) {
            Optional<Users> checkUser = usersRepository.findByEmail(userDto.getEmail());
            if (checkUser.isPresent()) {
                return "Email already exist";
            }
            user.setEmail(userDto.getEmail());
        }
        if (password != null) {
            password = funUtils.checkPassword(password);
            if (password == null) {
                return "Password not valid";
            }
            user.setPassword(password);
        }
        user = usersRepository.save(user);
        return jwtService.generateToken(user);
    }

    /**
     * Subscribes the current user to a topic.
     *
     * @param idTopic the ID of the topic to subscribe to.
     * @return true if the subscription is successful, false otherwise.
     */
    public Boolean topicSubscription(int idTopic) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = usersRepository.findByEmail(email).get();
        Optional<Topics> topics = topicsRepository.findById(idTopic);
        if (topics.isEmpty()) {
            return false;
        }
        List<Topics> topicsList = user.getTopicsList();
        if (topicsList == null) {
            topicsList = new ArrayList<>();
        }
        if (topicsList.contains(topics.get())) {
            return false; //car existe déjà
        }
        topicsList.add(topics.get());
        user.setTopicsList(topicsList);
        usersRepository.save(user);
        return true;
    }

    /**
     * Unsubscribes the current user from a topic.
     *
     * @param idTopic the ID of the topic to unsubscribe from.
     * @return true if the unsubscription is successful, false otherwise.
     */
    public Boolean topicUnsubscription(int idTopic) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = usersRepository.findByEmail(email).get();
        Optional<Topics> topics = topicsRepository.findById(idTopic);
        if (topics.isEmpty()) {
            return false;
        }
        List<Topics> topicsList = user.getTopicsList();
        if (topicsList == null) {
            return false;
        }
        if (!topicsList.contains(topics.get())) {
            return false;
        }

        topicsList.remove(topics.get());
        user.setTopicsList(topicsList);
        usersRepository.save(user);
        return true;
    }

    /**
     * Retrieves a list of articles based on the user's topics subscriptions.
     *
     * @return List of ArticlesDto objects corresponding to the user's subscribed topics.
     */
    public List<ArticlesDto> getArticlesByTopicsSubscriptions() {

        UsersDto usersDto = authService.getMyInfo();
        List<ArticlesDto> articlesDtoList = articlesService.getAllArticlesByTopics(usersDto.getTopicsList());

        return articlesDtoList;
    }
}
