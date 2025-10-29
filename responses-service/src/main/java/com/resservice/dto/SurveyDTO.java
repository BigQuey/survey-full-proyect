package com.resservice.dto;

import java.util.List;

public class SurveyDTO {

	private Long id;
	private String title;
	private String description;
	private List<QuestionDTO> questions;

	public SurveyDTO() {
		super();
	}

	public SurveyDTO(Long id, String title, String description, List<QuestionDTO> questions) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.questions = questions;
	}

	public List<QuestionDTO> getQuestions() {
		return questions;
	}

	public void setQuestions(List<QuestionDTO> questions) {
		this.questions = questions;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
