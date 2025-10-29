package com.resservice.dto;

import java.util.List;

public class ResponseDTO {

	private Long surveyId;
	private String userEmail;
	private List<AnswerDTO> answers;

	public ResponseDTO() {
		super();
	}

	public ResponseDTO(Long surveyId, String userEmail, List<AnswerDTO> answers) {
		super();
		this.surveyId = surveyId;
		this.userEmail = userEmail;
		this.answers = answers;
	}

	public Long getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(Long surveyId) {
		this.surveyId = surveyId;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public List<AnswerDTO> getAnswers() {
		return answers;
	}

	public void setAnswers(List<AnswerDTO> answers) {
		this.answers = answers;
	}

}