package com.openclassrooms.mddapi.back.repository;

import com.openclassrooms.mddapi.back.model.Topics;
import com.openclassrooms.mddapi.back.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicsRepository extends JpaRepository<Topics, Integer> {

    Topics findByLabel(String label);
}
