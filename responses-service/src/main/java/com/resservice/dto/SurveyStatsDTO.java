package com.resservice.dto;

import java.util.List;

public record SurveyStatsDTO(
	    Long surveyId,
	    String surveyTitle,
	    List<QuestionStatsDTO> questions // Una lista con las estadísticas de cada pregunta
	) {}