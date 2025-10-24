package com.surservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.surservice.models.Survey;

public interface SurveyRepository extends JpaRepository<Survey, Long>{

}
