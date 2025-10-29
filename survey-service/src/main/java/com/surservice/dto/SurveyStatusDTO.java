package com.surservice.dto;

public record SurveyStatusDTO(
	    Long id,
	    String title,
	    String description,
	    boolean completedByUser // El nuevo campo
	) {}