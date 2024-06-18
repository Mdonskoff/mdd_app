package com.openclassrooms.mddapi.back.repository;

import com.openclassrooms.mddapi.back.model.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentsRepository extends JpaRepository<Comments, Integer> {
}
