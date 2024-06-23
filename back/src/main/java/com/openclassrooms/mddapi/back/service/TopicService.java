package com.openclassrooms.mddapi.back.service;

import com.openclassrooms.mddapi.back.dto.TopicsDto;
import com.openclassrooms.mddapi.back.model.Topics;
import com.openclassrooms.mddapi.back.repository.TopicsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TopicService {

    @Autowired
    TopicsRepository topicsRepository;

    /**
     * Retrieves all topics.
     *
     * @return a list of all topic DTOs.
     */
    public List<TopicsDto> getAll() {
        List<Topics> topicsList = topicsRepository.findAll();
        List<TopicsDto> topicsDtoList = new ArrayList<>();

        // Convert each topic entity to a DTO and add to the list
        for (Topics topic : topicsList) {
            TopicsDto newTopicsDto = convertTopicsToTopicsDto(topic);
            topicsDtoList.add(newTopicsDto);
        }
        return topicsDtoList;
    }

    /**
     * Creates a new topic.
     *
     * @param topic the topic DTO containing topic details.
     * @return the created topic as a DTO, or null if validation or persistence fails.
     */
    public TopicsDto createTopic(TopicsDto topic) {
        // Validate the input DTO
        if (topic.getLabel().isEmpty()) {
            return null;
        }
        // Check if the topic label already exists
        Topics checkTopic = topicsRepository.findByLabel(topic.getLabel());
        if (checkTopic != null) {
            return null;
        }
        // Convert the DTO to an entity, save it, and return the saved entity as a DTO
        checkTopic = convertTopicsDtoToTopics(topic);
        return convertTopicsToTopicsDto(topicsRepository.save(checkTopic));

    }

    /**
     * Converts a topic entity to a data transfer object.
     *
     * @param topic the topic entity to convert.
     * @return the converted topic DTO.
     */
    private TopicsDto convertTopicsToTopicsDto(Topics topic) {
        TopicsDto topicsDto = new TopicsDto();
        topicsDto.setId(topic.getId());
        topicsDto.setLabel(topic.getLabel());
        return topicsDto;
    }

    /**
     * Converts a topic data transfer object to an entity.
     *
     * @param topicsDto the topic DTO to convert.
     * @return the converted topic entity.
     */
    private Topics convertTopicsDtoToTopics(TopicsDto topicsDto) {
        Topics topic = new Topics();
        topic.setId(topicsDto.getId());
        topic.setLabel(topicsDto.getLabel());
        return topic;
    }


}
