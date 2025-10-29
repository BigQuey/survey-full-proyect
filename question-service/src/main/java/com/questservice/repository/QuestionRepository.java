package com.questservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.questservice.models.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
	List<Question> findBySurveyId(Long surveyId);
}
