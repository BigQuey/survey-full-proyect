package com.resservice.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import com.resservice.models.Response;

public interface ResponseRepository extends JpaRepository<Response, Long> {
	List<Response> findBySurveyId(Long surveyId);
	List<Response> findByUserEmail(String email);
	List<Response> findByUserEmailAndSurveyId(String email, Long surveyId);
}
