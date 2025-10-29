package com.questservice.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.questservice.dto.QuestionDTO;
import com.questservice.models.Question;
import com.questservice.repository.QuestionRepository;



@Service
public class QuestionService extends ICRUDImpl<Question, Long>{
	
	@Autowired
	private QuestionRepository repo;
	@Autowired
    private ModelMapper mapper;
	@Override
	public JpaRepository<Question, Long> getRepository() {
		// TODO Auto-generated method stub
		return repo;
	}
	public List<QuestionDTO> findBySurveyId(Long surveyId){
		return repo.findBySurveyId(surveyId)
                .stream()
                .map(q -> mapper.map(q, QuestionDTO.class))
                .collect(Collectors.toList());
	};

}
