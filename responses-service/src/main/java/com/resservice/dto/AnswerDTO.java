package com.resservice.dto;

public class AnswerDTO {
	private Long questionId;
	private String response;

	public AnswerDTO() {
		super();
	}

	public AnswerDTO(Long questionId, String response) {
		super();
		this.questionId = questionId;
		this.response = response;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

}