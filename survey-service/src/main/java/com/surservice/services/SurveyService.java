package com.surservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.surservice.models.Survey;
import com.surservice.repository.SurveyRepository;

@Service
public class SurveyService extends ICRUDImpl<Survey, Long>{
	@Autowired
	private SurveyRepository repo;
	
	
	@Override
	public JpaRepository<Survey, Long> getRepository() {
		// TODO Auto-generated method stub
		return repo;
	}

}
