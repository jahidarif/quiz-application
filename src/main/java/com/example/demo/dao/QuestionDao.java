package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.demo.model.Question; 

import java.util.List;

@Repository  
public interface QuestionDao extends JpaRepository<Question, Integer> {
    List<Question> findByCategory(String category1);
    @Query(value = "SELECT * FROM question q WHERE q.category = :category ORDER BY RANDOM() LIMIT :numQ", nativeQuery = true)
    List<Question> findQuestionByCategory(@Param("category") String category1, @Param("numQ") int numQ);
}
