package com.surservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.surservice.models.Question;
import com.surservice.repository.QuestionRepository;

@Service
public class QuestionService extends ICRUDImpl<Question, Long>{
	
	@Autowired
	private QuestionRepository repo;
	
	@Override
	public JpaRepository<Question, Long> getRepository() {
		// TODO Auto-generated method stub
		return repo;
	}

}
